package com.lkmc.lkmcplugin.module.dailyQuest;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.dataBase.SQLite;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

public class DailyQuestBase {
    public static Map<UUID, DailyQuestPlayerData> data = new HashMap<>();
    public static Map<UUID, Timer> timer = new HashMap<>();

    public static void init() {
        create();
        load();
    }

    public static void addUser(UUID uuid) {
        data.put(uuid, new DailyQuestPlayerData(uuid));
        try {
            SQLite.insert(LKMCP.connection, "Daily" + DailySignInBase.getMonth(), "uuid, battle, battleOld, pvp, pvpOld, quest, questOld, catch, catchOld, fishing, fishingOld, time, timeOld, legendary, state",
                    "'" + uuid.toString() + "', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void create() {
        try {
            SQLite.createTable(LKMCP.connection, "Daily" + DailySignInBase.getMonth(),
                    "uuid CHAR(36) PRIMARY KEY, battle LONG, battleOld LONG, pvp LONG, pvpOld LONG, quest LONG, questOld LONG, " +
                            "catch LONG, catchOld LONG, fishing LONG, fishingOld LONG, time LONG, timeOld LONG, legendary INT, state INT");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        data.clear();
        try {
            String sql = "SELECT * FROM '" + "Daily" + DailySignInBase.getMonth() + "';";
            Statement statement = LKMCP.connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                UUID uuid = UUID.fromString(result.getString("uuid"));
                long[] newData = new long[6];
                long[] oldData = new long[6];
                newData[0] = result.getLong("battle");
                newData[1] = result.getLong("pvp");
                newData[2] = result.getLong("quest");
                newData[3] = result.getLong("catch");
                newData[4] = result.getLong("fishing");
                newData[5] = result.getLong("time");
                oldData[0] = result.getLong("battleOld");
                oldData[1] = result.getLong("pvpOld");
                oldData[2] = result.getLong("questOld");
                oldData[3] = result.getLong("catchOld");
                oldData[4] = result.getLong("fishingOld");
                oldData[5] = result.getLong("timeOld");
                int legendary = result.getInt("legendary");
                int state = result.getInt("state");
                data.put(uuid, new DailyQuestPlayerData(uuid, newData, oldData, legendary, state));
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void upDate() {
        for (UUID uuid : data.keySet()) {
            data.get(uuid).upDate();
        }
        SQLite.update(LKMCP.connection, "Daily" + DailySignInBase.getMonth(), "battleOld = battle, pvpOld = pvp, questOld = quest, catchOld = catch, fishingOld = fishing, timeOld = time, state = 0", "");
    }

    public static void upLoad(UUID uuid, String arg, long num) {
        SQLite.update(LKMCP.connection, "Daily" + DailySignInBase.getMonth(), arg + " = " + num, "WHERE uuid = '" + uuid + "'");
    }

    public static void end(UUID uuid) {
        timer.get(uuid).cancel();
        timer.remove(uuid);
    }

    public static void start(UUID uuid) {
        Timer t = new Timer();
        t.schedule(new TimeConTask(uuid), 60 * 1000, 60 * 1000);
        timer.put(uuid, t);
    }
}
