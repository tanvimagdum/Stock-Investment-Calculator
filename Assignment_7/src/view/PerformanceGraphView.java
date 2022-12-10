package view;

import java.util.List;

import javax.swing.JPanel;

/**
 * This interface represents the definition to display the graph using the graphics in swing.
 */
public interface PerformanceGraphView {
  /**
   * This function generates the graph as well as the scale of the graph.
   * This function draws the xaxis, yaxis based on the values of the input, the generated graph
   * shows red if the slope of the line is negative indicating the diminishing performance of
   * portfolio between two dates, graph line are green when the slope of the line is positive
   * indicating the growth of the portfolio between two dates.
   *
   * @param xData        represents the interval dates.
   * @param yData        represents the scaled data between 0 and 50.
   * @param intervalSize represents the intervals and the scale for the yaxis.
   * @return JPanel consisting of graph panel and the scale panel.
   */
  JPanel displayGraph(List<String> xData, List<String> yData, List<Double> intervalSize);
}
