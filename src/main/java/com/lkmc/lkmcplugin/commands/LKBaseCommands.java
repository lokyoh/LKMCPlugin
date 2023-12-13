package com.lkmc.lkmcplugin.commands;

import com.lkmc.lkmcplugin.api.dataBase.DataBaseConsult;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LKBaseCommands implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args[0].equalsIgnoreCase("legendaryTOP")) {
            if (args.length == 1) {
                DataBaseConsult.consultLegendary(sender, 1);
                return true;
            }
            if (args.length == 2) {
                try {
                    int page = Integer.parseInt(args[1]);
                    DataBaseConsult.consultLegendary(sender, page);
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage("不是有效的数字");
                    return true;
                }
            }
        }
        if (args[0].equalsIgnoreCase("legendary")) {
            if (args.length == 1) {
                DataBaseConsult.consultPlayerLegendary(sender, (Player) sender);
                return true;
            }
            if (args.length == 2) {
                Player p = Bukkit.getPlayer(args[1]);
                if (p == null) {
                    sender.sendMessage("找不到玩家");
                    return true;
                }
                DataBaseConsult.consultPlayerLegendary(sender, p);
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("lotteryrecord")) {
            if (args.length == 1) {
                DataBaseConsult.consultDrawLog((Player) sender, 1);
                return true;
            }
            if (args.length == 2) {
                try {
                    int page = Integer.parseInt(args[1]);
                    DataBaseConsult.consultDrawLog((Player) sender, page);
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage("不是有效的数字");
                    return true;
                }
            }
            if (args.length == 3) {
                if(!sender.isOp()){
                    sender.sendMessage("你没有权限使用");
                    return true;
                }
                try {
                    int page = Integer.parseInt(args[1]);
                    Player p = Bukkit.getPlayer(args[2]);
                    if (p == null) {
                        sender.sendMessage("找不到玩家");
                        return true;
                    }
                    DataBaseConsult.consultDrawLog(p, page);
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage("不是有效的数字");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("legendaryTOP", "legendary", "lotteryrecord");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("legendary")) {
            return null;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("legendaryTOP") || args.length == 2 && args[0].equalsIgnoreCase("lotteryrecord")) {
            return Collections.singletonList("1");
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("lotteryrecord") && sender.isOp()) {
            return null;
        }
        return new ArrayList<>();
    }
}
