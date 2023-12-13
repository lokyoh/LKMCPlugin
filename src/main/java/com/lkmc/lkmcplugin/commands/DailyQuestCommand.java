package com.lkmc.lkmcplugin.commands;

import com.lkmc.lkmcplugin.inventeryUI.DailyQuestUI;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DailyQuestCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            DailyQuestUI.show((Player) sender);
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reloadPlayerData")) {
                if (!sender.isOp()) {
                    sender.sendMessage("你没有权限使用");
                    return true;
                }
                DailyQuestBase.upDate();
                sender.sendMessage("数据更新完毕");
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1 && sender.isOp()) {
            return Arrays.asList("reloadPlayerData");
        }
        return new ArrayList<>();
    }
}
