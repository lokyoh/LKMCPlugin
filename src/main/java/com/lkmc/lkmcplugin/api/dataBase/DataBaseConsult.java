package com.lkmc.lkmcplugin.api.dataBase;

import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestPlayerData;
import com.lkmc.lkmcplugin.module.draw.DrawBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataBaseConsult {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    static class PLegendary {
        public String name;
        public int times;

        PLegendary(DailyQuestPlayerData data) {
            name = Bukkit.getPlayer(data.uuid).getDisplayName();
            times = data.getLegendary();
        }
    }

    public static void consultLegendary(CommandSender sender, int page) {
        List<PLegendary> list = new ArrayList<>();
        for (UUID uuid : DailyQuestBase.data.keySet()) {
            if (Bukkit.getPlayer(uuid) == null)
                continue;
            list.add(new PLegendary(DailyQuestBase.data.get(uuid)));
        }
        list.sort((o1, o2) -> o2.times - o1.times);
        if (page > ((list.size() - 1) / 10 + 1)) {
            sender.sendMessage("页面数量超过最大值");
            return;
        }
        sender.sendMessage("神兽刷新选中排行第" + page + "/" + ((list.size() - 1) / 10 + 1) + "页:");
        for (int i = 0; i < Math.min(list.size() - (page - 1) * 10, 10); i++) {
            PLegendary data = list.get(i + (page - 1) * 10);
            sender.sendMessage((i + (page - 1) * 10 + 1) + ":" + data.name + " " + data.times);
        }
    }

    public static void consultPlayerLegendary(CommandSender sender, Player p) {
        sender.sendMessage(p.getDisplayName() + " 已经被神兽刷新选中 " + DailyQuestBase.data.get(p.getUniqueId()).getLegendary() + " 次了");
    }

    public static void consultDrawLog(Player p, int page) {
        executorService.submit(() -> {
            List<String> logs = DrawBase.getLog(p);
            if (page > ((logs.size() - 1) / 10 + 1)) {
                p.sendMessage("页面数量超过最大值");
                return;
            }
            p.sendMessage(p.getDisplayName() + " 的抽奖记录第" + page + "/" + ((logs.size() - 1) / 10 + 1) + "页:");
            for (int i = 0; i < Math.min(logs.size() - (page - 1) * 10, 10); i++) {
                p.sendMessage(logs.get(i + (page - 1) * 10));
            }
        });
    }
}
