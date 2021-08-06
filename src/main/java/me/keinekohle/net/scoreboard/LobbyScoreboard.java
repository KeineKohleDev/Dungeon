package me.keinekohle.net.scoreboard;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.Classes;
import me.keinekohle.net.utilities.LobbyScoreboardUtilities;
import me.keinekohle.net.utilities.GlobalUtilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public final class LobbyScoreboard {

    private LobbyScoreboard() {
        throw new IllegalStateException("Utility class");
    }

    public static void sendLobbyScoreboard(Player player) {
        MySQLMethods mySQLMethods = new MySQLMethods();
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
        setScore(objective, "§aSelected Class", score);
        score -= 1;

        setScore(objective,  LobbyScoreboardUtilities.getClassAndColor(player), score);
        score -= 1;

        //Space
        setScore(objective, ChatColor.DARK_AQUA + "", score);
        score -= 1;

        //Difficulty
        setScore(objective, GlobalUtilities.getColorByName(KeineKohle.COMPARATORDISPLAYNAME) + KeineKohle.COMPARATORDISPLAYNAME, score);
        score -= 1;

        setScore(objective, "§2Normal", score);
        score -= 1;

        //Space
        setScore(objective, ChatColor.DARK_BLUE + "", score);
        score -= 1;

        //Coins
        setScore(objective, GlobalUtilities.getColorByName(KeineKohle.COINS) + KeineKohle.COINS, score);
        score -= 1;

        setScore(objective,  GlobalUtilities.getColorByName(KeineKohle.COINS) + LobbyScoreboardUtilities.getCoins(player), score);

        //-- send the scoreboard to the player --
        player.setScoreboard(scoreboard);
    }

    private static void setScore(Objective objective, String entry, int score) {
        objective.getScore(entry).setScore(score);
    }
}
