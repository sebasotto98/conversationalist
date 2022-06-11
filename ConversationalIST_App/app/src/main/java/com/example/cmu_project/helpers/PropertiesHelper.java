package com.example.cmu_project.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

public class PropertiesHelper {
    private final Properties configProp = new Properties();

    private PropertiesHelper()
    {
        //Private constructor to restrict new instances
        InputStream in = Objects.requireNonNull(this.getClass().getClassLoader()).getResourceAsStream("local.properties");
        System.out.println("Reading all properties from the file");
        try {
            configProp.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Bill Pugh Solution for singleton pattern
    private static class LazyHolder
    {
        private static final PropertiesHelper INSTANCE = new PropertiesHelper();
    }

    public static PropertiesHelper getInstance()
    {
        return LazyHolder.INSTANCE;
    }

    public String getProperty(String key){
        return configProp.getProperty(key);
    }

    public Set<String> getAllPropertyNames(){
        return configProp.stringPropertyNames();
    }

    public boolean containsKey(String key){
        return configProp.containsKey(key);
    }
}