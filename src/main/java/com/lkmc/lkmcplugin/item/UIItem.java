package com.lkmc.lkmcplugin.item;

import com.lkmc.lkmcplugin.LKMCP;
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
        ItemStack error = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta errorItemMeta = error.getItemMeta();
        errorItemMeta.setDisplayName("§c未找到此UI物品");
        error.setItemMeta(errorItemMeta);
        UIItems.put("error", error);
        // blank
        ItemStack blank = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta blankItemMeta = blank.getItemMeta();
        blankItemMeta.setDisplayName("空白板");
        blank.setItemMeta(blankItemMeta);
        UIItems.put("blank", blank);
        // exit
        ItemStack exit = new ItemStack(Material.COMPASS);
        ItemMeta exitItemMeta = exit.getItemMeta();
        exitItemMeta.setDisplayName("退出");
        exitItemMeta.setLore(Collections.singletonList("点击退出"));
        exit.setItemMeta(exitItemMeta);
        UIItems.put("exit", exit);
        // draw_once
        ItemStack drawOnce = new ItemStack(Material.END_CRYSTAL);
        ItemMeta drawOnceItemMeta = drawOnce.getItemMeta();
        drawOnceItemMeta.setDisplayName("点击抽奖");
        drawOnce.setItemMeta(drawOnceItemMeta);
        UIItems.put("draw_once", drawOnce);
        // draw_ten_times
        ItemStack drawTenTimes = new ItemStack(Material.END_CRYSTAL);
        ItemMeta drawTenTimesItemMeta = drawTenTimes.getItemMeta();
        drawTenTimesItemMeta.setDisplayName("点击十连抽奖");
        drawTenTimes.setItemMeta(drawTenTimesItemMeta);
        UIItems.put("draw_ten_times", drawTenTimes);
        // draw_error1
        ItemStack drawError1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta drawError1ItemMeta = drawError1.getItemMeta();
        drawError1ItemMeta.setDisplayName("§c未找到抽奖券或背包空间不足");
        drawError1.setItemMeta(drawError1ItemMeta);
        UIItems.put("draw_error1", drawError1);
        // draw_error2
        ItemStack drawError2 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta drawError2ItemMeta = drawError2.getItemMeta();
        drawError2ItemMeta.setDisplayName("§c抽奖出现错误");
        drawError2.setItemMeta(drawError2ItemMeta);
        UIItems.put("draw_error2", drawError2);
        // pokemon_none_icon
        ItemStack pokemonNoneIcon = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta pokemonNoneIconItemMeta = pokemonNoneIcon.getItemMeta();
        pokemonNoneIconItemMeta.setDisplayName("§c未找到宝可梦");
        pokemonNoneIcon.setItemMeta(pokemonNoneIconItemMeta);
        UIItems.put("pokemon_none_icon", pokemonNoneIcon);
        // daily_sign_in_today_not_attend
        ItemStack dailySignInTodayNotAttend = new ItemStack(Material.RED_WOOL);
        ItemMeta dailySignInTodayNotAttendItemMeta = dailySignInTodayNotAttend.getItemMeta();
        dailySignInTodayNotAttendItemMeta.setLore(Arrays.asList("§4未签到", "§5§n点击签到"));
        dailySignInTodayNotAttendItemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10, true);
        dailySignInTodayNotAttend.setItemMeta(dailySignInTodayNotAttendItemMeta);
        UIItems.put("daily_sign_in_today_not_attend", dailySignInTodayNotAttend);
        // daily_sign_in_today_attend
        ItemStack dailySignInTodayAttend = new ItemStack(Material.LIME_WOOL);
        ItemMeta dailySignInTodayAttendItemMeta = dailySignInTodayAttend.getItemMeta();
        dailySignInTodayAttendItemMeta.setLore(Collections.singletonList("§a已签到"));
        dailySignInTodayAttendItemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10, true);
        dailySignInTodayAttend.setItemMeta(dailySignInTodayAttendItemMeta);
        UIItems.put("daily_sign_in_today_attend", dailySignInTodayAttend);
        // daily_sign_in_past_not_attend
        ItemStack dailySignInPastNotAttend = new ItemStack(Material.RED_WOOL);
        ItemMeta dailySignInPastNotAttendItemMeta = dailySignInPastNotAttend.getItemMeta();
        dailySignInPastNotAttendItemMeta.setLore(Arrays.asList("§4未签到", "§5§n点击补签"));
        dailySignInPastNotAttend.setItemMeta(dailySignInPastNotAttendItemMeta);
        UIItems.put("daily_sign_in_past_not_attend", dailySignInPastNotAttend);
        // daily_sign_in_past_attend
        ItemStack dailySignInPastAttend = new ItemStack(Material.LIME_WOOL);
        ItemMeta dailySignInPastAttendItemMeta = dailySignInPastAttend.getItemMeta();
        dailySignInPastAttendItemMeta.setLore(Collections.singletonList("§a已签到"));
        dailySignInPastAttend.setItemMeta(dailySignInPastAttendItemMeta);
        UIItems.put("daily_sign_in_past_attend", dailySignInPastAttend);
        // daily_sign_in_future
        ItemStack dailySignInFuture = new ItemStack(Material.GRAY_WOOL);
        ItemMeta dailySignInFutureItemMeta = dailySignInFuture.getItemMeta();
        dailySignInFutureItemMeta.setLore(Collections.singletonList("§7§o时间未到"));
        dailySignInFuture.setItemMeta(dailySignInFutureItemMeta);
        UIItems.put("daily_sign_in_future", dailySignInFuture);
        // daily_sign_in_prize
        ItemStack dailySignInPrize = new ItemStack(Material.BOOKSHELF);
        ItemMeta dailySignInPrizeItemMeta = dailySignInPrize.getItemMeta();
        dailySignInPrizeItemMeta.setDisplayName("累计签到奖励");
        dailySignInPrizeItemMeta.setLore(Arrays.asList(
                "3天:钻石*3 5天:高级球*20 7天:黑暗球*20 10天:金币*100"
                , "13天:钻石*3 15天:计时球*20 17天:金币*100 20天:抽奖券*1"
                , "23天:钻石*3 25天:大师球*1 27天:重复球*20 30天:金币*100"
                , "满签:抽奖券*1"
        ));
        dailySignInPrize.setItemMeta(dailySignInPrizeItemMeta);
        UIItems.put("daily_sign_in_prize", dailySignInPrize);
        // daily_sign_in_point
        ItemStack dailySignInPoint = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta dailySignInPointItemMeta = dailySignInPoint.getItemMeta();
        dailySignInPointItemMeta.setDisplayName("每日积分");
        dailySignInPoint.setItemMeta(dailySignInPointItemMeta);
        UIItems.put("daily_sign_in_point", dailySignInPoint);
        // daily_shop
        ItemStack dailyShop = new ItemStack(Material.BEACON);
        ItemMeta dailyShopItemMeta = dailyShop.getItemMeta();
        dailyShopItemMeta.setDisplayName("每日积分商店");
        dailyShopItemMeta.setLore(Collections.singletonList("点击进入每日积分商店"));
        dailyShop.setItemMeta(dailyShopItemMeta);
        UIItems.put("daily_shop", dailyShop);
        // next_page
        ItemStack nextPage = new ItemStack(Material.PAPER);
        ItemMeta nextPageItemMeta = nextPage.getItemMeta();
        nextPageItemMeta.setDisplayName("下一页");
        nextPageItemMeta.setLore(Collections.singletonList("点击进入下一页"));
        nextPage.setItemMeta(nextPageItemMeta);
        UIItems.put("next_page", nextPage);
        // previous_page
        ItemStack previousPage = new ItemStack(Material.PAPER);
        ItemMeta previousPageItemMeta = previousPage.getItemMeta();
        previousPageItemMeta.setDisplayName("上一页");
        previousPageItemMeta.setLore(Collections.singletonList("点击进入上一页"));
        previousPage.setItemMeta(previousPageItemMeta);
        UIItems.put("previous_page", previousPage);
        // fist_page
        ItemStack fistPage = new ItemStack(Material.BEACON);
        ItemMeta fistPageItemMeta = fistPage.getItemMeta();
        fistPageItemMeta.setDisplayName("第一页");
        fistPageItemMeta.setLore(Collections.singletonList("点击进入第一页"));
        fistPage.setItemMeta(fistPageItemMeta);
        UIItems.put("fist_page", fistPage);
        // can_sell_info
        ItemStack canSellInfo = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta canSellInfoItemMeta = canSellInfo.getItemMeta();
        canSellInfoItemMeta.setDisplayName("收购值:");
        canSellInfo.setItemMeta(canSellInfoItemMeta);
        UIItems.put("can_sell_info", canSellInfo);
        // sell1
        ItemStack sell1 = new ItemStack(Material.PAPER);
        ItemMeta sell1ItemMeta = sell1.getItemMeta();
        sell1ItemMeta.setDisplayName("卖1个");
        sell1.setItemMeta(sell1ItemMeta);
        UIItems.put("sell1", sell1);
        // sell8
        ItemStack sell8 = new ItemStack(Material.PAPER);
        ItemMeta sell8ItemMeta = sell8.getItemMeta();
        sell8ItemMeta.setDisplayName("卖8个");
        sell8.setItemMeta(sell8ItemMeta);
        sell8.setAmount(8);
        UIItems.put("sell8", sell8);
        // sell16
        ItemStack sell16 = new ItemStack(Material.PAPER);
        ItemMeta sell16ItemMeta = sell16.getItemMeta();
        sell16ItemMeta.setDisplayName("卖16个");
        sell16.setItemMeta(sell16ItemMeta);
        sell16.setAmount(16);
        UIItems.put("sell16", sell16);
        // sell32
        ItemStack sell32 = new ItemStack(Material.PAPER);
        ItemMeta sell32ItemMeta = sell32.getItemMeta();
        sell32ItemMeta.setDisplayName("卖32个");
        sell32.setItemMeta(sell32ItemMeta);
        sell32.setAmount(32);
        UIItems.put("sell32", sell32);
        // sell64
        ItemStack sell64 = new ItemStack(Material.PAPER);
        ItemMeta sell64ItemMeta = sell64.getItemMeta();
        sell64ItemMeta.setDisplayName("卖64个");
        sell64.setItemMeta(sell64ItemMeta);
        sell64.setAmount(64);
        UIItems.put("sell64", sell64);
        // goods_tag
        ItemStack goodsTag = new ItemStack(Material.NAME_TAG);
        UIItems.put("goods_tag", goodsTag);
        // shop_tips
        ItemStack shopTips = new ItemStack(Material.BOOK);
        ItemMeta shopTipsItemMeta = shopTips.getItemMeta();
        shopTipsItemMeta.setDisplayName("左键进入数量选择界面,右键全部售出");
        shopTips.setItemMeta(shopTipsItemMeta);
        UIItems.put("shop_tips", shopTips);
        // quest_complete
        ItemStack questComplete = new ItemStack(Material.LIME_WOOL);
        ItemMeta questCompleteItemMeta = questComplete.getItemMeta();
        questCompleteItemMeta.setDisplayName("已领取");
        questComplete.setItemMeta(questCompleteItemMeta);
        UIItems.put("quest_complete", questComplete);
        // quest_uncompleted
        ItemStack questUncompleted = new ItemStack(Material.RED_WOOL);
        ItemMeta questUncompletedItemMeta = questUncompleted.getItemMeta();
        questUncompletedItemMeta.setDisplayName("未完成");
        questUncompleted.setItemMeta(questUncompletedItemMeta);
        UIItems.put("quest_uncompleted", questUncompleted);
        // quest_unTake
        ItemStack questUnTake = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta questUnTakeItemMeta = questUnTake.getItemMeta();
        questUnTakeItemMeta.setDisplayName("已完成");
        questUnTakeItemMeta.setLore(Collections.singletonList("点击领取"));
        questUnTake.setItemMeta(questUnTakeItemMeta);
        UIItems.put("quest_unTake", questUnTake);
        // battle_info
        ItemStack battleInfo = new ItemStack(Material.getMaterial("PIXELMON_XS_EXP_CANDY"));
        UIItems.put("battle_info", battleInfo);
        // pvp_info
        ItemStack pvpInfo = new ItemStack(Material.getMaterial("PIXELMON_PUNCHING_GLOVE"));
        UIItems.put("pvp_info", pvpInfo);
        // quest_info
        ItemStack questInfo = new ItemStack(Material.getMaterial("PIXELMON_QUEST_EDITOR"));
        UIItems.put("quest_info", questInfo);
        // catch_info
        ItemStack catchInfo = new ItemStack(MyPokemon.getPokeBall("master_ball"));
        UIItems.put("catch_info", catchInfo);
        // login_info
        ItemStack loginInfo = new ItemStack(Material.EXPERIENCE_BOTTLE);
        UIItems.put("login_info", loginInfo);
        // fishing_info
        ItemStack fishingInfo = new ItemStack(Material.getMaterial("PIXELMON_OLD_ROD"));
        UIItems.put("fishing_info", fishingInfo);
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
