package me.keinekohle.net.utilities;

import org.bukkit.Material;

public class Variables {

    public static final String BRACKETSCOLOR = "BRACKETSCOLOR";

    public static final String COINS = "Coins";
    public static final String CHESTDISPLAYNAME = "Shop";

    public static final String SHOPBOOSTER = "Booster";
    public static final String SHOPTITLES = "Titles";

    public static final String COMPARATORDISPLAYNAME  = "Difficulty";

    public static final String DIFFICULTYEASY = "Easy";
    public static final String DIFFICULTYNORMAL = "Normal";
    public static final String DIFFICULTYHARD = "Hard";
    public static final String DIFFICULTYVERYHARD = "Very hard";
    public static final String ANVILDISPLAYNAME  = "Upgrade";
    public static final String ENDERCHESTNAME  = "My Inventory";
    public static final String BOOKDISPLAYNAME  = "Settings";

    public static final String ABILITIESDISPLAYNAME  = "Abilities";

    //PlayerSettings
    public static final String AUTOSELECTCLASS = "Auto select class";
    public static final Boolean AUTOSELECTCLASSVALUE = true;
    public static final Material AUTOSELECTCLASSICON = Material.CHEST_MINECART;

    private Variables() {
        throw new IllegalStateException("Utility class");
    }

}
