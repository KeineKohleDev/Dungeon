package me.keinekohle.net.utilities;

import me.keinekohle.net.mysql.MySQLMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Abilites {
    
    public static final String SELECT = "§aSelect (Left click)";
    public static final String DESELECT = "§cDeselect (Right click)";

    public static final String FIRE = "Fire";
    public static final List<String> FIRELOREDESCRIPTION = Arrays.asList("Sets the enemies around the player on fire", SELECT);
    public static final String FIREGRENADE = "Fire grenade";
    public static final List<String> FIREGRENADELOREDESCRIPTION = Arrays.asList("Can throw a grenade and where it lands the enemies are set on fire", SELECT);

    public static final String EARTH = "Earth";
    public static final List<String> EARTHLOREDESCRIPTION = Arrays.asList("Damage x2 against enemies who can normaly spawn in the world", SELECT);
    public static final String HELL = "Hell";
    public static final List<String> HELLLOREDESCRIPTION = Arrays.asList("Damage x2 against enemies who can normaly spawn in the nether", SELECT);

    public static final String HEALBEACON = "Heal beacon";
    public static final List<String> HEALBEACONLOREDESCRIPTION = Arrays.asList("Can place a heal beacon that heals the teammates in an area", SELECT);
    public static final String HEALGRENADE = "Heal grenade";
    public static final List<String> HEALGRENADELOREDESCRIPTION = Arrays.asList("Can throw a heal grenade and where it lands teammates are healed", SELECT);

    public static final String GRENADE = "Grenade";
    public static final List<String> GRENADELOREDESCRIPTION = Arrays.asList("Can throw a grenade and where it lands the enemies will get damage", SELECT);
    public static final String C4 = "C4";
    public static final List<String> C4LOREDESCRIPTION = Arrays.asList("Can place c4 and detonate from a distance", SELECT);

    public static final String FREEZE = "Freeze";
    public static final List<String> FREEZELOREDESCRIPTION = Arrays.asList("Freezes the enemies around the player", SELECT);
    public static final String FREEZEGRANDE = "Freeze grenade";
    public static final List<String> FREEZEGRANDELOREDESCRIPTION = Arrays.asList("Freezes the enemies at the point of landing", SELECT);

    public static final String WEAKNESS = "Weakness";
    public static final List<String> WEAKNESSLOREDESCRIPTION = Arrays.asList("Weakens the enemies near the player for a short time", SELECT);
    public static final String WWEAKNESSGRANDE = "Weakness grenade";
    public static final List<String> WWEAKNESSGRANDELOREDESCRIPTION = Arrays.asList("Can throw a weakens grenade and where it lands the enemies will get weakened.", SELECT);

    private Abilites() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String> selectClassAbilities(String className) {
        List<String> abilities = new ArrayList<>();
        MySQLMethods mySQLMethods = new MySQLMethods();
        String abilitiesFormat = mySQLMethods.selectAbilitiesFromClasses(className, 1).replace("[", "").replace("]", "");
        abilities.add(abilitiesFormat.substring(0, abilitiesFormat.indexOf(",")));
        abilities.add(abilitiesFormat.substring(abilitiesFormat.indexOf(",") + 2));
        return abilities;
    }
}
