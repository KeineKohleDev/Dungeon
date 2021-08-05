package me.keinekohle.net.main;

import me.keinekohle.net.commands.CmdDungeon;
import me.keinekohle.net.listeners.*;
import me.keinekohle.net.listeners.cmd.ListenerCmdClassesClick;
import me.keinekohle.net.listeners.lobby.*;
import me.keinekohle.net.listeners.setup.ListenerSetupCreateNewClass;
import me.keinekohle.net.listeners.setup.ListenerSetupCreateNewClassLevel;
import me.keinekohle.net.listeners.setup.ListenerSetupInventoryClickEvent;
import me.keinekohle.net.utilities.ClassFabric;
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
    public static final HashMap<Player, ClassFabric> SETUPMODE = new HashMap<>();
    public static final List<Player> INPRIVIEW = new ArrayList<>();
    public static final List<Player> BUILDMODE = new ArrayList<>();
    public static final HashMap<Player, String> VOTEDIFFICULTY = new HashMap<>();

    //-- Player lobby hotbar --
    public static final String CHESTDISPLAYNAME = "Shop";
    public static final String COMPARATORDISPLAYNAME  = "Difficulty";
    public static final String ANVILDISPLAYNAME  = "Upgrade";
    public static final String BOOKDISPLAYNAME  = "Settings";

    public static final String ABILITIESDISPLAYNAME  = "Abilities";

    public static final String SHOPBOOSTER = "Booster";
    public static final String SHOPTITLES = "Titles";

    public static  int connections = 0;

    //BRACKETS
    public static final String BRACKETSCOLOR = "BRACKETSCOLOR";
    public static final String CHATCOLOR = "CHATCOLOR";

    public static final String DISPLAYNAME = "Dungeon";

    public static final String DIFFICULTYEASY = "Easy";
    public static final String DIFFICULTYNORMAL = "Normal";
    public static final String DIFFICULTYHARD = "Hard";
    public static final String DIFFICULTYVERYHARD = "Very hard";

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
        System.getLogger(Bukkit.getVersion());

    }

    private void loadCommonCommands() {
        getCommand("dungeon").setExecutor(new CmdDungeon());
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

        //cmd /dungeon
        pm.registerEvents(new ListenerCmdClassesClick(), this);

        //Lobby only
        pm.registerEvents(new ListenerLobbyPlayerInteractEvent(), this);
        pm.registerEvents(new ListenerLobbyEntityDamageByEntityEvent(), this);
        pm.registerEvents(new ListenerLobbyEntityDamageEvent(), this);
        pm.registerEvents(new ListenerLobbyPlayerMoveEvent(), this);
        pm.registerEvents(new ListenerLobbyShopClick(), this);
        pm.registerEvents(new ListenerLobbyUpgradeClick(), this);
        pm.registerEvents(new ListenerLobbyDifficultyVoteClick(), this);
        pm.registerEvents(new ListenerLobbyInventoryCloseEvent(), this);

        //Setup only
        pm.registerEvents(new ListenerSetupCreateNewClass(), this);
        pm.registerEvents(new ListenerSetupInventoryClickEvent(), this);
        pm.registerEvents(new ListenerSetupCreateNewClassLevel(), this);
    }

}
