package com.epam.mjc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class FileReader {

    public Profile getDataFromFile(File file) {


        Map<String, String> profileProperties = new HashMap<String, String>();
        String property = "", value = "";

        try (FileInputStream input = new FileInputStream(file)) {
            boolean isProperty = false;
            int ch;
            while ((ch = input.read()) != -1) {
                if (ch == '\n') {
                    profileProperties.put(property.trim(), value.trim());
                    property = "";
                    value = "";
                    isProperty = false;
                }

                if (ch == ':' && !isProperty) {
                    isProperty = true;
                    continue;
                }

                if (isProperty) value += (char) ch;
                else property += (char) ch;

            }

        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }

        return parsingFileIntoMap(profileProperties);
    }
    
    public Profile parsingFileIntoMap(Map<String, String> properties) {
        return new Profile(properties.get("Name").trim(),
                Integer.valueOf(properties.get("Age")),
                properties.get("Email"),
                Long.valueOf(properties.get("Phone"))
        );
    }

}
