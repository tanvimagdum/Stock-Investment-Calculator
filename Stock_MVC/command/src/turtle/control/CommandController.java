package turtle.control;

import java.util.InputMismatchException;
import java.util.Scanner;

import turtle.control.commands.Koch;
import turtle.control.commands.Move;
import turtle.control.commands.Square;
import turtle.control.commands.Trace;
import turtle.control.commands.Turn;
import turtle.tracingmodel.Line;
import turtle.tracingmodel.SmarterTurtle;
import turtle.tracingmodel.TracingTurtleModel;


/**
 * Created by blerner on 10/10/16.
 */
public class CommandController {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    TracingTurtleModel m = new SmarterTurtle();
    TracingTurtleCommand cmd = null;
    while (s.hasNext()) {
      String in = s.next();
      try {
        switch (in) {
          case "q":
          case "quit":
            return;
         case "show":
            for (Line l : m.getLines()) {
              System.out.println(l);
            }
            //reset the model
            m = new SmarterTurtle();
            break;
          case "move":
            cmd = new Move(s.nextDouble());
            break;
          case "trace":
            cmd = new Trace(s.nextDouble());
            break;
          case "turn":
            cmd = new Turn(s.nextDouble());
            break;
          case "square":
            cmd = new Square(s.nextDouble());
            break;
          case "koch":
            cmd = new Koch(s.nextDouble(),s.nextInt());
            break;
          default:
            System.out.println(String.format("Unknown command %s", in));
            cmd = null;
            break;
        }
        if (cmd != null) {
          cmd.go(m);
          cmd = null;
        }
      } catch (InputMismatchException ime) {
        System.out.println("Bad length to " + in);
      }
    }
  }
}
