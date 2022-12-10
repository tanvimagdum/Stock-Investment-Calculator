package model;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * {@code PerformanceGraph} is used to defined method to generate scaled interval between dates.
 */
interface PerformanceGraph {

  /**
   * This method generates the scaled intervales between two dates.
   * If number of days between two dates are greater than 30 then interval is based on months
   * If number of months are between two dates are greater than 30 then interval is based on years.
   * @param startDate represents the start date of the interval.
   * @param endDate represents the end date of the interval.
   * @return returns map with scale identifier and scales.
   * @throws ParseException generates error if unable to parse date.
   */
  Map<Character, List<String>> getInterval(String startDate, String endDate) throws ParseException;
}
