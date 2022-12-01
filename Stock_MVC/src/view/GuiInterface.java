package view;

public interface GuiInterface extends ViewInterface {

  public String printWarning(String line);

  public Object[] getOperationalStuff();

  public String getPortfolioName();

  public void setConStuff(Object[] o);

  public void setCurrScreen(String str);

  public String getCurrScreen();
}
