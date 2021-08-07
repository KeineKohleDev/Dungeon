package me.keinekohle.net.utilities;

import java.io.*;
import java.util.Properties;

public final class Language {
    
    public static String noPermissions;
    public static String classGotAutoSelected;
    public static String classItemsReceived;

    //PlayerJoinEvent
    public static String playerJoinMessage;
    public static String playerLeaveMessage;

    public static String purchasedClass;
    public static String notEnoughCoins;
    public static String upgradeClass;

    public static String rankNeeded;


    public static String noClassesBoughtYet;
    public static String previewStart;
    public static String previewEnd;

    public static String armorStandName;

    //Settings
    public static String autoSelectClassDisplayname;

    public static String voteForDifficulty;

    public static String notEnoughPlayersToStartTheGame;

    private Language() {
        throw new IllegalStateException("Utility class");
    }

    public static void createLanguageProperties() {
        try {
            OutputStream outputStream = new FileOutputStream("plugins/Dungeon/language.properties");

            Properties languageProperties = new Properties();

            languageProperties.setProperty("noPermissions", "#FC1C3FYou don't have the permission to perform this command!");
            languageProperties.setProperty("classGotAutoSelected", "The Class you used the last time got selected for you!");
            languageProperties.setProperty("classItemsReceived", "You have received the class %classname%, level %classlevel%!");

            languageProperties.setProperty("playerJoinMessage", "#51AAFC%player% §8[#44FC44+§8]");
            languageProperties.setProperty("playerLeaveMessage", "#51AAFC%player% §8[#E83139-§8]");

            languageProperties.setProperty("purchasedClass", "You have purchased the class %classname% for %coins% coins!");
            languageProperties.setProperty("notEnoughCoins", "You do not have enough coins!");
            languageProperties.setProperty("upgradeClass", "You have upgraded the class %classname% from level %fromlevel% to level %tolevel% for %coins% coins!");
            languageProperties.setProperty("rankNeeded", "You need the rank %servergroup% to get access to this content!");
            languageProperties.setProperty("noClassesBoughtYet", "You have to purchase a class before you can apply upgrades!");

            languageProperties.setProperty("previewStart", "If you move, the preview will stop.");
            languageProperties.setProperty("previewEnd", "Preview ended because you moved!");

            languageProperties.setProperty("armorStandName", "Class Selection");

            languageProperties.setProperty("voteForDifficulty", "You have voted for %vote%");

            //Player settings
            languageProperties.setProperty("autoSelectClassDisplayname", "#183918Auto select class");

            languageProperties.setProperty("notEnoughPlayersToStartTheGame", "There are not enough players to start the game!");

            languageProperties.store(outputStream, "This is the Default language file copy it and add Locale");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadLanguageProperties() {
        try {
            InputStream outputStream = new FileInputStream("plugins/Dungeon/language.properties");

            Properties languageProperties = new Properties();
            languageProperties.load(outputStream);

            noPermissions = Replacements.replaceHexColors(languageProperties.getProperty("noPermissions"));
            classGotAutoSelected = Replacements.replaceHexColors(languageProperties.getProperty("classGotAutoSelected"));
            classItemsReceived = Replacements.replaceHexColors(languageProperties.getProperty("classItemsReceived"));
            
            playerJoinMessage = Replacements.replaceHexColors(languageProperties.getProperty("playerJoinMessage"));
            playerLeaveMessage = Replacements.replaceHexColors(languageProperties.getProperty("playerLeaveMessage"));

            purchasedClass = Replacements.replaceHexColors(languageProperties.getProperty("purchasedClass"));
            notEnoughCoins = Replacements.replaceHexColors(languageProperties.getProperty("notEnoughCoins"));
            upgradeClass = Replacements.replaceHexColors(languageProperties.getProperty("upgradeClass"));
            rankNeeded = Replacements.replaceHexColors(languageProperties.getProperty("rankNeeded"));
            noClassesBoughtYet = Replacements.replaceHexColors(languageProperties.getProperty("noClassesBoughtYet"));

            previewStart = Replacements.replaceHexColors(languageProperties.getProperty("previewStart"));
            previewEnd = Replacements.replaceHexColors(languageProperties.getProperty("previewEnd"));

            armorStandName = Replacements.replaceHexColors(languageProperties.getProperty("armorStandName"));

            voteForDifficulty = Replacements.replaceHexColors(languageProperties.getProperty("voteForDifficulty"));

            notEnoughPlayersToStartTheGame = Replacements.replaceHexColors(languageProperties.getProperty("notEnoughPlayersToStartTheGame"));

            autoSelectClassDisplayname = Replacements.replaceHexColors(languageProperties.getProperty("autoSelectClassDisplayname"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
