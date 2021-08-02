package me.keinekohle.net.main;

import me.keinekohle.net.commands.CmdDungeon;
import me.keinekohle.net.commands.tabcompleter.TabCompletCmdDungeon;
import me.keinekohle.net.listeners.*;
import me.keinekohle.net.listeners.lobby.ListenerLobbyEntityDamageByEntityEvent;
import me.keinekohle.net.listeners.lobby.ListenerLobbyEntityDamageEvent;
import me.keinekohle.net.listeners.lobby.ListenerLobbyPlayerInteractEvent;
import me.keinekohle.net.utilities.GlobalUtilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class KeineKohle extends JavaPlugin {

    //message PREFIX
    public static final String PREFIX = GlobalUtilities.surroundWithBracketsAndColorCodes(GlobalUtilities.getColorByName(KeineKohle.DISPLAYNAME) + KeineKohle.DISPLAYNAME);
    public static final String PERMISSIONPREFIX = "Dungeon.";

    //-- ArrayLists --
    //buildmode
    public static final List<Player> buildmode = new ArrayList<>();

    //-- Player lobby hotbar --
    public static final String CHESTDISPLAYNAME = "Shop";
    public static final String COMPARATORDISPLAYNAME  = "Difficulty";
    public static final String ANVILDISPLAYNAME  = "Upgrade";
    public static final String BOOKDISPLAYNAME  = "Settings";


    //BRACKETS
    public static final String BRACKETSCOLOR = "BRACKETSCOLOR";
    public static final String CHATCOLOR = "CHATCOLOR";

    public static final String DISPLAYNAME = "Dungeon";

    //-- Global --
    public static final String COINS = "Coins";

    //Stage
    public static boolean inGame = false;


    //server identifier

    //loaded on startup
    @Override
    public void onEnable() {
        // NEED TO BE ADDED: LICENSE CHECK!

        loadCommonListeners();
        loadCommonCommands();
        loadCommonTabCompleter();
        System.getLogger(Bukkit.getVersion());

    }

    private void loadCommonCommands() {
        getCommand("dungeon").setExecutor(new CmdDungeon());
    }

    private void loadCommonTabCompleter() {
        getCommand("dungeon").setTabCompleter(new TabCompletCmdDungeon());
    }

    private void loadCommonListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        //Global
        pm.registerEvents(new ListenerPlayerJoinEvent(), this);
        pm.registerEvents(new ListenerEntityPickupItemEvent(), this);
        pm.registerEvents(new ListenerPlayerDropItemEvent(), this);
        pm.registerEvents(new ListenerBlockBreakEvent(), this);
        pm.registerEvents(new ListenerBlockPlaceEvent(), this);
        pm.registerEvents(new ListenerFoodLevelChangeEvent(), this);

        //Lobby only
        pm.registerEvents(new ListenerLobbyPlayerInteractEvent(), this);
        pm.registerEvents(new ListenerLobbyEntityDamageByEntityEvent(), this);
        pm.registerEvents(new ListenerLobbyEntityDamageEvent(), this);
    }

}
