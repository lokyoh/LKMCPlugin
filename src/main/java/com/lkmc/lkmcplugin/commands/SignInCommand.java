package com.lkmc.lkmcplugin.commands;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.inventeryUI.DailySignInUI;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
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

public class SignInCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            DailySignInUI.show(p);
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("sign")) {
                DailySignInBase.signIn(p);
                return true;
            }
            if (args[0].equalsIgnoreCase("shop")) {
                DailySignInUI.showShop(p, 1);
                return true;
            }
            if (args[0].equalsIgnoreCase("gift")) {
                DailySignInBase.takeGift(p);
                return true;
            }
            if (args[0].equalsIgnoreCase("sup")) {
                sender.sendMessage("请输入天数");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.isOp()) {
                    sender.sendMessage("你没有权限使用");
                    return true;
                }
                LKMCP.dailyShopEnable = true;
                DailySignInBase.goods.clear();
                DailySignInBase.readShopConfig();
                sender.sendMessage("重载执行完毕");
                return true;
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("sup")) {
                try {
                    int day = Integer.parseInt(args[1]);
                    if (day > DailySignInBase.getDays() || day < 1) {
                        sender.sendMessage("天数不是有效值");
                        return true;
                    }
                    DailySignInBase.sup(p, day);
                } catch (NumberFormatException e) {
                    sender.sendMessage("天数不是有效值");
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1 && sender.isOp())
            return Arrays.asList("sign", "sup", "shop", "gift", "reload");
        if (args.length == 1 && !sender.isOp())
            return Arrays.asList("sign", "sup", "shop", "gift");
        return new ArrayList<>();
    }
}
