package me.keinekohle.net.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class LobbyScoreboard {

    private static String displayName = "§6§lDungeon§";

    public static void sendLobbyScoreboard(Player player) {
        //-- Get the scoreboard manager --
        ScoreboardManager manager = Bukkit.getScoreboardManager();

        //-- create a new scoreboard --
        Scoreboard scoreboard = manager.getNewScoreboard();

        //-- register new objectiv --
        Objective objective = scoreboard.registerNewObjective("display", "dummy", displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //-- Set scores --
        //Space
        setScore(objective, "§a", 0);

        //Class
        setScore(objective, "§aSelected Class", 1);
        setScore(objective, "§8None", 2);

        //Space
        setScore(objective, "§b", 3);

        //Difficulty
        setScore(objective, "§cDifficulty", 4);
        setScore(objective, "§2Normal", 5);

        //Coins
        setScore(objective, "§6Coins", 4);
        setScore(objective, "§80", 5);

        //-- send the scoreboard to the player --
        player.setScoreboard(scoreboard);
    }

    private static void setScore(Objective objective, String entry, int score) {
        objective.getScore(entry).setScore(score);
    }
}
