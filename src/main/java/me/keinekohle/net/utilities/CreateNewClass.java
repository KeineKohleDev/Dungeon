package me.keinekohle.net.utilities;

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class CreateNewClass {

    private Integer stage = 0;
    private Integer stageMax = 10;
    private String className;
    private Integer classLevel;
    private Integer classCoast;
    private String classColor;
    private String representativeItem;
    private List<String> ability = new ArrayList<>();
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

    public List<String> getAbility() {
        return ability;
    }

    public void setAbility(List<String> ability) {
        this.ability = ability;
    }

    public String getClassColor() {
        return classColor;
    }

    public void setClassColor(String classColor) {
        this.classColor = classColor;
    }

    public String getRepresentativeItem() {
        return representativeItem;
    }

    public void setRepresentativeItem(String representativeItem) {
        this.representativeItem = representativeItem;
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



}
