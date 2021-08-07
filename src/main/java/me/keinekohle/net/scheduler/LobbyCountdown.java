package me.keinekohle.net.scheduler;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import me.keinekohle.net.utilities.ClassFabric;
import me.keinekohle.net.utilities.Language;
import me.keinekohle.net.utilities.PlayerUtilities;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class LobbyCountdown {

    private static int time = 60;

    private LobbyCountdown() {
        throw new IllegalStateException("Utility class");
    }

    public static void startLobbyCountdown() {
        if (Bukkit.getOnlinePlayers().size() >= 2) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(KeineKohle.getPlugin(KeineKohle.class), () -> {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.setLevel(time);
                    all.setExp((float) time / 60);
                    if (time == 20 || time == 15 || time == 10 || time <= 5 && time != 0) {
                        all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                    }
                }

                if (time == 0) {
                    //Teleport
                    MySQLMethods mySQLMethods = new MySQLMethods();
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        ClassFabric classFabric = KeineKohle.SELECTEDCLASS.get(all);
                        all.playSound(all.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                        KeineKohle.INPRIVIEW.clear();
                        PlayerUtilities.clearPlayerPotionEffects(all);
                        PlayerUtilities.clearPlayerInventory(all);
                        classFabric.giveClassItems();
                    }
                    KeineKohle.inGame = true;
                    Bukkit.getScheduler().cancelTasks(KeineKohle.getPlugin(KeineKohle.class));
                }
                time--;
            }, 20, 20);
        } else {
            Bukkit.broadcastMessage(KeineKohle.PREFIX + KeineKohle.CHATCOLOR + Language.notEnoughPlayersToStartTheGame);
        }
    }
}
