package com.lkmc.lkmcplugin.commands;

import com.lkmc.lkmcplugin.item.MyItem;
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
import java.util.Collections;
import java.util.List;

public class ItemGiveCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("你没有权限使用");
            return true;
        }
        if (args.length == 2) {
            Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                sender.sendMessage("玩家名错误");
                return true;
            }
            ItemStack is = MyItem.get(args[1]);
            p.getInventory().addItem(is);
            sender.sendMessage("已给予玩家 1 个 " + is.getItemMeta().getDisplayName());
            return true;
        }
        if (args.length != 3) {
            return false;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            sender.sendMessage("玩家名错误");
            return true;
        }
        try {
            int amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                sender.sendMessage("数量不是有效数字");
                return true;
            }
            ItemStack is = MyItem.get(args[1]);
            if (is == null) {
                sender.sendMessage("找不到物品" + args[1]);
                return true;
            }
            is.setAmount(amount);
            p.getInventory().addItem(is);
            sender.sendMessage("已给予玩家 " + args[2] + " 个 " + is.getItemMeta().getDisplayName());
        } catch (NumberFormatException e) {
            sender.sendMessage("数量的不是数字");
            return true;
        } catch (Exception e) {
            sender.sendMessage("出现其他错误，请检查控制台输出");
            e.printStackTrace();
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.isOp())
            return new ArrayList<>();
        if (args.length == 1)
            return null;
        if (args.length == 2) {
            return new ArrayList<>(MyItem.MyItems.keySet());
        }
        if (args.length == 3)
            return Collections.singletonList("1");
        return new ArrayList<>();
    }
}
