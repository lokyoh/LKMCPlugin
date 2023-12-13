package com.lkmc.lkmcplugin.inventeryUI;

import com.lkmc.lkmcplugin.item.UIItem;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DailyQuestUI {
    public static void show(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6每日任务界面");
        for (int i = 0; i < 53; i++) {
            inv.setItem(i, UIItem.get("blank"));
        }
        DailyQuestPlayerData pData = DailyQuestBase.data.get(p.getUniqueId());
        switch (pData.getState()) {
            case 0:
                if (pData.settlement() >= 1) {
                    inv.setItem(1, UIItem.get("quest_unTake"));
                } else {
                    inv.setItem(1, UIItem.get("quest_uncompleted"));
                }
                inv.setItem(2, UIItem.get("quest_uncompleted"));
                inv.setItem(3, UIItem.get("quest_uncompleted"));
                inv.setItem(4, UIItem.get("quest_uncompleted"));
                inv.setItem(6, UIItem.get("quest_uncompleted"));
                break;
            case 1:
                inv.setItem(1, UIItem.get("quest_complete"));
                if (pData.settlement() >= 2) {
                    inv.setItem(2, UIItem.get("quest_unTake"));
                } else {
                    inv.setItem(2, UIItem.get("quest_uncompleted"));
                }
                inv.setItem(3, UIItem.get("quest_uncompleted"));
                inv.setItem(4, UIItem.get("quest_uncompleted"));
                inv.setItem(6, UIItem.get("quest_uncompleted"));
                break;
            case 2:
                inv.setItem(1, UIItem.get("quest_complete"));
                inv.setItem(2, UIItem.get("quest_complete"));
                if (pData.settlement() >= 3) {
                    inv.setItem(3, UIItem.get("quest_unTake"));
                } else {
                    inv.setItem(3, UIItem.get("quest_uncompleted"));
                }
                inv.setItem(4, UIItem.get("quest_uncompleted"));
                inv.setItem(6, UIItem.get("quest_uncompleted"));
                break;
            case 3:
                inv.setItem(1, UIItem.get("quest_complete"));
                inv.setItem(2, UIItem.get("quest_complete"));
                inv.setItem(3, UIItem.get("quest_complete"));
                if (pData.settlement() >= 4) {
                    inv.setItem(4, UIItem.get("quest_unTake"));
                } else {
                    inv.setItem(4, UIItem.get("quest_uncompleted"));
                }
                inv.setItem(6, UIItem.get("quest_uncompleted"));
                break;
            case 4:
                inv.setItem(1, UIItem.get("quest_complete"));
                inv.setItem(2, UIItem.get("quest_complete"));
                inv.setItem(3, UIItem.get("quest_complete"));
                inv.setItem(4, UIItem.get("quest_complete"));
                if (pData.settlement() >= 4) {
                    inv.setItem(6, UIItem.get("quest_unTake"));
                } else {
                    inv.setItem(6, UIItem.get("quest_uncompleted"));
                }
                break;
            case 5:
                inv.setItem(1, UIItem.get("quest_complete"));
                inv.setItem(2, UIItem.get("quest_complete"));
                inv.setItem(3, UIItem.get("quest_complete"));
                inv.setItem(4, UIItem.get("quest_complete"));
                inv.setItem(6, UIItem.get("quest_complete"));
                break;
        }
        inv.setItem(20, UIItem.getBattleInfo(p));
        inv.setItem(24, UIItem.getPvpInfo(p));
        inv.setItem(29, UIItem.getQuestInfo(p));
        inv.setItem(33, UIItem.getCatchInfo(p));
        inv.setItem(38, UIItem.getLoginInfo(p));
        inv.setItem(42, UIItem.getFishingInfo(p));
        inv.setItem(45, UIItem.getPoint(p));
        inv.setItem(49, UIItem.get("daily_shop"));
        inv.setItem(53, UIItem.get("exit"));
        p.openInventory(inv);
    }
}
