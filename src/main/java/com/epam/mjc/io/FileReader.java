package com.epam.mjc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileReader {

    Logger logger = Logger.getLogger(FileReader.class.getName());
    public Profile getDataFromFile(File file) {


        Map<String, String> profileProperties = new HashMap<>();
        StringBuilder property = new StringBuilder();
        StringBuilder value = new StringBuilder();

        try (FileInputStream input = new FileInputStream(file)) {
            boolean isProperty = false;
            int ch;
            while ((ch = input.read()) != -1) {
                if (ch == '\n') {
                    profileProperties.put(property.toString().trim(), value.toString().trim());
                    property = new StringBuilder();
                    value = new StringBuilder();
                    isProperty = false;
                }

                if (ch == ':' && !isProperty) {
                    isProperty = true;
                    continue;
                }

                if (isProperty) value.append((char) ch);
                else property.append((char) ch);

            }

        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File Not Found, ", e);
        }
        catch (IOException e) {
            e.printStackTrace();
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
