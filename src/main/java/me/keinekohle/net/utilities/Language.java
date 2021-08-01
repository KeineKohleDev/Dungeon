package me.keinekohle.net.utilities;

public class Language {

    private Language() {
        throw new IllegalStateException("Utility class");
    }

    //cmd_dungeon
    public static String cmdDungeonBuildnodeOn = Replacements.replaceHexColors("The Buildmode is now §aenabled!");
    public static String cmdDungeonBuildnodeOff = Replacements.replaceHexColors("The Buildmode is now §cdisabeled!");

    public static String noPermissions = Replacements.replaceHexColors("#FC1C3FYou don't have the permission to perform this command!");

    //PlayerJoinEvent
    public static String playerJoinMessage = Replacements.replaceHexColors("#51AAFC%player% §8[#44FC44+§8]");
}
