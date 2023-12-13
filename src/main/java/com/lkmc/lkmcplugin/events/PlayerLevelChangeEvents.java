package com.lkmc.lkmcplugin.events;

import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class PlayerLevelChangeEvents implements Listener {
    @EventHandler
    public void playerLeveUp(PlayerLevelChangeEvent e) {
        if (DailySignInBase.gift.get(e.getPlayer().getUniqueId()) != 1 && DailySignInBase.gift.get(e.getPlayer().getUniqueId()) != 0) {
            if (DailySignInBase.gift.get(e.getPlayer().getUniqueId()) == 2) {
                if (e.getNewLevel() >= 20) {
                    DailySignInBase.takeGiftTicket(e.getPlayer(), 3);
                }
            } else if (DailySignInBase.gift.get(e.getPlayer().getUniqueId()) == 3) {
                if (e.getNewLevel() >= 25) {
                    DailySignInBase.takeGiftTicket(e.getPlayer(), 4);
                }
            } else if (DailySignInBase.gift.get(e.getPlayer().getUniqueId()) == 4) {
                if (e.getNewLevel() >= 30) {
                    DailySignInBase.takeGiftTicket(e.getPlayer(), 1);
                }
            }
        }
    }
}
