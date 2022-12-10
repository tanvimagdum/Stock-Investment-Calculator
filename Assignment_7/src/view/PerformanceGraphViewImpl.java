package view;



import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;


/**
 * This method represents the implementation of the Performance Graph View.
 * This method extends JPanel and used Graphics to draw the lines between two points.
 * This class also provides the functionality to display the positive slope performance
 * shown in green color as well as negative slope performance shown in green color.
 */
public class PerformanceGraphViewImpl extends JPanel implements PerformanceGraphView {

  private int axisPadding;
  private int axisLabelPadding;
  private Color graphGridLayoutColor;
  private static Stroke GRAPH_STROKE;
  private int pointWeight;
  private int yAxisPartition;
  private List<Double> yAxisData;
  private List<String> xAxisData;
  private Graphics2D graphics;

  /**
   * Constructs the default performance graph object.
   */
  public PerformanceGraphViewImpl() {
    this.axisPadding = 20;
    this.axisLabelPadding = 20;
    this.graphGridLayoutColor = new Color(150, 150, 150, 150);
    this.GRAPH_STROKE = new BasicStroke(3f);
    this.pointWeight = 10;
    this.yAxisPartition = 50;
  }

  /**
   * This constructs the performanceGraphViewImpl with two inputs representing data to plot.
   *
   * @param xAxisData represents the interval dates.
   * @param yAxisData represents the scaled performance scores.
   */
  public PerformanceGraphViewImpl(List<String> xAxisData, List<Double> yAxisData) {
    this.xAxisData = xAxisData;
    this.yAxisData = yAxisData;
  }

  private double[] generateGraphScales() {
    double x = ((double) getWidth() - (2 * axisPadding) - axisLabelPadding)
            / (yAxisData.size() - 1);
    double y = ((double) getHeight() - 2 * axisPadding - axisLabelPadding) / (50 - 0);
    return new double[]{x, y};
  }

  private List<Point> generateGraphCoordinates(double xScale, double yScale) {
    List<Point> pointsOnGraph = new ArrayList<>();
    for (int i = 0; i < yAxisData.size(); i++) {
      int x1 = (int) (i * xScale + axisPadding + axisLabelPadding);
      int y1 = (int) ((50 - yAxisData.get(i)) * yScale + axisPadding);
      pointsOnGraph.add(new Point(x1, y1));
    }
    return pointsOnGraph;
  }

  private void generateYaxis() {
    for (int i = 0; i < yAxisPartition + 1; i++) {
      int x0 = axisPadding + axisLabelPadding;
      int y0 = getHeight() - ((i * (getHeight() - axisPadding * 2 - axisLabelPadding))
              / yAxisPartition + axisPadding + axisLabelPadding);
      int y1 = y0;
      int j = 1;
      if (yAxisData.size() > 0) {
        graphics.setColor(graphGridLayoutColor);
        graphics.drawLine(axisPadding + axisLabelPadding + j + pointWeight, y0,
                getWidth() - axisPadding, y1);
        graphics.setColor(Color.BLACK);
        String yLabel = (int) (Math.ceil((50 * ((i * 1.0) / yAxisPartition) * 100) / 100.0)) + "";
        FontMetrics metrics = graphics.getFontMetrics();
        int labelWidth = metrics.stringWidth(yLabel);
        graphics.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
      }
    }
  }

  private void generateXaxis() {
    for (int i = 0; i < yAxisData.size(); i++) {
      if (yAxisData.size() > 1) {
        int x0 = i * (getWidth() - axisPadding * 2 - axisLabelPadding) /
                (yAxisData.size() - 1) + axisPadding + axisLabelPadding;
        int x1 = x0;
        int y0 = getHeight() - axisPadding - axisLabelPadding;
        if ((i % ((int) ((yAxisData.size() / 20.0)) + 1)) == 0) {
          graphics.setColor(graphGridLayoutColor);
          graphics.drawLine(x0, getHeight() - axisPadding - axisLabelPadding - 1 - pointWeight
                  , x1, axisPadding);
          graphics.setColor(Color.BLACK);
          String xLabel = xAxisData.get(i) + " ";
          FontMetrics metrics = graphics.getFontMetrics();
          int labelWidth = metrics.stringWidth(xLabel);
          graphics.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
        }
      }
    }
  }

