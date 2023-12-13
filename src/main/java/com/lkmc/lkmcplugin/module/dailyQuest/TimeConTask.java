package com.lkmc.lkmcplugin.module.dailyQuest;

import com.lkmc.lkmcplugin.api.event.TimerEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.TimerTask;
import java.util.UUID;

public class TimeConTask extends BukkitRunnable {
    public UUID uuid;

    public TimeConTask(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void run() {
        DailyQuestBase.data.get(uuid).addTime(1);
        Bukkit.getServer().getPluginManager().callEvent(new TimerEvent(uuid));
    }
}
