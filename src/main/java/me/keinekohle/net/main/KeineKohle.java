package me.keinekohle.net.main;

import me.keinekohle.net.commands.CmdDungeon;
import me.keinekohle.net.listeners.*;
import me.keinekohle.net.listeners.cmd.ListenerCmdClassesClick;
import me.keinekohle.net.listeners.lobby.*;
import me.keinekohle.net.listeners.setup.ListenerSetupCreateNewClass;
import me.keinekohle.net.listeners.setup.ListenerSetupCreateNewClassLevel;
import me.keinekohle.net.listeners.setup.ListenerSetupInventoryClickEvent;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.scheduler.LobbyCountdown;
import me.keinekohle.net.utilities.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeineKohle extends JavaPlugin {

    public static final String PREFIX = GlobalUtilities.surroundWithBracketsAndColorCodes(GlobalUtilities.getColorByName(KeineKohle.DISPLAYNAME) + KeineKohle.DISPLAYNAME);
    public static final String PERMISSIONPREFIX = "Dungeon.";
    public static final String DISPLAYNAME = "Dungeon";
    public static ChatColor CHATCOLOR = ChatColor.of("#FCE23A");

    public static final HashMap<Player, ClassFabric> SETUPMODE = new HashMap<>();
    public static final List<Player> INPRIVIEW = new ArrayList<>();
    public static final List<Player> BUILDMODE = new ArrayList<>();
    public static final HashMap<Player, String> VOTEDIFFICULTY = new HashMap<>();
    public static final HashMap<Player, ClassFabric> SELECTEDCLASS = new HashMap<>();

    public static int connections = 0;
    public static boolean inGame = false;

    @Override
    public void onEnable() {
        // NEED TO BE ADDED: LICENSE CHECK!

        Language.createLanguageProperties();
        Language.loadLanguageProperties();

        loadCommonListeners();
        loadCommonCommands();
        System.getLogger(Bukkit.getVersion());

        //LobbyCountdown.startLobbyCountdown();

        MySQLMethods mySQLMethods = new MySQLMethods();
        mySQLMethods.createTablesIfNotExists();
        ClassSeletionArmorStand.spawnClassSelectionArmorStand();

        // bStats
        int classes = mySQLMethods.selectAllClasses().size();
        System.out.println("Classes: "+classes);
        int pluginId = 12348;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("classes", () -> {return String.valueOf(classes);}));

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
        pm.registerEvents(new ListenerLobbyClassSeletionClick(), this);
        pm.registerEvents(new ListenerLobbyDifficultyVoteClick(), this);
        pm.registerEvents(new ListenerLobbyInventoryCloseEvent(), this);
        pm.registerEvents(new ListenerLobbyPlayerInteractAtEntityEvent(), this);
        pm.registerEvents(new ListenerLobbySettingsClick(), this);


        //Setup only
        pm.registerEvents(new ListenerSetupCreateNewClass(), this);
        pm.registerEvents(new ListenerSetupInventoryClickEvent(), this);
        pm.registerEvents(new ListenerSetupCreateNewClassLevel(), this);
    }

}
