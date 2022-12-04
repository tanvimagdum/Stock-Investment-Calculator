package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * {@code PerformanceGraphImpl} represents the implementation of the PerformanceGraphImpl
 * that generates the intervals between two dates.
 */
public class PerformanceGraphImpl implements PerformanceGraph {
  @Override
  public Map<Character, List<String>> getInterval(String startDate, String endDate)
          throws java.text.ParseException {


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Map<Character, List<String>> intervalMap = new HashMap<>();

    Date d1 = sdf.parse(startDate);
    Date d2 = sdf.parse(endDate);
    Calendar startCalendar = getCalendarInstance(d1);
    Calendar endCalendar = getCalendarInstance(d2);

    int years = elapsedTime(startCalendar, endCalendar, YEAR);
    int months = elapsedTime(startCalendar, endCalendar, Calendar.MONTH);
    long days = elapsedTime(startCalendar, endCalendar, DATE);

    Map<Character, Integer> intervalMapScale = getVerticalScale(days, months, years);

    char operation = ' ';
    int duration = 0;

    for (Map.Entry<Character, Integer> entry : intervalMapScale.entrySet()) {
      operation = entry.getKey();
      duration = entry.getValue();
    }

    long intervalSize = (d2.getTime() - d1.getTime()) / duration;

    for (int i = 1; i <= duration && intervalSize > 0; i++) {
      Date date = new Date(d1.getTime() + intervalSize * i);
      Calendar temp = getCalendarInstance(date);

      if (operation == 'm') {
        if (!intervalMap.containsKey(operation)) {
          intervalMap.put(operation, new ArrayList<>());
        }
        intervalMap.get(operation).add(temp.getDisplayName(MONTH, Calendar.SHORT,
                Locale.getDefault()) + " " + (temp.get(YEAR)));

      } else if (operation == 'y') {
        if (!intervalMap.containsKey(operation)) {
          intervalMap.put(operation, new ArrayList<>());
        }
        intervalMap.get(operation).add(String.valueOf(temp.get(YEAR)));

      } else {
        if (!intervalMap.containsKey(operation)) {
          intervalMap.put(operation, new ArrayList<>());
        }
        intervalMap.get(operation).add(sdf.format(date));
      }
    }
    return intervalMap;
  }

  private static Calendar getCalendarInstance(Date date) {
    Calendar cal = Calendar.getInstance(Locale.US);
    cal.setTime(date);
    return cal;
  }


  private static Map<Character, Integer> getVerticalScale(long numDays, int numMonth,
                                                          int numYears) {
    Map<Character, Integer> verticalMap = new HashMap<>();

    if (numYears >= 5 && numYears <= 30) {
      verticalMap.put('y', numYears);
      return verticalMap;
    } else if (numYears > 30) {
      verticalMap.put('y', numYears / 6);
      return verticalMap;
    } else {
      if (numMonth >= 5 && numMonth <= 30) {
        verticalMap.put('m', numMonth);
        return verticalMap;
      } else if (numMonth > 30) {
        verticalMap.put('m', numMonth / 6);
        return verticalMap;
      } else {
        if (numDays <= 30) {
          verticalMap.put('d', (int) numDays);
          return verticalMap;
        } else {
          verticalMap.put('d', (int) numDays / 6);
          return verticalMap;
        }
      }
    }
  }

  private static int elapsedTime(Calendar startDate, Calendar endDate, int field) {

    Calendar temp = (Calendar) startDate.clone(); // Otherwise changes are been reflected.
    int milliseconds = -1;
    while (!temp.after(endDate)) {
      temp.add(field, 1);
      milliseconds++;
    }
    return milliseconds;
  }

}
