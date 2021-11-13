package org.jasef.framework.base;

import com.google.inject.Provides;
import org.jasef.framework.configuration.Configuration;
import org.jasef.framework.configuration.impl.SystemPropertiesConfiguration;
import org.jasef.framework.driver.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

public abstract class AbstractLoadableComponent<P extends AbstractLoadableComponent<P>>
    extends LoadableComponent<AbstractLoadableComponent<P>> {

  /**
   * This routine needs to be implemented by descendant classes. It is used to load a {@link
   * LoadableComponent} when it is not already loaded.
   */
  @Override
  public abstract void load();

  /**
   * This routine needs to be implemented by descendant classes. It needs to provide a reliable
   * check if the {@link LoadableComponent} was already loaded.
   *
   * @throws Error when the component is not loaded
   */
  @Override
  public abstract void isLoaded() throws Error;

  /**
   * Provides a browser driver instance to be used on the {@link LoadableComponent}s
   *
   * @return a WebDriver instance as required by configuration
   */
  @SuppressWarnings({"java:S125"})
  @Provides
  public WebDriver provideDriver() {
    // TODO fix driverType configuration when configuration implementation is finalized
    Configuration driverConfiguration = new SystemPropertiesConfiguration();
    DriverType driverType = DriverType.valueOf(
        driverConfiguration.get("driverType").toUpperCase());
    // TODO driver configurations and plain object return by appropriately configured driver
    return switch (driverType) {
      case CHROME -> new ChromeDriver();
      case FIREFOX -> new FirefoxDriver();
      case SAFARI -> new SafariDriver();
      case OPERA -> new OperaDriver();
      case REMOTE -> null;
    };
  }

  /**
   * Check if component is already loaded. If not, perform load operation as defined in Class
   * implementing {@link AbstractLoadableComponent}.
   *
   * @return The component.
   * @throws Error when the component cannot be loaded.
   */
  @Override
  @SuppressWarnings({"unchecked", "java:S1181"})
  // ignore unchecked cast, this is to be considered safe here
  // also ignore throwing an error instead of exception here. This extends Selenium API.
  public P get() {
    try {
      isLoaded();
      return (P) this;
    } catch (Error e) {
      load();
    }
    isLoaded();
    return (P) this;
  }
}
