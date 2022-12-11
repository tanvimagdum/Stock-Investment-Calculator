package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * {@code FlexiblePortfolioImpl} represents implementation methods of {@code FlexiblePortfolio}.
 * This class has implementation of buy shares, sell shares, daily composition, total valuation,
 * generate the performance graph etc.
 * Buying and Selling shares requires the date to be the working day of the week and does not takes
 * future dates.
 * Composition does not take the future dates.
 * Valuation of the portfolio requires the date to be the working day of the week and does not take
 * future dates. It also generates the total valuation of the entire portfolio.
 * Performance graph generates graph between two intervals via scaling values.
 */

public class FlexiblePortfolioImpl extends StockPortfolioImpl implements FlexiblePortfolio {

  private final FileIO fp;
  private final Properties prop;

  private final APIData apiData;

  private final PerformanceGraph performanceGraph;

  private FixedInvestment fixedInvestment;

  /**
   * Constructs the object of the PortfolioModel implementation.
   * This constructor initializes the properties from the config.properties.
   *
   * @throws IOException if the input stream is unable to load the properties file.
   */
  public FlexiblePortfolioImpl() throws IOException {
    this.prop = new Properties();
    FileInputStream ip = new FileInputStream("src/config.properties");
    this.prop.load(ip);
    this.prop.setProperty("resource_file", "flexible_portfolios");
    fp = new FileIOImpl(prop, this);
    apiData = new VantageAPIData();
    performanceGraph = new PerformanceGraphImpl();
    fixedInvestment = new FixedInvestmentImpl();
  }


  private <T> T convertObjToMap(Object o, TypeReference<T> ref) {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.convertValue(o, ref);
  }

  @Override
  public boolean buyShares(String symbol, double quantity, String date, double commissionFee,
                           String filename)
          throws ParseException, IOException, java.text.ParseException {
    if (validateDate(symbol, date)) {
      fp.writeFileData(symbol, quantity, date, commissionFee,
          "BUY", filename, true);
      return true;
    }
    return false;
  }

