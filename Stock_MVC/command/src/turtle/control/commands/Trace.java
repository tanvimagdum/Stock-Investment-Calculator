package turtle.control.commands;

import turtle.control.TracingTurtleCommand;
import turtle.control.UndoableTTCmd;
import turtle.tracingmodel.TracingTurtleModel;

/**
 * Created by blerner on 10/10/16.
 */
public class Trace implements UndoableTTCmd {
  double d;

  public Trace(Double d) {
    this.d = d;
  }

  @Override
  public void go(TracingTurtleModel m) {
    m.trace(this.d);
  }

  @Override
  public void undo(TracingTurtleModel m) {

  }
}
