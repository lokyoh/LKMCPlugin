package com.lkmc.lkmcplugin.module.systemShop;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.dataBase.SQLite;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SystemShopBase {
    public static Map<UUID, SystemShopPlayerData> data = new HashMap<>();
    public static Map<Integer, AcquisitionGoods> goods = new HashMap<>();

    public static void init() {
        create();
        reload();
        readConfig();
    }

    public static void create() {
        try {
            SQLite.createTable(LKMCP.connection, "sell", "uuid CHAR(36) PRIMARY KEY, mines DOUBLE, crops DOUBLE, drops DOUBLE, diamonds INT");
        } catch (SQLException e) {
            LKMCP.dailySystemEnable = false;
            LKMCP.printLog("在更新表时出现错误，已关闭签到系统");
            e.printStackTrace();
        }
    }

    public static void remake() {
        for (UUID uuid : data.keySet()) {
            data.get(uuid).reset();
        }
        SQLite.update(LKMCP.connection, "sell", "mines = 0, crops = 0, drops = 0, diamonds = 0", "");
    }

    public static void reload() {
        data.clear();
        try {
            String sql = "SELECT * FROM 'sell';";
            Statement statement = LKMCP.connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                UUID uuid = UUID.fromString(result.getString("uuid"));
                double mines = result.getDouble("mines");
                double crops = result.getDouble("crops");
                double drops = result.getDouble("drops");
                int diamonds = result.getInt("diamonds");
                data.put(uuid, new SystemShopPlayerData(mines, crops, drops, diamonds));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            LKMCP.dailySystemEnable = false;
            LKMCP.printLog("在读取表时出现错误，已关闭签到系统");
            e.printStackTrace();
        }
    }

    public static void updateMines(Player p, double mines) {
        BigDecimal bigDecimal = new BigDecimal(data.get(p.getUniqueId()).getMines() + mines);
        double newMines = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        SQLite.update(LKMCP.connection, "sell", "mines = " + newMines, "WHERE uuid = '" + p.getUniqueId() + "'");
        data.get(p.getUniqueId()).setMines(newMines);
    }

    public static void updateCrops(Player p, double crops) {
        BigDecimal bigDecimal = new BigDecimal(data.get(p.getUniqueId()).getCrops() + crops);
        double newCrops = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        SQLite.update(LKMCP.connection, "sell", "crops = " + newCrops, "WHERE uuid = '" + p.getUniqueId() + "'");
        data.get(p.getUniqueId()).setCrops(newCrops);
    }

    public static void updateDrops(Player p, double drops) {
        BigDecimal bigDecimal = new BigDecimal(data.get(p.getUniqueId()).getDrops() + drops);
        double newDrops = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        SQLite.update(LKMCP.connection, "sell", "drops = " + newDrops, "WHERE uuid = '" + p.getUniqueId() + "'");
        data.get(p.getUniqueId()).setDrops(newDrops);
    }

    public static void updateDiamonds(Player p, int diamonds) {
        int newDiamonds = data.get(p.getUniqueId()).getDiamonds() + diamonds;
        SQLite.update(LKMCP.connection, "sell", "diamonds = " + newDiamonds, "WHERE uuid = '" + p.getUniqueId() + "'");
        data.get(p.getUniqueId()).setDiamonds(newDiamonds);
    }

    public static void readConfig() {
        try {
            if (!LKMCP.acquisition.exists()) {
                throw new RuntimeException("文件不存在");
            }
            YamlConfiguration acquisitionConfig = YamlConfiguration.loadConfiguration(LKMCP.acquisition);
            Set<String> set = acquisitionConfig.getKeys(true);
            set.removeIf(s -> s.contains("."));
            int i = 0;
            for (String path : set) {
                String id = acquisitionConfig.getString(path + ".id");
                double price = acquisitionConfig.getDouble(path + ".price");
                int type = acquisitionConfig.getInt(path + ".type");
                AcquisitionGoods goodsItems = new AcquisitionGoods(id, price, type);
                if (goodsItems.check()) {
                    goods.put(i++, goodsItems);
                }
            }//创建奖品对象
        } catch (Exception e) {
            LKMCP.dailyShopEnable = false;
            LKMCP.printLog("在读取每日商店配置文件时发生系统性错误，以下是错误原因：");
            e.printStackTrace();
            LKMCP.printLog("已关闭每日商店功能，请向作者反馈错误信息");
        }
    }

    public static void addUser(UUID uuid) {
        data.put(uuid, new SystemShopPlayerData());
        try {
            SQLite.insert(LKMCP.connection, "sell", "uuid, mines, crops, drops, diamonds", "'" + uuid.toString() + "', 0, 0, 0, 0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
