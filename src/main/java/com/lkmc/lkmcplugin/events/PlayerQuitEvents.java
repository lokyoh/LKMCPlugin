package com.lkmc.lkmcplugin.events;

import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEvents implements Listener {
    @EventHandler
    public static void playerQuitEvents(PlayerQuitEvent e) {
        DailyQuestBase.end(e.getPlayer().getUniqueId());
    }
}
