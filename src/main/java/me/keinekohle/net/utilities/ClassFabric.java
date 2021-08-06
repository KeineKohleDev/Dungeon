package me.keinekohle.net.utilities;

import me.keinekohle.net.main.KeineKohle;
import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ClassFabric {

    private final Integer MODECREATENEWCLASS = 0;
    private final Integer MODECREATENEWCLASSLEVEL = 1;

    private Integer stage = 0;
    private Integer stageMax = 7;
    private Integer mode;
    private String className;
    private Integer classLevel;
    private Integer classCoast;
    private String classColor;
    private String icon;
    private String group;
    private List<String> abilities = new ArrayList<>();
    private Inventory inventory;

    public ClassFabric(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
        putPlayerIntoSetupMode(player);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    private void putPlayerIntoSetupMode(Player player) {
        KeineKohle.SETUPMODE.put(player, this);
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getMODECREATENEWCLASSLEVEL() {
        return MODECREATENEWCLASSLEVEL;
    }

    public Integer getMODECREATENEWCLASS() {
        return MODECREATENEWCLASS;
    }

    public Integer getStageMax() {
        return stageMax;
    }

    public void setStageMax(Integer stage_max) {
        this.stageMax = stage_max;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer state) {
        this.stage = state;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public String getClassColor() {
        return classColor;
    }

    public void setClassColor(String classColor) {
        this.classColor = classColor;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(Integer classLevel) {
        this.classLevel = classLevel;
    }

    public Integer getClassCoast() {
        return classCoast;
    }

    public void setClassCoast(Integer classCoast) {
        this.classCoast = classCoast;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void SaveClass() {
        MySQLMethods mySQLMethods = new MySQLMethods();
        mySQLMethods.insertClass(this.getClassName(), this.getClassLevel(), this.getClassCoast(), this.getClassColor(), this.getIcon(), this.getAbilities().toString(), this.getGroup());
        savePlayerInventoryToClassLevel(mySQLMethods);
    }

    private void savePlayerInventoryToClassLevel(MySQLMethods mySQLMethods) {
        int slot = 0;
        for (ItemStack itemStack : this.getInventory().getContents()) {
            if (itemStack != null) {
                YamlConfiguration configuration = new YamlConfiguration();
                configuration.set("i", itemStack);
                String itemstackYAML = configuration.saveToString().replace("'", "-|-");
                mySQLMethods.insertClassItemstack(this.getClassName(), this.getClassLevel(), slot, itemstackYAML);
            }
            slot++;
        }
    }


}