  @Override
  public boolean sellShares(String symbol, double quantity, String date, double commissionFee,
                            String filename)
          throws ParseException, IOException, java.text.ParseException {
    Map<String, Object> map = fp.readFileData(filename);
    if (map == null) {
      return false;
    }
    if (validateDate(symbol, date)) {
      List<Map<String, String>> symbolTransactions = new ArrayList<>();
      if (map.containsKey(symbol)) {
        Object stockObj = map.get(symbol);
        populateSymbolTransaction(symbolTransactions, stockObj);
        DateFormat df = new SimpleDateFormat(prop.getProperty("date_format"));
        boolean sellInvalidFlag = false;
        int totalQuantity = 0;
        for (Map<String, String> stockMap : symbolTransactions) {

          Date stockDate = df.parse(stockMap.get("Date"));
          Date inputDate = df.parse(date);

          if (stockMap.get("Operation").equalsIgnoreCase("BUY")
                  && (isStockDateBeforeInputDate(stockDate, inputDate)
                  || areDatesEqual(stockDate, inputDate))) {
            totalQuantity += (int) (Double.parseDouble(stockMap.get("Quantity")));
          }
          if (stockMap.get("Operation").equalsIgnoreCase("SELL")
                  && (isStockDateBeforeInputDate(stockDate, inputDate)
                  || areDatesEqual(stockDate, inputDate))) {
            totalQuantity -= (int) (Double.parseDouble(stockMap.get("Quantity")));
          }
          if (stockMap.get("Operation").equalsIgnoreCase("SELL")
                  && isStockDateAfterInputDate(stockDate, inputDate)
          ) {
            sellInvalidFlag = true;
            break;
          }
        }
        if (!sellInvalidFlag && totalQuantity >= quantity) {
          fp.writeFileData(symbol, quantity, date, commissionFee,
              "SELL", filename, false);
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
    return false;
  }

  @Override
  public double calculateCostBasis(String filename, String date) throws ParseException,
          IOException, java.text.ParseException {
    checkForStrategyAndAdd(filename, date);
    List<Map<String, String>> symbolTransactions = new ArrayList<>();
    DateFormat df = new SimpleDateFormat(prop.getProperty("date_format"));
    Map<String, Object> map = fp.readFileData(filename);
    if (map == null) {
      return -1;
    }
    double totalFee = 0.0;
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String symbol = entry.getKey();
      Object obj = map.get(symbol);
      populateSymbolTransaction(symbolTransactions, obj);
      double fee = 0.0;
      for (Map<String, String> stockMap : symbolTransactions) {
        double quantity = Double.parseDouble(stockMap.get("Quantity"));
        double commission = Double.parseDouble(stockMap.get("Fee"));
        Date stockDate = df.parse(stockMap.get("Date"));
        Date inputDate = df.parse(date);
        String[] reader = apiData.getInputStream(symbol);
        String price = apiData.getPriceForDate(reader,
                stockMap.get("Date").toString(), "daily").toString();
        if (isStockDateAfterInputDate(inputDate, stockDate) || areDatesEqual(stockDate,
                inputDate)) {
          if (stockMap.get("Operation").equals("BUY")) {
            fee += commission + Double.parseDouble(price) * quantity;
          } else {
            fee += commission;
          }
        }

      }
      totalFee += fee;
    }
    return totalFee;
  }

  private void populateSymbolTransaction(List<Map<String, String>> symbolTransactions, Object obj) {
    symbolTransactions.clear();
    ArrayList<Object> values = (ArrayList<Object>) obj;
    for (Object objt : values) {
      Map<String, String> newMap = convertObjToMap(objt, new TypeReference<>() {
      });
      symbolTransactions.add(new HashMap<>(newMap));
    }
  }

  @Override
  public List<List<String>> getFlexiblePortfolioValuation(String filename, String date)
          throws ParseException, IOException, java.text.ParseException {
    checkForStrategyAndAdd(filename, date);
    List<List<String>> portfolioList = getFlexiblePortfolioComposition(filename, date);
    if (portfolioList == null) {
      return null;
    }
    return getUpdatedRecord(portfolioList, date);
  }

  @Override
  public List<List<String>> getFlexiblePortfolioComposition(String filename, String date)
          throws ParseException, java.text.ParseException, IOException {
    checkForStrategyAndAdd(filename, date);
    List<Map<String, String>> symbolTransactions = new ArrayList<>();
    List<List<String>> portfolioList = new ArrayList<>();
    Map<String, Object> map = fp.readFileData(filename);
    if (map == null) {
      return null;
    }
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String symbol = entry.getKey();
      Object obj = map.get(symbol);
      populateSymbolTransaction(symbolTransactions, obj);
      DateFormat df = new SimpleDateFormat(prop.getProperty("date_format"));
      List<String> currList = new ArrayList<>();
      double quantity = 0;
      for (Map<String, String> stockMap : symbolTransactions) {

        Date stockDate = df.parse(stockMap.get("Date"));
        Date inputDate = df.parse(date);

        if (stockMap.get("Operation").equalsIgnoreCase("BUY")
                && (isStockDateBeforeInputDate(stockDate, inputDate)
                || areDatesEqual(stockDate, inputDate))) {
          quantity += Double.parseDouble(stockMap.get("Quantity"));
        }
        if (stockMap.get("Operation").equalsIgnoreCase("SELL")
                && (isStockDateBeforeInputDate(stockDate, inputDate)
                || areDatesEqual(stockDate, inputDate))) {
          quantity -= Double.parseDouble(stockMap.get("Quantity"));
        }
      }
      currList.add(symbol);
      currList.add("" + (int) (quantity));
      currList.add(date);
      currList.add("");
      portfolioList.add(currList);
    }
    return portfolioList;
  }


  private boolean areDatesEqual(Date date1, Date date2) {
    return date1.equals(date2);
  }

  private boolean isStockDateBeforeInputDate(Date date1, Date date2) {
    return date1.before(date2);
  }

  private boolean isStockDateAfterInputDate(Date date1, Date date2) {
    return date1.after(date2);
  }

  private boolean isHoliday(String date) throws java.text.ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date weekend = sdf.parse(date);
    Calendar cal = Calendar.getInstance();
    cal.setTime(weekend);
    int day = cal.get(Calendar.DAY_OF_WEEK);
    return day == Calendar.SATURDAY || day == Calendar.SUNDAY;
  }


  @Override
  public String getFlexiblePortfolioTotalValuation(List<List<String>> stocks) {
    return getTotalValuation(stocks);
  }

  @Override
  public String createFlexiblePortfolio() throws IOException {
    return fp.createFile();
  }

