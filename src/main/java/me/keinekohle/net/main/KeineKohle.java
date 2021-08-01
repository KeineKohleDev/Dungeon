package me.keinekohle.net.main;

import me.keinekohle.net.commands.TabCompleter.TabComplet_cmd_dungeon;
import me.keinekohle.net.commands.cmd_dungeon;
import me.keinekohle.net.license.ServerIdentifier;
import me.keinekohle.net.listeners.Listener_DropItemEvent;
import me.keinekohle.net.listeners.Listener_PlayerJoinEvent;
import me.keinekohle.net.stuff.ClassStuff;
import me.keinekohle.net.stuff.Stuff;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class KeineKohle extends JavaPlugin {

    //message prefix
    public static final String prefix = Stuff.surroundWithBracketsAndColorCodes(Stuff.getColorByName(KeineKohle.DISPLAYNAME) + KeineKohle.DISPLAYNAME);
    public static final String permission_prefix = "Dungeon.";

    //-- Hashmaps --
    //classes
    public static final HashMap<String, String> class_prefix = new HashMap<>();


    //-- ArrayLists --
    //buildmode
    public static ArrayList<Player> buildmode = new ArrayList<Player>();

    //-- Player lobby hotbar --
    //Chest
    public static final String CHEST_DISPLAYNAME = "Shop";
    //Comperator
    public static final String COMPARATOR_DISPLAYNAME  = "Difficulty";
    //Anvil
    public static final String ANVIL_DISPLAYNAME  = "Upgrade";

    public static final String DISPLAYNAME = "Dungeon";

    //-- Global --
    public static final String COINS = "Coins";

    //Stage
    public static boolean inGame = false;


    //server identifier

    //loaded on startup
    @Override
    public void onEnable() {
        //-- Classes --
        ClassStuff.registerAllStandardClasses();
        // NEED TO BE ADDED: LICENSE CHECK!
        ClassStuff.registerAllAddonClasses();

        loadCommonListeners();
        loadCommonCommands();
        loadCommonTabCompleter();

    }

    private void loadCommonCommands() {
        getCommand("dungeon").setExecutor(new cmd_dungeon());
    }

    private void loadCommonTabCompleter() {
        getCommand("dungeon").setTabCompleter(new TabComplet_cmd_dungeon());
    }

    private void loadCommonListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Listener_PlayerJoinEvent(), this);
        pm.registerEvents(new Listener_DropItemEvent(), this);
    }
}
