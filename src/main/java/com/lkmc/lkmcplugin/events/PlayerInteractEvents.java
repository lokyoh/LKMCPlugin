package com.lkmc.lkmcplugin.events;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.inventeryUI.DrawUI;
import com.lkmc.lkmcplugin.inventeryUI.GenderTicketUI;
import com.lkmc.lkmcplugin.inventeryUI.ShinyTicketUI;
import com.lkmc.lkmcplugin.item.MyItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteractEvents implements Listener {
    @EventHandler
    public void playerInteractEvents(PlayerInteractEvent e) {
        if (!LKMCP.drawEnable)
            return;
        try {
            if (e.hasItem() && e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                ItemMeta im = e.getItem().getItemMeta();
                if (im == null) {
                    return;
                }
                if (im.equals(MyItem.getMeta("ticket"))) {
                    DrawUI.show(e.getPlayer());
                    return;
                }
                if (im.equals(MyItem.getMeta("shiny_ticket"))) {
                    ShinyTicketUI.show(e.getPlayer());
                    return;
                }
                if (im.equals(MyItem.getMeta("gender_ticket"))) {
                    GenderTicketUI.show(e.getPlayer());
                }
            }
        } catch (Exception ignored) {
        }
    }
}
