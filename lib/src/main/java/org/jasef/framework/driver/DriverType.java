package org.jasef.framework.driver;

public enum DriverType {
  CHROME("chrome"),
  FIREFOX("firefox"),
  OPERA("opera"),
  REMOTE("remote"),
  SAFARI("safari");

  String type;

  DriverType(String type) {
    this.type = type;
  }

  /**
   * Gets the browser type as a String.
   *
   * @return the browser type
   */
  public String getType() {
    return this.type;
  }
}
