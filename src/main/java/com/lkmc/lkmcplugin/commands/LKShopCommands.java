package com.lkmc.lkmcplugin.commands;

import com.lkmc.lkmcplugin.inventeryUI.LKShopUI;
import com.lkmc.lkmcplugin.module.systemShop.SystemShopBase;
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

public class LKShopCommands implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("sell")) {
                LKShopUI.showSell(p, 1);
                return true;
            }
            if (args[0].equalsIgnoreCase("buy")) {
                LKShopUI.showBuy(p, 1);
                return true;
            }
            if (args[0].equalsIgnoreCase("reloadSellGoods")) {
                if (!sender.isOp()) {
                    sender.sendMessage("你没有权限使用");
                    return true;
                }
                SystemShopBase.goods.clear();
                SystemShopBase.readConfig();
                sender.sendMessage("重载执行完毕");
                return true;
            }
            if (args[0].equalsIgnoreCase("reloadData")) {
                if (!sender.isOp()) {
                    sender.sendMessage("你没有权限使用");
                    return true;
                }
                SystemShopBase.remake();
                sender.sendMessage("已经重新加载玩家数据");
                return true;
            }
            if (args[0].equalsIgnoreCase("reloadBuyGoods")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1 && sender.isOp()) {
            return Arrays.asList("sell", "reloadSellGoods", "reloadData");
        }
        if (args.length == 1 && !sender.isOp()) {
            return Arrays.asList("sell");
        }
        return new ArrayList<>();
    }
}
