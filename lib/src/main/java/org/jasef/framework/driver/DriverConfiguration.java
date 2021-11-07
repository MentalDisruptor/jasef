package org.jasef.framework.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class DriverConfiguration {

    public DriverConfiguration() {
        throw new UnsupportedOperationException("implementation pending");
    }

    public ChromeDriver getChrome(){
        return new ChromeDriver();
    }

    public FirefoxDriver getFirefox() {
        return new FirefoxDriver();
    }

    public SafariDriver getSafari() {
        return new SafariDriver();
    }

    public OperaDriver getOpera() {
        return new OperaDriver();
    }

    public RemoteWebDriver getChromeRemote() {
        ChromeOptions chromeOptions = new GenericOptions<>(ChromeOptions.class).create();
        try {
            return new RemoteWebDriver(new URL("http://grid4host:4444/"), chromeOptions);
        } catch (MalformedURLException malformedURLException) {
            return null;
        }
    }

    public RemoteWebDriver getFirefoxRemote() {
        return null;
    }
}
