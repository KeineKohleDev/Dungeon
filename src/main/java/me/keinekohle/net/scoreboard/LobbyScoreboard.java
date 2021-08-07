package me.keinekohle.net.scoreboard;

import com.connorlinfoot.titleapi.TitleAPI;
import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public final class LobbyScoreboard {

    private LobbyScoreboard() {
        throw new IllegalStateException("Utility class");
    }

    public static void sendLobbyScoreboard() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            TitleAPI.sendTabTitle(player, GlobalUtilities.getColorByName(KeineKohle.DISPLAYNAME) + ChatColor.BOLD + KeineKohle.DISPLAYNAME, "§b" + Bukkit.getOnlinePlayers().size() + "§8/§b4");
            //-- Get the scoreboard manager --
            ScoreboardManager manager = Bukkit.getScoreboardManager();

            //-- create a new scoreboard --
            Scoreboard scoreboard = manager.getNewScoreboard();

            //-- register new objectiv --
            Objective objective = scoreboard.registerNewObjective("display", "dummy", GlobalUtilities.getColorByName(KeineKohle.DISPLAYNAME) + ChatColor.BOLD + KeineKohle.DISPLAYNAME);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            //-- Set scores --
            //Space
            int score = 9;
            setScore(objective, ChatColor.DARK_PURPLE + "", score);
            score -= 1;

            //Class
            setScore(objective, "Selected Class", score);
            score -= 1;

            Team classTeam = scoreboard.registerNewTeam("class");
            classTeam.setPrefix(LobbyScoreboardUtilities.getClassAndColor(player));
            classTeam.addEntry("§a");
            setScore(objective, "§a", score);
            score -= 1;

            //Space
            setScore(objective, ChatColor.DARK_AQUA + "", score);
            score -= 1;

            //Difficulty
            setScore(objective, "Difficulty", score);
            score -= 1;

            setScore(objective, "§2Normal", score);
            score -= 1;

            //Space
            setScore(objective, ChatColor.DARK_BLUE + "", score);
            score -= 1;

            //Coins
            setScore(objective, "Coins", score);
            score -= 1;

            Team playerCoins = scoreboard.registerNewTeam("playerCoins");
            playerCoins.setPrefix(GlobalUtilities.getColorByName(Variables.COINS) + LobbyScoreboardUtilities.getCoins(player));
            playerCoins.addEntry("§e");
            setScore(objective, "§e", score);

            registerClassesAsTeams(scoreboard);

            //-- send the scoreboard to the player --
            player.setScoreboard(scoreboard);
        }
    }

    private static void setScore(Objective objective, String entry, int score) {
        objective.getScore(entry).setScore(score);
    }

    private static void registerClassesAsTeams(Scoreboard scoreboard) {
        MySQLMethods mySQLMethods = new MySQLMethods();
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setCustomName(ChatColor.of("#DEFAE2") + all.getName());
            all.setCustomNameVisible(true);
            scoreboard.registerNewTeam(all.getName());
            String title = mySQLMethods.selectCurrentPlayerTitle(all);
            if (title != null) {
                ChatColor titleColor = mySQLMethods.selectTitleColor(title);
                scoreboard.getTeam(all.getName()).setSuffix(" " + titleColor + title);
            }
            if (KeineKohle.SELECTEDCLASS.containsKey(all)) {
                ClassFabric classFabric = KeineKohle.SELECTEDCLASS.get(all);
                scoreboard.getTeam(all.getName()).setPrefix(classFabric.getClassColor() + classFabric.getClassName() + " §a");
            } else {
                scoreboard.getTeam(all.getName()).setPrefix(GlobalUtilities.getColorByName(Classes.NONECLASS) + Classes.NONECLASS + " ");
            }

            scoreboard.getTeam(all.getName()).addEntry(all.getName());
        }


    }
}