  @Override
  public List<String> generateAndDisplayStockGraph(String startDate, String endDate,
                                                   String fileName)
          throws ParseException, java.text.ParseException, IOException {
    Map<Character, List<String>> performanceMap = performanceGraph.getInterval(startDate, endDate);
    SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
    SimpleDateFormat year_date = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<String> priceList = new ArrayList<>();
    for (Map.Entry<Character, List<String>> entry : performanceMap.entrySet()) {
      if (entry.getKey() == 'm') {
        for (String str : entry.getValue()) {
          double value = 0;
          String parseDate = sdf.format(month_date.parse(str));
          List<List<String>> dummyResult = getFlexiblePortfolioComposition(fileName, parseDate);
          if (dummyResult == null) {
            return null;
          }
          List<String> resultList = apiData.getInputStreamMonthly(dummyResult.get(0).get(0),
                  parseDate.split("-")[0] + "-" + parseDate.split("-")[1]);
          String monthDate = resultList.get(1);
          if (!sdf.parse(monthDate).before(sdf.parse(endDate))) {
            monthDate = endDate;
          }

          flexibleGraphHelper(fileName, priceList, str, value, monthDate);
        }
      }


      if (entry.getKey() == 'y') {
        for (String str : entry.getValue()) {
          double value = 0;
          String day = "-01";
          String parseDate = sdf.format(year_date.parse(str)).split("-")[0] + "-12" + day;
          List<List<String>> dummyResult = getFlexiblePortfolioComposition(fileName, parseDate);
          String yearDate = sdf.format(year_date.parse(str)).split("-")[0] + "-12";
          if (!sdf.parse(yearDate + "-31").before(sdf.parse(endDate))) {
            yearDate = endDate.split("-")[0] + "-" + endDate.split("-")[1];
          }
          List<String> resultList = apiData.getInputStreamMonthly(dummyResult.get(0).get(0),
                  yearDate);
          String monthDate = resultList.get(1);
          flexibleGraphHelper(fileName, priceList, str, value, monthDate);
        }
      }


      if (entry.getKey() == 'd') {
        for (String str : entry.getValue()) {
          double value = 0;
          List<List<String>> result = getFlexiblePortfolioComposition(fileName, str);
          for (List<String> res : result) {
            String symbol = res.get(0);
            String[] reader = apiData.getInputStream(symbol);
            String price = apiData.getPriceForDate(reader, str, "daily").toString();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date today = new Date();
            String newdate = str;
            while (price.equals("") && format.parse(newdate).before(today)) {
              Calendar c = Calendar.getInstance();
              c.setTime(sdf.parse(newdate));
              c.add(Calendar.DATE, 1);
              newdate = sdf.format(c.getTime());
              reader = apiData.getInputStream(symbol);
              price = apiData.getPriceForDate(reader, newdate, "daily").toString();
            }
            if (price.equals("") || price.equals("0.00")) {
              value += Double.parseDouble("0.0000") * Double.parseDouble(res.get(1));
            } else {
              value += Double.parseDouble(price) * Double.parseDouble(res.get(1));
            }
          }
          priceList.add("" + str + ":" + String.format("%.2f", value));

        }
      }
    }
    return priceList;
  }

  private void flexibleGraphHelper(String fileName, List<String> priceList, String str,
                                   double value, String monthDate)
          throws ParseException, java.text.ParseException, IOException {
    List<List<String>> result = getFlexiblePortfolioComposition(fileName, monthDate);

    for (List<String> res : result) {
      String symbol = res.get(0);
      String[] reader = apiData.getInputStream(symbol);
      String price = apiData.getPriceForDate(reader, monthDate, "daily").toString();
      if (!price.equals("")) {
        value += Double.parseDouble(price) * Double.parseDouble(res.get(1));
      }
    }
    priceList.add("" + str + ":" + String.format("%.2f", value));
  }

  @Override
  public List<String> generateAsterisk(List<String> priceList) {
    List<Integer> asterisks = generateAsteriskGraph(priceList);
    List<String> getAsterisksCount = new ArrayList<>();
    for (int i = 0; i < priceList.size(); i++) {
      getAsterisksCount.add(priceList.get(i).split(":")[0] + " : " + asterisks.get(i));
    }
    return getAsterisksCount;
  }

