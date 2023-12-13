package com.lkmc.lkmcplugin;

import com.lkmc.lkmcplugin.api.event.DailyRefreshEvent;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import com.lkmc.lkmcplugin.module.systemShop.SystemShopBase;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyTask extends BukkitRunnable {
    @Override
    public void run() {
        LKMCP.printLog("更新每日数据中...");
        if (LKMCP.dailySystemEnable) {
            DailySignInBase.setDay(new SimpleDateFormat("dd").format(new Date()));
            if (DailySignInBase.setMonth(new SimpleDateFormat("yyyyMM").format(new Date()))) {
                DailySignInBase.create();
                DailySignInBase.reload();
                DailyQuestBase.create();
                DailyQuestBase.load();
            }
            SystemShopBase.remake();
            DailyQuestBase.upDate();
        }
        Bukkit.getServer().getPluginManager().callEvent(new DailyRefreshEvent());
        LKMCP.printLog("每日数据更新完成");
    }
}
