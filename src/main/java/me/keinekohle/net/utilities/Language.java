package me.keinekohle.net.utilities;

public final class Language {

    //cmd_dungeon
    public static String cmdDungeonBuildnodeOn = Replacements.replaceHexColors("The Buildmode is now §aenabled!");
    public static String cmdDungeonBuildnodeOff = Replacements.replaceHexColors("The Buildmode is now §cdisabeled!");

    public static String noPermissions = Replacements.replaceHexColors("#FC1C3FYou don't have the permission to perform this command!");

    public static String classGotAutoSelected = Replacements.replaceHexColors("The Class you used the last time got auto selected!");

    public static String classItemsReceived = Replacements.replaceHexColors("You received the class %classname% level %classlevel%!");

    //PlayerJoinEvent
    public static String playerJoinMessage = Replacements.replaceHexColors("#51AAFC%player% §8[#44FC44+§8]");

    private Language() {
        throw new IllegalStateException("Utility class");
    }
}
