package com.lkmc.lkmcplugin.commands;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.inventeryUI.DrawUI;
import com.lkmc.lkmcplugin.item.MyItem;
import com.lkmc.lkmcplugin.module.draw.DrawBase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DrawCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0)
            return false;
        if (!sender.isOp()) {
            sender.sendMessage("你没有权限使用");
            return true;
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (args.length > 3) {
                sender.sendMessage("/lkdraw give <玩家> <数量>");
                return true;
            }
            try {
                int amount = 1;
                if (args.length == 3) {
                    amount = Integer.parseInt(args[2]);
                    if (amount <= 0) {
                        sender.sendMessage("不是有效数量");
                        return true;
                    }
                }
                Player p = Bukkit.getPlayer(args[1]);
                if (p == null) {
                    sender.sendMessage("找不到玩家");
                    return true;
                }
                ItemStack is = new ItemStack(MyItem.get("ticket"));
                is.setAmount(amount);
                p.getInventory().addItem(is);
                sender.sendMessage("已经给予玩家" + args[1] + " " + args[2] + "个" + is.getItemMeta().getDisplayName());
            } catch (NumberFormatException e) {
                sender.sendMessage("数量不是数字");
                return true;
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("draw")) {
            if (!LKMCP.drawEnable) {
                sender.sendMessage("插件未开启抽奖功能或加载奖品时出现错误");
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage("/lkdraw draw <玩家>");
                return true;
            }
            try {
                Player p = Bukkit.getPlayer(args[1]);
                if (p == null) {
                    sender.sendMessage("找不到玩家");
                    return true;
                }
                DrawUI.show(p);
                sender.sendMessage("已经为" + args[1] + "打开抽奖界面");
            } catch (Exception e) {
                sender.sendMessage("出现错误");
                return true;
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            LKMCP.drawEnable = true;
            DrawBase.prize.clear();
            DrawBase.prizeName.clear();
            DrawBase.allRate = 0;
            DrawBase.readPrizeConfig();
            sender.sendMessage("重载执行完毕");
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.isOp())
            return new ArrayList<>();
        if (args.length == 1)
            return Arrays.asList("draw", "give", "reload");
        if (args.length == 2)
            return null;
        if (args.length == 3 && args[0].equalsIgnoreCase("give"))
            return Collections.singletonList("1");
        return new ArrayList<>();
    }
}
