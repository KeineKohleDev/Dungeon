package me.keinekohle.net.utilities;

import me.keinekohle.net.mysql.MySQLMethods;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class CreateNewClass {

    private Integer stage = 0;
    private Integer stageMax = 10;
    private String className;
    private Integer classLevel;
    private Integer classCoast;
    private String classColor;
    private String icon;
    private List<String> abilities = new ArrayList<>();
    private Inventory inventory;


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
        mySQLMethods.insertClass(this.getClassName(), this.getClassLevel(), this.getClassCoast(), this.getClassColor(), this.getIcon(), this.getAbilities().toString());
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
