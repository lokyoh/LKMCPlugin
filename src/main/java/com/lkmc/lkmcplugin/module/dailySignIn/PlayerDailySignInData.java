package com.lkmc.lkmcplugin.module.dailySignIn;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class PlayerDailySignInData {
    private int days;
    private int con;
    private int[] dayData;

    public PlayerDailySignInData(int days, int con, int[] data) {
        this.days = days;
        this.con = con;
        this.dayData = new int[DailySignInBase.getDays()];
        System.arraycopy(data, 0, this.dayData, 0, DailySignInBase.getDays());
    }

    public PlayerDailySignInData() {
        days = 0;
        con = 0;
        dayData = new int[DailySignInBase.getDays()];
    }

    public int getDays() {
        return days;
    }

    public int getCon(int day) {
        if (day != 1 && dayData[day - 2] == 0 & dayData[day - 1] == 0) {
            return 0;
        }
        if (day != 1 && dayData[day - 2] == 0 & dayData[day - 1] == 1) {
            return 1;
        }
        return con;
    }

    public boolean noSignIN(int day) {
        return dayData[day - 1] == 0;
    }

    public void signIn(UUID uuid, int day) {
        dayData[day - 1] = 1;
        days++;
        if (day != 1 && dayData[day - 2] == 1) {
            con++;
        } else {
            con = 1;
        }
        String change = "day" + day + " = 1, days = " + days + ", con = " + con;
        DailySignInBase.upDate(uuid, change);
    }

    public void supSignIn(UUID uuid, int day) {
        con = getCon(DailySignInBase.getDay());
        dayData[day - 1] = 1;
        days++;
        String change = "day" + day + " = 1, days = " + days;
        DailySignInBase.upDate(uuid, change);
    }

    public ItemStack getInfo(Player p) {
        ItemStack playerInfo = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta playerInfoItemMeta = playerInfo.getItemMeta();
        playerInfoItemMeta.setDisplayName(p.getDisplayName());
        playerInfoItemMeta.setLore(Arrays.asList("累计签到:" + getDays(), "连续签到:" + getCon(DailySignInBase.getDay())));
        playerInfo.setItemMeta(playerInfoItemMeta);
        return playerInfo;
    }
}
