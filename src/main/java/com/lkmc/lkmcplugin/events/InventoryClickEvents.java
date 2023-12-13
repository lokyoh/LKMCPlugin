package com.lkmc.lkmcplugin.events;

import com.lkmc.lkmcplugin.api.MyInventory;
import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.inventeryUI.DailyQuestUI;
import com.lkmc.lkmcplugin.inventeryUI.DailySignInUI;
import com.lkmc.lkmcplugin.inventeryUI.LKShopUI;
import com.lkmc.lkmcplugin.item.MyItem;
import com.lkmc.lkmcplugin.item.UIItem;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestPlayerData;
import com.lkmc.lkmcplugin.module.dailySignIn.DailySignInBase;
import com.lkmc.lkmcplugin.module.dailySignIn.PlayerDailySignInData;
import com.lkmc.lkmcplugin.module.draw.DrawBase;
import com.lkmc.lkmcplugin.module.systemShop.SystemShopBase;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.gender.Gender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryClickEvents implements Listener {
    @EventHandler
    public void inventoryClickEvents(InventoryClickEvent e) {
        try {
            // 闪光修改界面
            if (Objects.equals(e.getView().getTitle(), "§6闪光修改界面")) {
                e.setCancelled(true);
                if (e.getInventory().getItem(e.getRawSlot()).getItemMeta().getDisplayName().equals("点击变成闪光")) {
                    int i = 0;
                    switch (e.getRawSlot()) {
                        case 6:
                            i = 1;
                            break;
                        case 12:
                            i = 2;
                            break;
                        case 15:
                            i = 3;
                            break;
                        case 21:
                            i = 4;
                            break;
                        case 24:
                            i = 5;
                            break;
                    }
                    if (MyInventory.useThisThing((Player) e.getWhoClicked(), MyItem.getMeta("shiny_ticket"))) {
                        MyPokemon.getPlayerPartyStorage((Player) e.getWhoClicked()).get(i).setPalette("shiny");
                    } else {
                        e.getWhoClicked().sendMessage("未找到闪光券");
                    }
                    e.getWhoClicked().closeInventory();
                } else if (e.getRawSlot() == 26)
                    e.getWhoClicked().closeInventory();
                return;
            }
            // 性别修改界面
            if (Objects.equals(e.getView().getTitle(), "§6性别修改界面")) {
                e.setCancelled(true);
                if (e.getInventory().getItem(e.getRawSlot()).getItemMeta().getDisplayName().matches(".*点击变性")) {
                    int i = 0;
                    switch (e.getRawSlot()) {
                        case 6:
                            i = 1;
                            break;
                        case 12:
                            i = 2;
                            break;
                        case 15:
                            i = 3;
                            break;
                        case 21:
                            i = 4;
                            break;
                        case 24:
                            i = 5;
                            break;
                    }
                    if (MyInventory.useThisThing((Player) e.getWhoClicked(), MyItem.getMeta("gender_ticket"))) {
                        Pokemon pokemon = MyPokemon.getPlayerPartyStorage((Player) e.getWhoClicked()).get(i);
                        pokemon.setGender(pokemon.getGender() == Gender.FEMALE ? Gender.MALE : Gender.FEMALE);
                    } else {
                        e.getWhoClicked().sendMessage("未找到变性券");
                    }
                    e.getWhoClicked().closeInventory();
                } else if (e.getRawSlot() == 26)
                    e.getWhoClicked().closeInventory();
                return;
            }
            // 每日签到界面
            if (Objects.equals(e.getView().getTitle(), "§6每日签到界面")) {
                e.setCancelled(true);
                Player p = (Player) e.getWhoClicked();
                PlayerDailySignInData data = DailySignInBase.dailySignInDataMap.get(p.getUniqueId());
                if (e.getRawSlot() == DailySignInBase.getDay() - 1) {
                    if (DailySignInBase.signIn(p)) {
                        e.getInventory().setItem(e.getRawSlot(), UIItem.getDailySignIn("daily_sign_in_today_attend", "第" + DailySignInBase.getDay() + "天"));
                        e.getInventory().setItem(45, data.getInfo(p));
                        e.getInventory().setItem(49, UIItem.getPoint(p));
                    }
                } else if (e.getRawSlot() < DailySignInBase.getDay() - 1) {
                    if (DailySignInBase.sup(p, e.getRawSlot() + 1)) {
                        e.getInventory().setItem(e.getRawSlot(), UIItem.getDailySignIn("daily_sign_in_past_attend", "第" + e.getRawSlot() + "天"));
                        e.getInventory().setItem(45, data.getInfo(p));
                        e.getInventory().setItem(49, UIItem.getPoint(p));
                    }
                } else if (e.getRawSlot() == 51)
                    DailySignInUI.showShop(p, 1);
                else if (e.getRawSlot() == 53)
                    e.getWhoClicked().closeInventory();
            } else if (e.getView().getTitle().matches("§6每日积分商店 第(\\d*)页")) {
                e.setCancelled(true);
                Player p = (Player) e.getWhoClicked();
                Pattern r = Pattern.compile("§6每日积分商店 第(\\d*)页");
                Matcher m = r.matcher(e.getView().getTitle());
                if (m.find()) {
                    int page = Integer.parseInt(m.group(1));
                    if (e.getRawSlot() < Math.min(DailySignInBase.goods.size() - (page - 1) * 36, 36)) {
                        DailySignInBase.goods.get((page - 1) * 36 + e.getRawSlot()).buyGoods(p);
                        e.getInventory().setItem(45, UIItem.getPoint(p));
                        return;
                    }
                    if (e.getRawSlot() == 48) {
                        if (page < 2)
                            return;
                        DailySignInUI.showShop(p, page - 1);
                    }
                    if (e.getRawSlot() == 49) {
                        if (page == 1)
                            return;
                        DailySignInUI.showShop(p, 1);
                    }
                    if (e.getRawSlot() == 50) {
                        if (DailySignInBase.goods.size() - (page - 1) * 36 < 36)
                            return;
                        DailySignInUI.showShop(p, page + 1);
                    }
                }
                if (e.getRawSlot() == 53)
                    e.getWhoClicked().closeInventory();
                return;
            }
            if (Objects.equals(e.getView().getTitle(), "§6抽奖界面")) {
                e.setCancelled(true);
                switch (e.getRawSlot()) {
                    case 21:
                        for (int i = 2; i < 16; i++) {
                            e.getInventory().setItem(i, UIItem.get("blank"));
                            if (i == 6)
                                i = 10;
                        }
                        e.getInventory().setItem(13, DrawBase.drawItem((Player) e.getWhoClicked()));
                        break;
                    case 23:
                        for (int i = 2; i < 16; i++) {
                            e.getInventory().setItem(i, DrawBase.drawItem((Player) e.getWhoClicked()));
                            if (i == 6)
                                i = 10;
                        }
                        break;
                    case 26:
                        e.getWhoClicked().closeInventory();
                        break;
                }
                return;
            }
            if (e.getView().getTitle().matches("§6系统收购商店 第(\\d*)页")) {
                e.setCancelled(true);
                Player p = (Player) e.getWhoClicked();
                Pattern r = Pattern.compile("§6系统收购商店 第(\\d*)页");
                Matcher m = r.matcher(e.getView().getTitle());
                if (m.find()) {
                    int page = Integer.parseInt(m.group(1));
                    if (e.getRawSlot() < Math.min(SystemShopBase.goods.size() - (page - 1) * 45, 45)) {
                        if (e.isLeftClick()) {
                            LKShopUI.chooseSellAmount(p, (page - 1) * 45 + e.getRawSlot());
                        } else if (e.isRightClick()) {
                            int amount = MyInventory.getItemAmount(p, SystemShopBase.goods.get((page - 1) * 45 + e.getRawSlot()).getGoods());
                            if (amount == 0) {
                                p.sendMessage("找不到物品");
                                return;
                            }
                            if (SystemShopBase.goods.get((page - 1) * 45 + e.getRawSlot()).acquisition(p, amount))
                                e.getInventory().setItem(45, UIItem.getCanSell(p));
                        }
                        return;
                    }
                    if (e.getRawSlot() == 48) {
                        if (page < 2)
                            return;
                        LKShopUI.showSell(p, page - 1);
                    }
                    if (e.getRawSlot() == 49) {
                        if (page == 1)
                            return;
                        LKShopUI.showSell(p, 1);
                    }
                    if (e.getRawSlot() == 50) {
                        if (SystemShopBase.goods.size() - (page - 1) * 36 < 36)
                            return;
                        LKShopUI.showSell(p, page + 1);
                    }
                }
                if (e.getRawSlot() == 53)
                    e.getWhoClicked().closeInventory();
            }
            if (e.getView().getTitle().equalsIgnoreCase("§6选择卖出的数量")) {
                e.setCancelled(true);
                Player p = (Player) e.getWhoClicked();
                ItemMeta data = e.getClickedInventory().getItem(0).getItemMeta();
                int index = Integer.parseInt(data.getDisplayName());
                if (e.getRawSlot() == 11) {
                    SystemShopBase.goods.get(index).acquisition(p, 1);
                } else if (e.getRawSlot() == 12) {
                    SystemShopBase.goods.get(index).acquisition(p, 8);
                } else if (e.getRawSlot() == 13) {
                    SystemShopBase.goods.get(index).acquisition(p, 16);
                } else if (e.getRawSlot() == 14) {
                    SystemShopBase.goods.get(index).acquisition(p, 32);
                } else if (e.getRawSlot() == 15) {
                    SystemShopBase.goods.get(index).acquisition(p, 64);
                } else if (e.getRawSlot() == 17)
                    LKShopUI.showSell(p, 1);
                return;
            }
            if (Objects.equals(e.getView().getTitle(), "§6每日任务界面")) {
                e.setCancelled(true);
                Player p = (Player) e.getWhoClicked();
                DailyQuestPlayerData data = DailyQuestBase.data.get(p.getUniqueId());
                if (e.getRawSlot() >= 1 && e.getRawSlot() <= 4 || e.getRawSlot() == 6) {
                    if (data.getState() < data.settlement() || data.getState() == 4) {
                        data.addState();
                        DailyQuestUI.show(p);
                    } else if (data.getState() == 5) {
                        p.sendMessage("你已经领取所有的奖励了");
                    } else {
                        p.sendMessage("请任意完成子任务中的一个");
                    }
                } else if (e.getRawSlot() == 49)
                    DailySignInUI.showShop(p, 1);
                else if (e.getRawSlot() == 53)
                    e.getWhoClicked().closeInventory();
            }
        } catch (Exception ignore) {
        }
    }
}
