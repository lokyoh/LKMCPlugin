package com.lkmc.lkmcplugin.module.dailyQuest;

import java.util.TimerTask;
import java.util.UUID;

public class TimeConTask extends TimerTask {
    UUID uuid;

    public TimeConTask(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void run() {
        DailyQuestBase.data.get(uuid).addTime(1);
    }
}
