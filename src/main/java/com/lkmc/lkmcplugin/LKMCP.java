package com.lkmc.lkmcplugin;

import com.lkmc.lkmcplugin.api.dataBase.SQLite;
import com.lkmc.lkmcplugin.commands.*;
import com.lkmc.lkmcplugin.events.*;
import com.lkmc.lkmcplugin.item.MyItem;
import com.lkmc.lkmcplugin.item.UIItem;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import com.lkmc.lkmcplugin.module.dailySignIn.DailyTask;
import com.lkmc.lkmcplugin.module.draw.DrawBase;
import com.lkmc.lkmcplugin.module.systemShop.SystemShopBase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public final class LKMCP extends JavaPlugin {
    public static LKMCP plugin;
    public static boolean drawEnable = true;
    public static boolean dailySignInEnable = true;

    public static boolean dailyShopEnable = true;
    public static File prizeFile;
    public static File dailyShopFile;
    public static File acquisition;
    public static Connection connection;
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
    public static Timer timer = new Timer();
    public static DailyTask dailyTask = new DailyTask();
    public static int mines;
    public static int crops;
    public static int drops;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        reloadConfig();
        checkConfigVersion();
        // 命令注册
        Bukkit.getPluginCommand("lkgive").setExecutor(new ItemGiveCommand());
        Bukkit.getPluginCommand("lkbase").setExecutor(new LKBaseCommands());
        Bukkit.getPluginCommand("lkdraw").setExecutor(new DrawCommand());
        Bukkit.getPluginCommand("signin").setExecutor(new SignInCommand());
        Bukkit.getPluginCommand("lkquest").setExecutor(new DailyQuestCommand());
        Bukkit.getPluginCommand("lkshop").setExecutor(new LKShopCommands());
        // 事件注册
        Bukkit.getPluginManager().registerEvents(new BukkitHookForgeEvents(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLevelChangeEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitEvents(), this);
        // 物品初始化
        MyItem.init();
        // UI组件初始化
        UIItem.init();
        // 抽奖注册与初始化
        prizeFile = new File(getDataFolder(), "prize.yml");
        if (!prizeFile.exists()) {
            saveResource("prize.yml", false);
        }
        DrawBase.init();
        // 每日更新计划任务
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        if (date.before(new Date())) {
            Calendar startDT = Calendar.getInstance();
            startDT.setTime(date);
            startDT.add(Calendar.DAY_OF_MONTH, 1);
            date = startDT.getTime();
        }
        timer.schedule(dailyTask, date, PERIOD_DAY);
        // 每日签到注册与初始化
        mines = getConfig().getInt("daily-acquisition-settings.mines", -1);
        crops = getConfig().getInt("daily-acquisition-settings.crops", -1);
        drops = getConfig().getInt("daily-acquisition-settings.drops", -1);
        try {
            connection = SQLite.getConnection();
            // 每日签到
            dailyShopFile = new File(getDataFolder(), "dailyshop.yml");
            if (!dailyShopFile.exists()) {
                saveResource("dailyshop.yml", false);
            }
            DailySignInBase.init();
            // 每日任务
            DailyQuestBase.init();
            // 系统商店
            acquisition = new File(getDataFolder(), "acquisition.yml");
            if (!acquisition.exists()) {
                saveResource("acquisition.yml", false);
            }
            SystemShopBase.init();
        } catch (SQLException e) {
            dailySignInEnable = false;
            printLog("SQL开启失败，已关闭每日系统");
            e.printStackTrace();
        }
        printLog("LKMC插件加载完成");
    }

    @Override
    public void onDisable() {
        try {
            connection.close();
            timer.cancel();
            dailyTask.cancel();
        } catch (Exception ignored) {
        }
        printLog("LKMC插件已卸载");
    }

    //控制台消息
    public static void printLog(String s) {
        Bukkit.getConsoleSender().sendMessage(s);
    }

    //检查配置文件版本
    private void checkConfigVersion() {
        if (getConfig().getInt("version", 0) != 3) {
            saveResource("config.yml", true);
            printLog("配置文件过时，已重置配置文件");
        }
    }
}
