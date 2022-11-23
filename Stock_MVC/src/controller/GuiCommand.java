package controller;

import model.PortfolioManager;
import view.JFrameView;

public interface GuiCommand {
  void go(JFrameView f, PortfolioManager p, API api);
}
