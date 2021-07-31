package me.keinekohle.net.stuff;

import me.keinekohle.net.main.KeineKohle;

public class ClassStuff {

    public static void registerAllStandardClasses() {
        KeineKohle.class_prefix.put("Tank", "§8Tank");
        KeineKohle.class_prefix.put("Healer", "§cHealer");
        KeineKohle.class_prefix.put("Survivor", "§3Survivor");
        KeineKohle.class_prefix.put("Archer", "§bArcher");
    }

    public static void registerAllAddonClasses() {
        KeineKohle.class_prefix.put("Bomber", "§0Bomber");
    }
}
