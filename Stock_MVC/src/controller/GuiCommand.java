package controller;

import model.PortfolioManager;
import view.JFrameView;

public interface GuiCommand extends Command {
  void go(JFrameView f, PortfolioManager p, API api);
}
