package com.lkmc.lkmcplugin.module.dailyQuest;

import com.lkmc.lkmcplugin.api.MyEconomy;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.UUID;

public class DailyQuestPlayerData {
    //0:战斗胜利次数battle 1:玩家对战胜利次数pvp 2:完成任务次数quest 3:捕捉精灵数catch 4:钓鱼次数fishing 5:登录时间time
    public final UUID uuid;
    long[] data;
    long[] oldData;
    int state;
    int legendary;

    public DailyQuestPlayerData(UUID uuid) {
        this.uuid = uuid;
        data = new long[6];
        oldData = new long[6];
        legendary = 0;
    }

    public DailyQuestPlayerData(UUID uuid, long[] data, long[] oldData, int legendary, int state) {
        this.uuid = uuid;
        this.data = new long[6];
        this.oldData = new long[6];
        System.arraycopy(data, 0, this.data, 0, 6);
        System.arraycopy(oldData, 0, this.oldData, 0, 6);
        this.legendary = legendary;
        this.state = state;
    }

    public long getBattle() {
        return data[0] - oldData[0];
    }

    public long getPvp() {
        return data[1] - oldData[1];
    }

    public long getQuest() {
        return data[2] - oldData[2];
    }

    public long getCatch() {
        return data[3] - oldData[3];
    }

    public long getFishing() {
        return data[4] - oldData[4];
    }

    public long getTime() {
        return data[5] - oldData[5];
    }

    public void addBattle() {
        DailyQuestBase.upLoad(uuid, "battle", ++data[0]);
        if (getBattle() == 30) {
            MyEconomy.giveMoney(Bukkit.getPlayer(uuid), 5);
            Bukkit.getPlayer(uuid).sendMessage("恭喜你完成了每日 战斗胜利 任务，获得5金币");
        }
    }

    public void addPvp() {
        DailyQuestBase.upLoad(uuid, "pvp", ++data[1]);
        if (getPvp() == 3) {
            MyEconomy.giveMoney(Bukkit.getPlayer(uuid), 5);
            Bukkit.getPlayer(uuid).sendMessage("恭喜你完成了每日 PVP胜利 任务，获得5金币");
        }
    }

    public void addQuest() {
        DailyQuestBase.upLoad(uuid, "quest", ++data[2]);
        if (getQuest() == 1) {
            MyEconomy.giveMoney(Bukkit.getPlayer(uuid), 5);
            Bukkit.getPlayer(uuid).sendMessage("恭喜你完成了每日 完成NPC任务 任务，获得5金币");
        }
    }

    public void addCatch() {
        DailyQuestBase.upLoad(uuid, "catch", ++data[3]);
        if (getCatch() == 5) {
            MyEconomy.giveMoney(Bukkit.getPlayer(uuid), 5);
            Bukkit.getPlayer(uuid).sendMessage("恭喜你完成了每日 捕捉宝可梦 任务，获得5金币");
        }
    }

    public void addFishing() {
        DailyQuestBase.upLoad(uuid, "fishing", ++data[4]);
        if (getFishing() == 10) {
            MyEconomy.giveMoney(Bukkit.getPlayer(uuid), 5);
            Bukkit.getPlayer(uuid).sendMessage("恭喜你完成了每日 钓鱼 任务，获得5金币");
        }
    }

    public void addTime(long time) {
        data[5] += time;
        DailyQuestBase.upLoad(uuid, "time", data[5]);
        if (getTime() == 45) {
            MyEconomy.giveMoney(Bukkit.getPlayer(uuid), 5);
            Bukkit.getPlayer(uuid).sendMessage("恭喜你完成了每日 在线时长 任务，获得5金币");
        }
    }

    public void upDate() {
        state = 0;
        System.arraycopy(data, 0, oldData, 0, 6);
    }

    public void addState() {
        if (state < 4) {
            DailySignInBase.addPoint(1, Bukkit.getPlayer(uuid));
            state++;
            DailyQuestBase.upLoad(uuid, "state", state);
            Bukkit.getPlayer(uuid).sendMessage("恭喜你完成一个子任务，获得1点每日积分");
        } else {
            DailySignInBase.addPoint(2, Bukkit.getPlayer(uuid));
            state++;
            DailyQuestBase.upLoad(uuid, "state", state);
            MyEconomy.giveMoney(Bukkit.getPlayer(uuid), 10);
            Bukkit.getPlayer(uuid).sendMessage("恭喜你每日任务完成，获得10金币与2点每日积分");
        }
    }

    public int getState() {
        return state;
    }

    public void addLegendary() {
        ++legendary;
        DailyQuestBase.upLoad(uuid, "legendary", legendary);
    }

    public int getLegendary() {
        return legendary;
    }

    public int settlement() {
        int con = 0;
        if (getBattle() >= 30) {
            con++;
        }
        if (getPvp() >= 3) {
            con++;
        }
        if (getQuest() >= 1) {
            con++;
        }
        if (getCatch() >= 5) {
            con++;
        }
        if (getTime() >= 45) {
            con++;
        }
        if (getFishing() >= 10) {
            con++;
        }
        return con;
    }
}
