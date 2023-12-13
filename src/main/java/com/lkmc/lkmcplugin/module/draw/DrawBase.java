package com.lkmc.lkmcplugin.module.draw;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.MyInventory;
import com.lkmc.lkmcplugin.api.dataBase.DataBaseConsult;
import com.lkmc.lkmcplugin.api.dataBase.SQLite;
import com.lkmc.lkmcplugin.item.MyItem;
import com.lkmc.lkmcplugin.item.UIItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class DrawBase {
    private static ItemStack prizeList;
    public static List<IPrizeBase> prize = new ArrayList<>();
    public static List<String> prizeName = new ArrayList<>();
    public static int allRate = 0;

    public static ItemStack getPrizeList() {
        return prizeList;
    }

    public static void setPrizeListLore(List<String> lore) {
        ItemMeta prizeListMeta = prizeList.getItemMeta();
        prizeListMeta.setLore(lore);
        prizeList.setItemMeta(prizeListMeta);
    }

    public static void init() {
        prizeList = new ItemStack(Material.BOOK);
        ItemMeta prizeListMeta = prizeList.getItemMeta();
        prizeListMeta.setDisplayName("奖品概率");
        prizeList.setItemMeta(prizeListMeta);
        readPrizeConfig();
    }

    public static void readPrizeConfig() {
        try {
            if (!LKMCP.prizeFile.exists()) {
                throw new RuntimeException("文件不存在");
            }
            YamlConfiguration prizeConfig = YamlConfiguration.loadConfiguration(LKMCP.prizeFile);
            Set<String> set = prizeConfig.getKeys(true);
            set.removeIf(s -> s.contains("."));
            List<Integer> lRate = new ArrayList<>();
            for (String str : set) {
                String type = prizeConfig.getString(str + ".type");
                if (type != null) {
                    int rate = prizeConfig.getInt(str + ".rate");
                    if (rate > 0) {
                        addPrize(prizeConfig, str, type, rate);
                        prizeName.add(prizeConfig.getString(str + ".display"));
                        lRate.add(rate);
                        DrawBase.allRate += rate;
                    } else {
                        throw new RuntimeException(str + "的rate值不符合要求");
                    }
                } else {
                    throw new RuntimeException(str + "未定义类型");
                }
            }//创建奖品对象
            List<String> lore = new ArrayList<>();
            for (int i = 0; i < prizeName.size(); i++) {
                String rate = String.format("%.2f", ((double) lRate.get(i) / (double) DrawBase.allRate) * 100.);
                lore.add(prizeName.get(i) + "=>" + rate + "%");
            }
            setPrizeListLore(lore);
        } catch (RuntimeException e) {
            LKMCP.drawEnable = false;
            LKMCP.printLog("在读取抽奖配置文件时发生错误，以下是错误原因：");
            LKMCP.printLog(e.getMessage());
            LKMCP.printLog("已关闭抽奖功能,请修改配置文件后重新加载");
        } catch (Exception e) {
            LKMCP.drawEnable = false;
            LKMCP.printLog("在读取抽奖配置文件时发生系统性错误，以下是错误原因：");
            e.printStackTrace();
            LKMCP.printLog("已关闭抽奖功能，请向作者反馈错误信息");
        }
    }

    private static void addPrize(YamlConfiguration prizeConfig, String path, String type, int rate) throws RuntimeException {
        switch (type) {
            case "item":
                String itemID = prizeConfig.getString(path + ".id");
                int itemAmount = prizeConfig.getInt(path + ".amount");
                DrawBase.prize.add(new PrizeItem(rate, itemID, itemAmount));
                return;
            case "pokeBall":
                String pokeBallID = prizeConfig.getString(path + ".id");
                int pokeBallAmount = prizeConfig.getInt(path + ".amount");
                DrawBase.prize.add(new PrizePokeBall(rate, pokeBallID, pokeBallAmount));
                return;
            case "money":
                int moneyAmount = prizeConfig.getInt(path + ".amount");
                DrawBase.prize.add(new PrizeMoney(rate, moneyAmount));
                return;
            case "pokemon":
                String species = prizeConfig.getString(path + ".species");
                String palette = prizeConfig.getString(path + ".palette");
                DrawBase.prize.add(new PrizePokemon(rate, species, palette));
                return;
            case "pokeMoney":
                int pokeMoneyAmount = prizeConfig.getInt(path + ".amount");
                DrawBase.prize.add(new PrizePokeMoney(rate, pokeMoneyAmount));
                return;
            case "lkItem":
                String lkItemID = prizeConfig.getString(path + ".id");
                int lkItemAmount = prizeConfig.getInt(path + ".amount");
                DrawBase.prize.add(new PrizeLKItem(rate, lkItemID, lkItemAmount));
                return;
            default:
                throw new RuntimeException("找不到" + path + "所属的类型");
        }
    }

    public static ItemStack drawItem(Player p) {
        Random random = new Random();
        int prizeNum = random.nextInt(allRate) + 1;
        int i = 0;
        for (IPrizeBase pb : prize) {
            if (prizeNum <= pb.getRate()) {
                if (MyInventory.hasEmpty(p) && MyInventory.useThisThing(p, MyItem.getMeta("ticket"))) {
                    addLog(p.getUniqueId(), prizeName.get(i));
                    return pb.win(p);
                }
                return UIItem.get("draw_error1");
            } else {
                prizeNum -= pb.getRate();
            }
            i++;
        }
        return UIItem.get("draw_error2");
    }

    public static void create(Player p) {
        try {
            SQLite.createTable(LKMCP.connection, p.getUniqueId() + "-LotteryRecord", "date CHAR(22),log TEXT");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addLog(UUID uuid, String log) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss");
        LKMCP.printLog(Bukkit.getPlayer(uuid).getDisplayName() + "抽到了" + log);
        SQLite.executorService.submit(()->{
            try {
                SQLite.insert(LKMCP.connection, uuid + "-LotteryRecord", "date, log", "'" + ft.format(dNow) + "', '" + log + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static List<String> getLog(Player p) {
        List<String> logs = new ArrayList<>();
        try {
            String sql = "SELECT * FROM '" + p.getUniqueId() + "-LotteryRecord" + "';";
            Statement statement = LKMCP.connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                String date = result.getString("date");
                String log = result.getString("log");
                logs.add(date + ":" + log);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.reverse(logs);
        return logs;
    }
}
