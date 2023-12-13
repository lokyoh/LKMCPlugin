package com.lkmc.lkmcplugin.events;

import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import com.lkmc.lkmcplugin.module.draw.DrawBase;
import com.lkmc.lkmcplugin.module.systemShop.SystemShopBase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEvents implements Listener {
    @EventHandler
    public void playerJoinEvents(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!DailySignInBase.dailySignInDataMap.containsKey(p.getUniqueId())) {
            DailySignInBase.add(p.getUniqueId());
            p.sendMessage("§c" + p.getDisplayName() + "欢迎今天登录游戏，今天未进行签到!!!");
        } else {
            if (DailySignInBase.dailySignInDataMap.get(p.getUniqueId()).noSignIN(DailySignInBase.getDay())) {
                p.sendMessage("§c" + p.getDisplayName() + "欢迎今天登录游戏，今天未进行签到!!!");
            }
        }
        if (!DailySignInBase.dailyPoint.containsKey(p.getUniqueId())) {
            DailySignInBase.addPointUser(p.getUniqueId());
            p.sendMessage("§c新手礼包未领取!!!");
        } else {
            if (DailySignInBase.gift.get(p.getUniqueId()) == 0) {
                p.sendMessage("§c新手礼包未领取!!!");
            }
        }
        if (!SystemShopBase.data.containsKey(p.getUniqueId())) {
            SystemShopBase.addUser(p.getUniqueId());
        }
        if (!DailyQuestBase.data.containsKey(p.getUniqueId())) {
            DailyQuestBase.addUser(p.getUniqueId());
        }
        DrawBase.create(p);
        DailyQuestBase.start(p.getUniqueId());
    }
}
