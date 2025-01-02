package de.essagl.tuya.aircal.adapter.web.mvc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUpdater {

    public static void updateProperties(String filePath, Properties newProperties) throws IOException {
        Properties properties = new Properties();

        // Load existing properties from the file
        try (FileInputStream in = new FileInputStream(filePath)) {
            properties.load(in);
        }

        // Overwrite existing properties with new values
        for (String key : newProperties.stringPropertyNames()) {
            properties.setProperty(key, newProperties.getProperty(key));
        }

        // Store the updated properties back to the file
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            properties.store(out, "Updated properties");
        }
    }


}
