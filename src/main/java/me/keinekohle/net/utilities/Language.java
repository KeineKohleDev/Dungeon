package me.keinekohle.net.utilities;

public final class Language {

    //cmd_dungeon
    public static String cmdDungeonBuildnodeOn = Replacements.replaceHexColors("The Buildmode is now §aenabled!");
    public static String cmdDungeonBuildnodeOff = Replacements.replaceHexColors("The Buildmode is now §cdisabeled!");

    public static String noPermissions = Replacements.replaceHexColors("#FC1C3FYou don't have the permission to perform this command!");

    public static String classGotAutoSelected = Replacements.replaceHexColors("The Class you used the last time got selected for you!");

    public static String classItemsReceived = Replacements.replaceHexColors("You have received the class %classname%, level %classlevel%!");

    //PlayerJoinEvent
    public static String playerJoinMessage = Replacements.replaceHexColors("#51AAFC%player% §8[#44FC44+§8]");
    public static String playerLeaveMessage = Replacements.replaceHexColors("#51AAFC%player% §8[#E83139-§8]");

    public static final String PURCHASEDCLASS = Replacements.replaceHexColors("You have purchased the class %classname% for %coins% coins!");
    public static final String NOTENOUGHTCOINS = Replacements.replaceHexColors("You do not have enough coins!");
    public static final String UPGRADEDCLASS = Replacements.replaceHexColors("You have upgraded the class %classname% from level %fromlevel% to level %tolevel% for %coins% coins!");


    public static final String NOCLASSESBOUGHTYET = Replacements.replaceHexColors("You have to purchase a class before you can apply upgrades!");
    public static final String PREVIEWSTART = Replacements.replaceHexColors("If you move, the preview will stop.");
    public static final String PREVIEWEND = Replacements.replaceHexColors("Preview ended because you moved! ");

    public static final String ARMORSTANDNAME = "Class Selection";

    public static final String VOTEDFORDIFFICULTY = Replacements.replaceHexColors("You have voted for %vote%");

    private Language() {
        throw new IllegalStateException("Utility class");
    }
}
