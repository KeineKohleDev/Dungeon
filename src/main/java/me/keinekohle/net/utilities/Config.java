package me.keinekohle.net.utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Config {

    private Config() {
        throw new IllegalStateException("Utility class");
    }

    private static Properties config = new Properties();

    public static void createProperties() {
        try {
            OutputStream outputStream = new FileOutputStream("plugins/Dungeon/config.properties");

            Properties languageProperties = new Properties();

            languageProperties.setProperty("voteForDifficulty", "You have voted for %vote%");

            languageProperties.store(outputStream, "This is the Default language file copy it and add Locale");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
