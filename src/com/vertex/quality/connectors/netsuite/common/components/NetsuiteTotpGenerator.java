package com.vertex.quality.connectors.netsuite.common.components;

import org.jboss.aerogear.security.otp.Totp;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class NetsuiteTotpGenerator {

    private static volatile NetsuiteTotpGenerator instance = null;

    private NetsuiteTotpGenerator (){}

    /**
     * Token Generator Singleton
     * @return
     */
    public static NetsuiteTotpGenerator getInstance() {
        if (instance == null) {
            synchronized(NetsuiteTotpGenerator.class) {
                if (instance == null) {
                    instance = new NetsuiteTotpGenerator();
                }
            }
        }
        return instance;
    }

    /**
     * Maybe this method waits for the login page?
     */
    public void twoFactorLogin(){
        //Wait for 2FA login page?
        //  onRefresh(
        //          if(pageTitle.equals("2FA Login") ){
        //              do login stuff?
        //          }
        //  )
//        beforeExecuteScript
//        default void beforeExecuteScript​(WebDriver driver, java.lang.String script, java.lang.Object[] args)



        AbstractWebDriverEventListener listener = new AbstractWebDriverEventListener() {
            @Override
            public void afterNavigateRefresh(WebDriver driver) {
                super.afterNavigateRefresh(driver);
            }
        };
    }

    /**
     * Method is used to get the TOTP based on the security token
     * // https://mvnrepository.com/artifact/org.jboss.aerogear/aerogear-otp-java
     * @return
     */
    public String getTwoFactorCode(String userKey){
        //Totp totp = new Totp("nlyy riax spwd omi7 buvo 32cu as6t z7aa"); // Example 2FA secret key
        //Totp totp = new Totp("WN5X CYSM VXWQ 5YTJ BVVP 6VSI CT3A WONS"); // ConnectorTestAutomation 2FA secret key
        Totp totp = new Totp(userKey);

        return totp.now(); //Generated 2FA code here
    }

    /**
     * Method is used to get the TOTP based on the security token
     * // https://mvnrepository.com/artifact/org.jboss.aerogear/aerogear-otp-java
     * @return
     */
    public String getTwoFactorCode(){
        //Totp totp = new Totp("nlyy riax spwd omi7 buvo 32cu as6t z7aa"); // Example 2FA secret key
        //Totp totp = new Totp("WN5X CYSM VXWQ 5YTJ BVVP 6VSI CT3A WONS"); // ConnectorTestAutomation 2FA secret key
        Totp totp = new Totp("5Z5P 7VBG LGCO 5EWN Y65Y ZYOU HQV3 AIU5");

        return totp.now(); //Generated 2FA code here
    }

}
