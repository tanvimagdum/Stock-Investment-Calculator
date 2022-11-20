package turtle.control.commands;

import turtle.control.TracingTurtleCommand;
import turtle.control.UndoableTTCmd;
import turtle.tracingmodel.TracingTurtleModel;

/**
 * Created by blerner on 10/10/16.
 */
public class Move implements TracingTurtleCommand {
  double d;

  public Move(Double d) {
    this.d = d;
  }

  @Override
  public void go(TracingTurtleModel m) {
    m.move(this.d);
  }

//  @Override
//  public void undo(TracingTurtleModel m) {
//    m.move(-this.d);
//  }
}
