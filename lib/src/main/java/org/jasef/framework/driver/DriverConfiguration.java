package org.jasef.framework.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class DriverConfiguration {

  public DriverConfiguration() {
    throw new UnsupportedOperationException("implementation pending");
  }

  public ChromeDriver getChrome() {
    return new ChromeDriver();
  }

  public FirefoxDriver getFirefox() {
    return new FirefoxDriver();
  }

  public RemoteWebDriver getChromeRemote() {
    ChromeOptions chromeOptions = new GenericOptions<>(ChromeOptions.class).create();
    try {
      return new RemoteWebDriver(getGridUrl(), chromeOptions);
    } catch (MalformedURLException malformedURLException) {
      log.error("Invalid grid URL", malformedURLException);
      return null;
    }
  }

  public RemoteWebDriver getFirefoxRemote() {
    FirefoxOptions firefoxOptions = new GenericOptions<>(FirefoxOptions.class).create();
    try {
      return new RemoteWebDriver(getGridUrl(), firefoxOptions);
    } catch (MalformedURLException malformedURLException) {
      log.error("Invalid grid URL", malformedURLException);
      return null;
    }
  }

  private URL getGridUrl() throws MalformedURLException {
    // TODO get grid url from config
    return new URL("https://grid4host:4444");
  }
}
