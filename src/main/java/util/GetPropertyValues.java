package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by AyvAndr on 4/1/2017.
 */
public class GetPropertyValues {

    public GetPropertyValues(){}

    public HashMap<String,String> getEnvironmentalPropValues() {
        HashMap<String,String> envProps = new HashMap<>();
        try {
            File file = new File("res/environment.properties");
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                envProps.put(key,value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return envProps;
    }

    public HashMap<String,String> getQueryPropValues() {
        HashMap<String,String> envProps = new HashMap<>();
        try {
            File file = new File("res/query.properties");
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                envProps.put(key,value);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return envProps;
    }
}
