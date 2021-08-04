package me.keinekohle.net.main;

import me.keinekohle.net.commands.CmdDungeon;
import me.keinekohle.net.commands.tabcompleter.TabCompletCmdDungeon;
import me.keinekohle.net.listeners.*;
import me.keinekohle.net.listeners.lobby.*;
import me.keinekohle.net.listeners.setup.ListenerSetupAsyncPlayerChatEvent;
import me.keinekohle.net.listeners.setup.ListenerSetupInventoryClickEvent;
import me.keinekohle.net.utilities.CreateNewClass;
import me.keinekohle.net.utilities.GlobalUtilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeineKohle extends JavaPlugin {

    //message PREFIX
    public static final String PREFIX = GlobalUtilities.surroundWithBracketsAndColorCodes(GlobalUtilities.getColorByName(KeineKohle.DISPLAYNAME) + KeineKohle.DISPLAYNAME);
    public static final String PERMISSIONPREFIX = "Dungeon.";

    //-- Lists --
    //buildmode
    public static final HashMap<Player, CreateNewClass> PLAYERCREATENEWCLASS = new HashMap<>();
    public static final List<Player> INPRIVIEW = new ArrayList<>();
    public static final List<Player> BUILDMODE = new ArrayList<>();

    //-- Player lobby hotbar --
    public static final String CHESTDISPLAYNAME = "Shop";
    public static final String COMPARATORDISPLAYNAME  = "Difficulty";
    public static final String ANVILDISPLAYNAME  = "Upgrade";
    public static final String BOOKDISPLAYNAME  = "Settings";

    public static final String ABILITIESDISPLAYNAME  = "Abilities";

    //BRACKETS
    public static final String BRACKETSCOLOR = "BRACKETSCOLOR";
    public static final String CHATCOLOR = "CHATCOLOR";

    public static final String DISPLAYNAME = "Dungeon";

    public static final String DIFICULTYEASY = "Easy";
    public static final String DIFICULTYNORMAL = "Normal";
    public static final String DIFICULTYHARD = "Hard";
    public static final String DIFICULTYVERYHARD = "Very hard";

    //-- Global --
    public static final String COINS = "Coins";

    //Stage
    public static final boolean INGAME = false;


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
        pm.registerEvents(new ListenerPlayerQuitEvent(), this);
        pm.registerEvents(new ListenerEntityPickupItemEvent(), this);
        pm.registerEvents(new ListenerPlayerDropItemEvent(), this);
        pm.registerEvents(new ListenerBlockBreakEvent(), this);
        pm.registerEvents(new ListenerBlockPlaceEvent(), this);
        pm.registerEvents(new ListenerFoodLevelChangeEvent(), this);

        //Lobby only
        pm.registerEvents(new ListenerLobbyPlayerInteractEvent(), this);
        pm.registerEvents(new ListenerLobbyEntityDamageByEntityEvent(), this);
        pm.registerEvents(new ListenerLobbyEntityDamageEvent(), this);
        pm.registerEvents(new ListenerLobbyInventoryClickEvent(), this);
        pm.registerEvents(new ListenerLobbyPlayerMoveEvent(), this);

        //Setup only
        pm.registerEvents(new ListenerSetupAsyncPlayerChatEvent(), this);
        pm.registerEvents(new ListenerSetupInventoryClickEvent(), this);
    }

}
