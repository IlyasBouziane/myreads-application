package com.myproject.myreadsapp.connection;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("datastax.astra")
public class DatastaxAstraConfigs {
    private File secureConnectBundle;

    public File getSecureConnectBundle() {
        return secureConnectBundle;
    }

    public void setSecureConnectBundle(File secureConnectBundle) {
        this.secureConnectBundle = secureConnectBundle;
    }
    

}
