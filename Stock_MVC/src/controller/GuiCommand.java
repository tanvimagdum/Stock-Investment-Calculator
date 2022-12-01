package controller;

import model.PortfolioManager;
import view.GuiInterface;
import view.JFrameView;

public interface GuiCommand extends Command {
  void go(GuiInterface f, PortfolioManager p, API api);
}
