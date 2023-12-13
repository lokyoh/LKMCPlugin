package com.lkmc.lkmcplugin.module.dailySignIn;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.MyEconomy;
import com.lkmc.lkmcplugin.api.MyInventory;
import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.api.dataBase.SQLite;
import com.lkmc.lkmcplugin.inventeryUI.DailySignInUI;
import com.lkmc.lkmcplugin.item.MyItem;
import com.lkmc.lkmcplugin.module.dailySignIn.dailyShop.*;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class DailySignInBase {
    private static String month;
    private static String day;
    public static Map<UUID, PlayerDailySignInData> dailySignInDataMap = new HashMap<>();
    public static Map<UUID, Integer> dailyPoint = new HashMap<>();
    public static Map<UUID, Integer> gift = new HashMap<>();
    public static Map<Integer, IGoods> goods = new HashMap<>();

    public static void init() {
        month = new SimpleDateFormat("yyyyMM").format(new Date());
        day = new SimpleDateFormat("dd").format(new Date());
        create();
        createPoint();
        reload();
        reloadPoint();
        readShopConfig();
    }

    public static void readShopConfig() {
        try {
            if (!LKMCP.dailyShopFile.exists()) {
                throw new RuntimeException("文件不存在");
            }
            YamlConfiguration shopConfig = YamlConfiguration.loadConfiguration(LKMCP.dailyShopFile);
            Set<String> set = shopConfig.getKeys(true);
            set.removeIf(s -> s.contains("."));
            int i = 0;
            for (String path : set) {
                String type = shopConfig.getString(path + ".type");
                if (type != null) {
                    switch (type) {
                        case "item":
                            String itemID = shopConfig.getString(path + ".id");
                            int itemAmount = shopConfig.getInt(path + ".amount");
                            int itemPrice = shopConfig.getInt(path + ".price");
                            GoodsItem goodsItems = new GoodsItem(itemID, itemAmount, itemPrice);
                            if (goodsItems.check()) {
                                goods.put(i++, goodsItems);
                            }
                            break;
                        case "pokeBall":
                            String pokeBallID = shopConfig.getString(path + ".id");
                            int pokeBallAmount = shopConfig.getInt(path + ".amount");
                            int pokeBallPrice = shopConfig.getInt(path + ".price");
                            GoodsPokeBall goodsPokeBall = new GoodsPokeBall(pokeBallID, pokeBallAmount, pokeBallPrice);
                            if (goodsPokeBall.check()) {
                                goods.put(i++, goodsPokeBall);
                            }
                            break;
                        case "money":
                            int moneyAmount = shopConfig.getInt(path + ".amount");
                            int moneyPrice = shopConfig.getInt(path + ".price");
                            GoodsMoney goodsMoney = new GoodsMoney(moneyAmount, moneyPrice);
                            if (goodsMoney.check()) {
                                goods.put(i++, goodsMoney);
                            }
                            break;
                        case "pokemon":
                            String species = shopConfig.getString(path + ".species");
                            String palette = shopConfig.getString(path + ".palette");
                            int pokemonPrice = shopConfig.getInt(path + ".price");
                            GoodsPokemon goodsPokemon = new GoodsPokemon(species, palette, pokemonPrice);
                            if (goodsPokemon.check()) {
                                goods.put(i++, goodsPokemon);
                            }
                            break;
                        case "pokeMoney":
                            int pokeMoneyAmount = shopConfig.getInt(path + ".amount");
                            int pokeMoneyPrice = shopConfig.getInt(path + ".price");
                            GoodsPokeMoney goodsPokeMoney = new GoodsPokeMoney(pokeMoneyAmount, pokeMoneyPrice);
                            if (goodsPokeMoney.check()) {
                                goods.put(i++, goodsPokeMoney);
                            }
                            break;
                        case "lkItem":
                            String lkItemID = shopConfig.getString(path + ".id");
                            int lkItemAmount = shopConfig.getInt(path + ".amount");
                            int lkItemPrice = shopConfig.getInt(path + ".price");
                            GoodsLKItem goodsLKItem = new GoodsLKItem(lkItemID, lkItemAmount, lkItemPrice);
                            if (goodsLKItem.check()) {
                                goods.put(i++, goodsLKItem);
                            }
                            break;
                        default:
                            LKMCP.printLog("找不到" + path + "所属的类型");
                    }
                } else {
                    LKMCP.printLog(path + "未定义类型");
                }
            }//创建奖品对象
        } catch (Exception e) {
            LKMCP.dailyShopEnable = false;
            LKMCP.printLog("在读取每日商店配置文件时发生系统性错误，以下是错误原因：");
            e.printStackTrace();
            LKMCP.printLog("已关闭每日商店功能，请向作者反馈错误信息");
        }
    }

    private static void reloadPoint() {
        dailyPoint.clear();
        try {
            String sql = "SELECT * FROM 'dailyData';";
            Statement statement = LKMCP.connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                UUID uuid = UUID.fromString(result.getString("uuid"));
                int point = result.getInt("point");
                int take = result.getInt("gift");
                dailyPoint.put(uuid, point);
                gift.put(uuid, take);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            LKMCP.dailySystemEnable = false;
            LKMCP.printLog("在读取表时出现错误，已关闭签到系统");
            e.printStackTrace();
        }
    }

    private static void createPoint() {
        try {
            String arg = "uuid CHAR(36) PRIMARY KEY, point INT, gift INT";
            SQLite.createTable(LKMCP.connection, "dailyData", arg);
        } catch (SQLException e) {
            LKMCP.dailySystemEnable = false;
            LKMCP.printLog("在更新表时出现错误，已关闭签到系统");
            e.printStackTrace();
        }
    }

    public static String getMonth() {
        return month;
    }

    public static boolean setMonth(String newMonth) {
        if (month.equals(newMonth)) {
            return false;
        } else {
            month = newMonth;
            return true;
        }
    }

    public static int getDay() {
        return Integer.parseInt(day);
    }

    public static void setDay(String newDay) {
        day = newDay;
    }

    public static int getDays() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    public static void create() {
        try {
            StringBuilder arg = new StringBuilder("uuid CHAR(36) PRIMARY KEY, days INT, con INT");
            for (int i = 1; i <= DailySignInBase.getDays(); i++) {
                arg.append(", day").append(i).append(" INT");
            }
            SQLite.createTable(LKMCP.connection, month, arg.toString());
        } catch (SQLException e) {
            LKMCP.dailySystemEnable = false;
            LKMCP.printLog("在更新表时出现错误，已关闭签到系统");
            e.printStackTrace();
        }
    }

    public static void reload() {
        dailySignInDataMap.clear();
        try {
            String sql = "SELECT * FROM '" + month + "';";
            Statement statement = LKMCP.connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                UUID uuid = UUID.fromString(result.getString("uuid"));
                int days = result.getInt("days");
                int con = result.getInt("con");
                int[] dayData = new int[getDays()];
                for (int i = 0; i < dayData.length; i++) {
                    dayData[i] = result.getInt("day" + (i + 1));
                }
                dailySignInDataMap.put(uuid, new PlayerDailySignInData(days, con, dayData));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            LKMCP.dailySystemEnable = false;
            LKMCP.printLog("在读取表时出现错误，已关闭签到系统");
            e.printStackTrace();
        }
    }

    public static void add(UUID uuid) {
        StringBuilder arg = new StringBuilder("uuid, days, con");
        for (int i = 1; i <= DailySignInBase.getDays(); i++) {
            arg.append(", day").append(i);
        }
        StringBuilder value = new StringBuilder("'" + uuid.toString() + "', 0, 0");
        for (int i = 1; i <= DailySignInBase.getDays(); i++) {
            value.append(", ").append("0");
        }
        dailySignInDataMap.put(uuid, new PlayerDailySignInData());
        try {
            SQLite.insert(LKMCP.connection, month, arg.toString(), value.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void upDate(UUID uuid, String change) {
        SQLite.update(LKMCP.connection, month, change, "WHERE uuid = '" + uuid.toString() + "'");
    }

    public static boolean signIn(Player p) {
        if (!MyInventory.hasEmpty(p)) {
            p.sendMessage("没有空间领取物品");
            return false;
        }
        PlayerDailySignInData data = dailySignInDataMap.get(p.getUniqueId());
        if (!data.noSignIN(getDay())) {
            p.sendMessage("已签到");
            return false;
        }
        int point = 1;
        int con = data.getCon(getDay());
        if (con >= 3 && con < 5) {
            point = 2;
        } else if (con >= 5) {
            point = 3;
        }
        data.signIn(p.getUniqueId(), getDay());
        addPoint(point, p);
        checkDays(data, p);
        p.sendMessage("签到成功");
        return true;
    }

    public static boolean sup(Player p, int tDay) {
        if (!MyInventory.hasEmpty(p)) {
            p.sendMessage("没有空间领取物品");
            return false;
        }
        PlayerDailySignInData data = dailySignInDataMap.get(p.getUniqueId());
        if (tDay >= getDay()) {
            p.sendMessage("补签日期大于当前日期");
            return false;
        }
        if (data.noSignIN(tDay)) {
            if (MyInventory.useThisThing(p, MyItem.getMeta("supplement_card"))) {
                data.supSignIn(p.getUniqueId(), tDay);
                addPoint(1, p);
                checkDays(data, p);
                p.sendMessage("补签成功");
                return true;
            }
            p.sendMessage("找不到补签卡");
            return false;
        }
        p.sendMessage("已签到");
        return false;
    }

    public static void addPoint(int point, Player p) {
        int i = dailyPoint.get(p.getUniqueId()) + point;
        dailyPoint.replace(p.getUniqueId(), i);
        SQLite.update(LKMCP.connection, "dailyData", "point = " + dailyPoint.get(p.getUniqueId()), "WHERE uuid = '" + p.getUniqueId() + "'");
    }

    private static void checkDays(PlayerDailySignInData data, Player p) {
        switch (data.getDays()) {
            case 3:
            case 13:
            case 23:
                ItemStack prize3 = new ItemStack(Material.DIAMOND, 3);
                p.getInventory().addItem(prize3);
                p.sendMessage("你已连续签到" + data.getDays() + "天,获得3个钻石");
                return;
            case 5:
                ItemStack prize5 = MyPokemon.getPokeBall("ultra_ball");
                prize5.setAmount(20);
                p.getInventory().addItem(prize5);
                p.sendMessage("你已连续签到" + data.getDays() + "天,获得20个高级球");
                return;
            case 7:
                ItemStack prize7 = MyPokemon.getPokeBall("dusk_ball");
                prize7.setAmount(20);
                p.getInventory().addItem(prize7);
                p.sendMessage("你已连续签到" + data.getDays() + "天,获得20个黑暗球");
                return;
            case 10:
            case 17:
            case 30:
                MyEconomy.giveMoney(p, 100);
                p.sendMessage("你已连续签到" + data.getDays() + "天,获得100金币");
                return;
            case 15:
                ItemStack prize15 = MyPokemon.getPokeBall("timer_ball");
                prize15.setAmount(20);
                p.getInventory().addItem(prize15);
                p.sendMessage("你已连续签到" + data.getDays() + "天,获得20个计时球");
                return;
            case 20:
                ItemStack prize20 = new ItemStack(MyItem.get("ticket"));
                prize20.setAmount(1);
                p.getInventory().addItem(prize20);
                p.sendMessage("你已连续签到" + data.getDays() + "天,获得1张抽奖券");
                return;
            case 25:
                ItemStack prize25 = MyPokemon.getPokeBall("master_ball");
                prize25.setAmount(1);
                p.getInventory().addItem(prize25);
                p.sendMessage("你已连续签到" + data.getDays() + "天,获得1个大师球");
                return;
            case 27:
                ItemStack prize27 = MyPokemon.getPokeBall("repeat_ball");
                prize27.setAmount(20);
                p.getInventory().addItem(prize27);
                p.sendMessage("你已连续签到" + data.getDays() + "天,获得20个重复球");
                break;
        }
        if (data.getDays() == getDays()) {
            ItemStack prize = new ItemStack(MyItem.get("ticket"));
            prize.setAmount(1);
            p.getInventory().addItem(prize);
            p.sendMessage("本月已全部签到,获得1张抽奖券");
        }
    }

    public static void addPointUser(UUID uuid) {
        dailyPoint.put(uuid, 0);
        gift.put(uuid, 0);
        try {
            SQLite.insert(LKMCP.connection, "dailyData", "uuid, point, gift", "'" + uuid.toString() + "', 0, 0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void takeGift(Player p) {
        if (gift.get(p.getUniqueId()) != 0) {
            p.sendMessage("你已经领过新手福利了!!!");
            return;
        }
        SQLite.update(LKMCP.connection, "dailyData", "gift = 2", "WHERE uuid = '" + p.getUniqueId() + "'");
        gift.replace(p.getUniqueId(), 2);
        DailySignInUI.showGift(p);
    }

    public static void takeGiftTicket(Player p, int state) {
        if (MyInventory.giveItem(p, MyItem.get("ticket"))) {
            SQLite.update(LKMCP.connection, "dailyData", "gift = " + state, "WHERE uuid = '" + p.getUniqueId() + "'");
            gift.replace(p.getUniqueId(), state);
            p.sendMessage("您的新手抽奖券已获得");
        } else {
            p.sendMessage("背包空间不足，下次升级会重新发放");
        }
    }

    public static void usePoint(Player p, int price) {
        int i = dailyPoint.get(p.getUniqueId()) - price;
        dailyPoint.replace(p.getUniqueId(), i);
        SQLite.update(LKMCP.connection, "dailyData", "point = " + dailyPoint.get(p.getUniqueId()), "WHERE uuid = '" + p.getUniqueId() + "'");
    }
}
