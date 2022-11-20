package turtle.control.commands;

import turtle.control.TracingTurtleCommand;
import turtle.tracingmodel.TracingTurtleModel;

/**
 * Created by ashesh on 10/28/2016.
 */
public class Save implements TracingTurtleCommand{

  @Override
  public void go(TracingTurtleModel m) {
    m.save();
  }
}
