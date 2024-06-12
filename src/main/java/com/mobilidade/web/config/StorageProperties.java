package com.mobilidade.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
//@Component
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "C:\\Users\\coutinho\\git\\web\\src\\main\\resources\\static\\assets\\img\\profile\\"; //"C:\\Users\\jcsantos\\Documents\\Unigranrio\\web\\src\\main\\resources\\static\\assets\\img\\profile\\";
    						                    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
