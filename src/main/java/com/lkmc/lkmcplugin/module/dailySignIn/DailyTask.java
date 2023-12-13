package com.lkmc.lkmcplugin.module.dailySignIn;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import com.lkmc.lkmcplugin.module.systemShop.SystemShopBase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class DailyTask extends TimerTask {
    @Override
    public void run() {
        if (LKMCP.dailySignInEnable) {
            LKMCP.printLog("更新每日签到数据中...");
            DailySignInBase.setDay(new SimpleDateFormat("dd").format(new Date()));
            if (DailySignInBase.setMonth(new SimpleDateFormat("yyyyMM").format(new Date()))) {
                DailySignInBase.create();
                DailySignInBase.reload();
                DailyQuestBase.create();
                DailyQuestBase.load();
            }
            SystemShopBase.remake();
            DailyQuestBase.upDate();
            LKMCP.printLog("每日签到数据更新完成");
        }
    }
}