  private double calculateSlope(int x1, int y1, int x2, int y2) {
    return (y2 - y1) * 1.0 / (x2 - x1);
  }

  private void plotLineGraph(List<Point> graphPoints) {
    graphics.setStroke(GRAPH_STROKE);
    for (int i = 0; i < graphPoints.size() - 1; i++) {
      int x1 = graphPoints.get(i).x;
      int y1 = graphPoints.get(i).y;
      int x2 = graphPoints.get(i + 1).x;
      int y2 = graphPoints.get(i + 1).y;
      if (calculateSlope(x1, y1, x2, y2) > 0) {
        graphics.setColor(Color.RED);
        graphics.drawLine(x1, y1, x2, y2);
      } else {
        graphics.setColor(Color.GREEN);
        graphics.drawLine(x1, y1, x2, y2);
      }

    }
  }

  private void plotDataPoint(List<Point> graphPoints) {
    for (int i = 0; i < graphPoints.size(); i++) {
      int x = graphPoints.get(i).x - pointWeight / 2;
      int y = graphPoints.get(i).y - pointWeight / 2;
      int ovalW = pointWeight;
      int ovalH = pointWeight;
      graphics.fillOval(x, y, ovalW, ovalH);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    graphics = (Graphics2D) g;

    double[] scales = generateGraphScales();
    List<Point> pointsonGraph = generateGraphCoordinates(scales[0], scales[1]);

    graphics.setColor(Color.WHITE);

    int x = axisPadding + axisLabelPadding;
    int y = axisPadding;
    int width = getWidth() - (2 * axisPadding) - axisLabelPadding;
    int height = getHeight() - (2 * axisPadding) - axisLabelPadding;

    graphics.fillRect(x, y, width, height);
    graphics.setColor(Color.BLACK);

    generateYaxis();
    generateXaxis();

    graphics.drawLine(axisPadding + axisLabelPadding,
            getHeight() - axisPadding - axisLabelPadding,
            axisPadding + axisLabelPadding, axisPadding);
    graphics.drawLine(axisPadding + axisLabelPadding,
            getHeight() - axisPadding - axisLabelPadding,
            getWidth() - axisPadding, getHeight() - axisPadding - axisLabelPadding);

    Stroke oldStroke = graphics.getStroke();
    plotLineGraph(pointsonGraph);
    graphics.setStroke(oldStroke);
    graphics.setColor(Color.BLACK);
    plotDataPoint(pointsonGraph);

  }

  @Override
  public JPanel displayGraph(List<String> xAxisData, List<String> yData,
                             List<Double> intervalSize) {
    List<Double> yAxisData = new ArrayList<>();
    int dataPoints = yData.size();
    for (int i = 0; i < dataPoints; i++) {
      yAxisData.add(Double.parseDouble(yData.get(i)));
    }
    PerformanceGraphView mainPanel = new PerformanceGraphViewImpl(xAxisData, yAxisData);
    JPanel newPanel = (JPanel) mainPanel;
    newPanel.setPreferredSize(new Dimension(900, 800));
    String scale = "";
    if (intervalSize.get(0).equals(intervalSize.get(2))) {
      scale = "Scale: 1 unit on y axis = $"
              + intervalSize.get(0);
    } else {
      scale = "Base: $"
              + intervalSize.get(0) +
              " interval $" + intervalSize.get(2);
    }
    String xaxis = "X - axis : Interval Dates";
    String yaxis = "Y - axis : Performance";
    JPanel scalePanel = new JPanel(new GridLayout(0, 1));
    JLabel label = new JLabel(scale);
    JLabel xAxisLabel = new JLabel(xaxis);
    JLabel yAxisLabel = new JLabel(yaxis);
    scalePanel.add(label);
    scalePanel.add(xAxisLabel);
    scalePanel.add(yAxisLabel);
    newPanel.add(scalePanel);


    return newPanel;
  }

}