  @Override
  public List<Double> getMinMaxPrice(List<String> priceList) {
    double maxPrice = Double.MIN_VALUE;
    double minPrice = Double.MAX_VALUE;
    double intervalSize;
    for (String price : priceList) {
      double getPrice = Double.parseDouble(price.split(":")[1]);
      if (getPrice > maxPrice) {
        maxPrice = getPrice;
      }
      if (getPrice < minPrice && getPrice > 0) {
        minPrice = getPrice;
      }
    }
    if (Math.ceil(maxPrice / minPrice) < 50) {
      intervalSize = minPrice;
    } else {
      intervalSize = Math.ceil((maxPrice - minPrice) / 49);
    }
    return List.of(minPrice, maxPrice, intervalSize);
  }

  private List<Integer> generateAsteriskGraph(List<String> priceList) {
    List<Double> minMax = getMinMaxPrice(priceList);
    double maxPrice = minMax.get(1);
    double minPrice = minMax.get(0);
    List<Integer> countStars = new ArrayList<>();
    if (Math.ceil(maxPrice / minPrice) < 50) {
      for (String price : priceList) {
        double getPrice = Double.parseDouble(price.split(":")[1]);
        countStars.add((int) Math.ceil(getPrice / minPrice));
      }
    } else {
      int intervalSize = (int) Math.ceil((maxPrice - minPrice) / 49);
      Double[] intervals = new Double[51];
      intervals[0] = minPrice;
      for (int i = 1; i <= 50; i++) {
        intervals[i] = Double.valueOf(intervalSize * i);
      }
      for (String price : priceList) {
        double getPrice = Double.parseDouble(price.split(":")[1]);
        if (getPrice == 0.0) {
          countStars.add(0);
          continue;
        }
        for (int i = 0; i < intervals.length - 1; i++) {
          double start = intervals[i];
          double end = intervals[i + 1];
          if (getPrice >= start && getPrice <= end) {
            countStars.add(i + 1);
          }
        }
      }
    }
    return countStars;
  }

  private boolean validateDate(String symbol, String date) throws IOException,
          java.text.ParseException {
    if (!validateSymbol(symbol) || !validateDate(date)) {
      return false;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String currentDate = sdf.format(new Date());
    if (sdf.parse(date).after(sdf.parse(currentDate))) {
      return false;
    }
    String[] reader = apiData.getInputStream(symbol);

    StringBuilder sb = apiData.getPriceForDate(reader, date, "daily");
    return sb.length() > 0;
  }

  @Override
  public boolean uploadFlexiblePortfolio(String path, String newFile)
          throws IOException, ParseException, java.text.ParseException,
          NoSuchFieldException {
    return fp.validateJSONFile(path, newFile);
  }

  @Override
  protected String getResourcePath() {
    return prop.getProperty("resource_file");
  }

  @Override
  public Properties getProperties() {
    return this.prop;
  }

  @Override
  public boolean fixedInvestmentStrategy(double amount, double weight,
                                         String symbol, String date,
                                         double commissionfee, String filename)
          throws IOException, ParseException, java.text.ParseException {
    return fixedInvestment.fixedInvestmentStrategy(amount, weight, symbol,
            date, commissionfee, filename);
  }

  @Override
  public boolean dollarCostAveraging(double amount, double weight, String symbol,
                                     String date, int timeInterval,
                                     String endDate,
                                     double commissionfee, String filename) throws IOException,
          ParseException, java.text.ParseException {
    return fixedInvestment.dollarCostAveraging(amount, weight, symbol, date, endDate, timeInterval,
            commissionfee, filename);
  }

  @Override
  public boolean writeToStrategy(double amount, List<Double> weight, List<String> symbol,
                                 String currentDate,
                                 int timeInterval, String endDate,
                                 List<Double> commissionfee, String filename)
          throws IOException, ParseException {
    return fp.writeStrategy(amount, weight, symbol, currentDate, timeInterval,
            endDate, commissionfee, filename);
  }

  @Override
  public boolean rebalanceSell(String symbol, double quantity, String date, double commissionFee,
      String filename)
      throws ParseException, IOException, NoSuchFieldException, java.text.ParseException {

    fp.writeFileData(symbol, quantity, date, commissionFee,
        "SELL", filename, true);
    return true;
  }

  public void checkForStrategyAndAdd(String fileName, String date) throws
          IOException, ParseException, java.text.ParseException {
    fp.checkForStrategy(fileName, date);

  }
}
