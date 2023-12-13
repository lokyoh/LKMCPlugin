package com.lkmc.lkmcplugin.module.systemShop;

import com.lkmc.lkmcplugin.LKMCP;

public class SystemShopPlayerData {
    private double mines;
    private double crops;
    private double drops;
    private int diamonds;

    public SystemShopPlayerData() {
        this.mines = 0;
        this.crops = 0;
        this.drops = 0;
        this.diamonds = 0;
    }

    public double getMines() {
        return mines;
    }

    public double getCrops() {
        return crops;
    }

    public double getDrops() {
        return drops;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setMines(double mines) {
        this.mines = mines;
    }

    public void setCrops(double crops) {
        this.crops = crops;
    }

    public void setDrops(double drops) {
        this.drops = drops;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public SystemShopPlayerData(double mines, double crops, double drops, int diamonds) {
        this.mines = mines;
        this.crops = crops;
        this.drops = drops;
        this.diamonds = diamonds;
    }

    public void reset() {
        mines = 0;
        crops = 0;
        drops = 0;
        diamonds = 0;
    }

    public boolean canSellMines(double price) {
        if (LKMCP.mines == -1)
            return true;
        return mines + price <= LKMCP.mines;
    }

    public boolean canSellCrops(double price) {
        if (LKMCP.crops == -1)
            return true;
        return crops + price <= LKMCP.crops;
    }

    public boolean canSellDrops(double price) {
        if (LKMCP.drops == -1)
            return true;
        return drops + price <= LKMCP.drops;
    }

    public boolean canSellDiamonds(int amount) {
        return diamonds + amount <= 64;
    }
}
