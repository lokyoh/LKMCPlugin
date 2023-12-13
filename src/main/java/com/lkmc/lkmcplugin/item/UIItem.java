package com.lkmc.lkmcplugin.item;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.ItemBuilder;
import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestPlayerData;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import com.lkmc.lkmcplugin.module.systemShop.SystemShopBase;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UIItem {
    public static Map<String, ItemStack> UIItems = new HashMap<>();

    public static void init() {
        // error
        UIItems.put("error", ItemBuilder.buildItem(
                Material.RED_STAINED_GLASS_PANE,
                "§c未找到此UI物品"
        ));
        // blank
        UIItems.put("blank", ItemBuilder.buildItem(
                Material.GRAY_STAINED_GLASS_PANE,
                "§7空白板"
        ));
        // exit
        UIItems.put("exit", ItemBuilder.buildItem(
                Material.COMPASS,
                "退出",
                Collections.singletonList("§5§n点击退出")
        ));
        // draw_once
        UIItems.put("draw_once", ItemBuilder.buildItem(
                Material.END_CRYSTAL,
                "§6点击抽奖"
        ));
        // draw_ten_times
        UIItems.put("draw_ten_times", ItemBuilder.buildItem(
                Material.END_CRYSTAL,
                "§6点击十连抽奖"
        ));
        // draw_error1
        UIItems.put("draw_error1", ItemBuilder.buildItem(
                Material.RED_STAINED_GLASS_PANE,
                "§c未找到抽奖券或背包空间不足"
        ));
        // draw_error2
        UIItems.put("draw_error2", ItemBuilder.buildItem(
                Material.RED_STAINED_GLASS_PANE,
                "§c抽奖出现错误"
        ));
        // pokemon_none_icon
        UIItems.put("pokemon_none_icon", ItemBuilder.buildItem(
                Material.RED_STAINED_GLASS_PANE,
                "§c未找到宝可梦"
        ));
        // daily_sign_in_today_not_attend
        UIItems.put("daily_sign_in_today_not_attend", ItemBuilder.buildItem(
                Material.RED_WOOL,
                Arrays.asList("§4未签到", "§5§n点击签到"),
                Collections.singletonList(Enchantment.LOOT_BONUS_BLOCKS)
        ));
        // daily_sign_in_today_attend
        UIItems.put("daily_sign_in_today_attend", ItemBuilder.buildItem(
                Material.LIME_WOOL,
                Collections.singletonList("§a已签到"),
                Collections.singletonList(Enchantment.LOOT_BONUS_BLOCKS)
        ));
        // daily_sign_in_past_not_attend
        UIItems.put("daily_sign_in_past_not_attend", ItemBuilder.buildItem(
                Material.RED_WOOL,
                Arrays.asList("§4未签到", "§5§n点击补签")
        ));
        // daily_sign_in_past_attend
        UIItems.put("daily_sign_in_past_attend", ItemBuilder.buildItem(
                Material.LIME_WOOL,
                Collections.singletonList("§a已签到")
        ));
        // daily_sign_in_future
        UIItems.put("daily_sign_in_future", ItemBuilder.buildItem(
                Material.GRAY_WOOL,
                Collections.singletonList("§7§o时间未到")
        ));
        // daily_sign_in_prize
        UIItems.put("daily_sign_in_prize", ItemBuilder.buildItem(
                Material.BOOKSHELF,
                "累计签到奖励",
                Arrays.asList(
                        "3天:钻石*3 5天:高级球*20 7天:黑暗球*20 10天:金币*100"
                        , "13天:钻石*3 15天:计时球*20 17天:金币*100 20天:抽奖券*1"
                        , "23天:钻石*3 25天:大师球*1 27天:重复球*20 30天:金币*100"
                        , "满签:抽奖券*1"
                )
        ));
        // daily_sign_in_point
        UIItems.put("daily_sign_in_point", ItemBuilder.buildItem(
                Material.GOLD_BLOCK,
                "§6每日积分"
        ));
        // daily_shop
        UIItems.put("daily_shop", ItemBuilder.buildItem(
                Material.BEACON,
                "§6每日积分商店",
                Collections.singletonList("§5§n点击进入每日积分商店")
        ));
        // next_page
        UIItems.put("next_page", ItemBuilder.buildItem(
                Material.PAPER,
                "下一页",
                Collections.singletonList("§5§n点击进入下一页")
        ));
        // previous_page
        UIItems.put("previous_page", ItemBuilder.buildItem(
                Material.PAPER,
                "上一页",
                Collections.singletonList("§5§n点击进入上一页")
        ));
        // fist_page
        UIItems.put("fist_page", ItemBuilder.buildItem(
                Material.BEACON,
                "第一页",
                Collections.singletonList("§5§n点击进入第一页")
        ));
        // can_sell_info
        UIItems.put("can_sell_info", ItemBuilder.buildItem(
                Material.GOLD_BLOCK,
                "收购值:"
        ));
        // sell1
        UIItems.put("sell1", ItemBuilder.buildItem(
                Material.PAPER,
                "卖1个"
        ));
        // sell8
        UIItems.put("sell8", ItemBuilder.buildItem(
                Material.PAPER,
                "卖8个",
                8
        ));
        // sell16
        UIItems.put("sell16", ItemBuilder.buildItem(
                Material.PAPER,
                "卖16个",
                16
        ));
        // sell32
        UIItems.put("sell32", ItemBuilder.buildItem(
                Material.PAPER,
                "卖32个",
                32
        ));
        // sell64
        UIItems.put("sell64", ItemBuilder.buildItem(
                Material.PAPER,
                "卖64个",
                64
        ));
        // goods_tag
        UIItems.put("goods_tag", ItemBuilder.buildItem(Material.NAME_TAG));
        // shop_tips
        UIItems.put("shop_tips", ItemBuilder.buildItem(
                Material.BOOK,
                "左键进入数量选择界面,右键全部售出"
        ));
        // quest_complete
        UIItems.put("quest_complete", ItemBuilder.buildItem(
                Material.LIME_WOOL,
                "§a已领取"
        ));
        // quest_uncompleted
        UIItems.put("quest_uncompleted", ItemBuilder.buildItem(
                Material.RED_WOOL,
                "§c未完成"
        ));
        // quest_unTake
        UIItems.put("quest_unTake", ItemBuilder.buildItem(
                Material.YELLOW_WOOL,
                "§e已完成",
                Collections.singletonList("§5§n点击领取")
        ));
        // battle_info
        UIItems.put("battle_info", ItemBuilder.buildItem("PIXELMON_XS_EXP_CANDY"));
        // pvp_info
        UIItems.put("pvp_info", ItemBuilder.buildItem("PIXELMON_PUNCHING_GLOVE"));
        // quest_info
        UIItems.put("quest_info", ItemBuilder.buildItem("PIXELMON_QUEST_EDITOR"));
        // catch_info
        ItemStack catchInfo = new ItemStack(MyPokemon.getPokeBall("master_ball"));
        UIItems.put("catch_info", catchInfo);
        // login_info
        UIItems.put("login_info", ItemBuilder.buildItem(Material.CLOCK));
        // fishing_info
        UIItems.put("fishing_info", ItemBuilder.buildItem("PIXELMON_OLD_ROD"));
    }

    public static ItemStack get(String name) {
        return UIItems.get(name) == null ? UIItems.get("error") : UIItems.get(name);
    }

    public static ItemStack getDailySignIn(String type, String display) {
        ItemStack itemStack = new ItemStack(get(type));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(display);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getPoint(Player p) {
        ItemStack point = new ItemStack(get("daily_sign_in_point"));
        ItemMeta pointMeta = point.getItemMeta();
        pointMeta.setLore(Collections.singletonList("已获得 " + DailySignInBase.dailyPoint.get(p.getUniqueId()) + " 分"));
        point.setItemMeta(pointMeta);
        return point;
    }


    public static ItemStack getCanSell(Player p) {
        ItemStack point = new ItemStack(get("can_sell_info"));
        ItemMeta pointMeta = point.getItemMeta();
        pointMeta.setLore(Arrays.asList("矿物:" +
                        String.format("%.2f", SystemShopBase.data.get(p.getUniqueId()).getMines()) + "/" + (LKMCP.mines == -1 ? "无限" : LKMCP.mines)
                , "农作物:" + String.format("%.2f", SystemShopBase.data.get(p.getUniqueId()).getCrops()) + "/" + (LKMCP.crops == -1 ? "无限" : LKMCP.crops)
                , "掉落物:" + String.format("%.2f", SystemShopBase.data.get(p.getUniqueId()).getDrops()) + "/" + (LKMCP.drops == -1 ? "无限" : LKMCP.drops)
                , "钻石:" + SystemShopBase.data.get(p.getUniqueId()).getDiamonds() + "/64"));
        point.setItemMeta(pointMeta);
        return point;
    }

    public static ItemStack getGoodsTag(int index) {
        ItemStack tag = new ItemStack(get("goods_tag"));
        ItemMeta tagMeta = tag.getItemMeta();
        tagMeta.setDisplayName(index + "");
        tag.setItemMeta(tagMeta);
        return tag;
    }

    public static ItemStack getBattleInfo(Player p) {
        ItemStack info = new ItemStack(get("battle_info"));
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName("对战胜利次数:");
        DailyQuestPlayerData playerData = DailyQuestBase.data.get(p.getUniqueId());
        if (playerData.getBattle() >= 30) {
            infoMeta.setLore(Arrays.asList(playerData.getBattle() + "/30", "§a已完成"));
            infoMeta.addEnchant(Enchantment.ARROW_DAMAGE, 10, true);
        } else {
            infoMeta.setLore(Arrays.asList(playerData.getBattle() + "/30", "§c未完成"));
        }
        info.setItemMeta(infoMeta);
        return info;
    }

    public static ItemStack getPvpInfo(Player p) {
        ItemStack info = new ItemStack(get("pvp_info"));
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName("PVP胜利次数:");
        DailyQuestPlayerData playerData = DailyQuestBase.data.get(p.getUniqueId());
        if (playerData.getPvp() >= 3) {
            infoMeta.setLore(Arrays.asList(playerData.getPvp() + "/3", "§a已完成"));
            infoMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 10, true);
        } else {
            infoMeta.setLore(Arrays.asList(playerData.getPvp() + "/3", "§c未完成"));
        }
        info.setItemMeta(infoMeta);
        return info;
    }

    public static ItemStack getQuestInfo(Player p) {
        ItemStack info = new ItemStack(get("quest_info"));
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName("任务完成次数:");
        DailyQuestPlayerData playerData = DailyQuestBase.data.get(p.getUniqueId());
        if (playerData.getQuest() >= 1) {
            infoMeta.setLore(Arrays.asList(playerData.getQuest() + "/1", "§a已完成"));
            infoMeta.addEnchant(Enchantment.DIG_SPEED, 10, true);
        } else {
            infoMeta.setLore(Arrays.asList(playerData.getQuest() + "/1", "§c未完成"));
        }
        info.setItemMeta(infoMeta);
        return info;
    }

    public static ItemStack getCatchInfo(Player p) {
        ItemStack info = new ItemStack(get("catch_info"));
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName("捕捉精灵次数:");
        DailyQuestPlayerData playerData = DailyQuestBase.data.get(p.getUniqueId());
        if (playerData.getCatch() >= 5) {
            infoMeta.setLore(Arrays.asList(playerData.getCatch() + "/5", "§a已完成"));
            infoMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10, true);
        } else {
            infoMeta.setLore(Arrays.asList(playerData.getCatch() + "/5", "§c未完成"));
        }
        info.setItemMeta(infoMeta);
        return info;
    }

    public static ItemStack getLoginInfo(Player p) {
        ItemStack info = new ItemStack(get("login_info"));
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName("登录时长:");
        DailyQuestPlayerData playerData = DailyQuestBase.data.get(p.getUniqueId());
        if (playerData.getTime() >= 45) {
            infoMeta.setLore(Arrays.asList(playerData.getTime() + "/45", "§a已完成"));
            infoMeta.addEnchant(Enchantment.MENDING, 10, true);
        } else {
            infoMeta.setLore(Arrays.asList(playerData.getTime() + "/45", "§c未完成"));
        }
        info.setItemMeta(infoMeta);
        return info;
    }

    public static ItemStack getFishingInfo(Player p) {
        ItemStack info = new ItemStack(get("fishing_info"));
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName("钓鱼次数:");
        DailyQuestPlayerData playerData = DailyQuestBase.data.get(p.getUniqueId());
        if (playerData.getFishing() >= 10) {
            infoMeta.setLore(Arrays.asList(playerData.getFishing() + "/10", "§a已完成"));
            infoMeta.addEnchant(Enchantment.LUCK, 10, true);
        } else {
            infoMeta.setLore(Arrays.asList(playerData.getFishing() + "/10", "§c未完成"));
        }
        info.setItemMeta(infoMeta);
        return info;
    }
}
