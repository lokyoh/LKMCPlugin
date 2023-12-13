package com.lkmc.lkmcplugin.module.systemShop;

import com.lkmc.lkmcplugin.LKMCP;
import com.lkmc.lkmcplugin.api.MyEconomy;
import com.lkmc.lkmcplugin.api.MyInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class AcquisitionGoods {
    private ItemStack goods;
    private String id;
    private double price;
    private int type;

    public AcquisitionGoods(String id, double price, int type) {
        this.id = id.toUpperCase();
        this.price = price;
        this.type = type;
    }

    public boolean check() {
        if (type < 0 || type > 4) {
            LKMCP.printLog(id + "类型不符合规范");
            return false;
        }
        if (Material.getMaterial(id) == null) {
            LKMCP.printLog("找不到物品" + id);
            return false;
        }
        if (price <= 0) {
            LKMCP.printLog("价格" + price + "无效");
            return false;
        }
        goods = new ItemStack(Material.getMaterial(id));
        return true;
    }

    public ItemStack getGoods() {
        return goods;
    }

    public ItemStack showGoods() {
        String strType = "杂项";
        switch (type) {
            case 1:
            case 4:
                strType = "矿物";
                break;
            case 2:
                strType = "农作物";
                break;
            case 3:
                strType = "掉落物";
                break;
        }
        ItemStack show = new ItemStack(goods);
        ItemMeta goodsMeta = show.getItemMeta();
        if (goodsMeta != null && goodsMeta.getLore() != null) {
            List<String> list = goodsMeta.getLore();
            list.add(strType);
            list.add("收购价格:" + price);
            goodsMeta.setLore(list);
        } else {
            goodsMeta.setLore(Arrays.asList(strType, "收购价格:" + price));
        }
        show.setItemMeta(goodsMeta);
        return show;
    }

    public boolean acquisition(Player p, int amount) {
        if (type == 0) {
            return acquisitionLogic(p, amount);
        } else if (type == 1) {
            if (SystemShopBase.data.get(p.getUniqueId()).canSellMines(price * amount))
                if (acquisitionLogic(p, amount)) {
                    SystemShopBase.updateMines(p, price * amount);
                    return true;
                } else {
                    return false;
                }
            else {
                p.sendMessage("你不能出售更多的矿物了");
                return false;
            }
        } else if (type == 2) {
            if (SystemShopBase.data.get(p.getUniqueId()).canSellCrops(price * amount))
                if (acquisitionLogic(p, amount)) {
                    SystemShopBase.updateCrops(p, price * amount);
                    return true;
                } else {
                    return false;
                }
            else {
                p.sendMessage("你不能出售更多的农作物了");
                return false;
            }
        } else if (type == 3) {
            if (SystemShopBase.data.get(p.getUniqueId()).canSellDrops(price * amount))
                if (acquisitionLogic(p, amount)) {
                    SystemShopBase.updateDrops(p, price * amount);
                    return true;
                } else {
                    return false;
                }
            else {
                p.sendMessage("你不能出售更多的掉落物了");
                return false;
            }
        } else if (type == 4) {
            if (SystemShopBase.data.get(p.getUniqueId()).canSellDiamonds(amount))
                if (acquisitionLogic(p, amount)) {
                    SystemShopBase.updateDiamonds(p, amount);
                    return true;
                } else {
                    return false;
                }
            else {
                p.sendMessage("你不能出售更多的钻石了");
                return false;
            }
        }
        return false;
    }

    public boolean acquisitionLogic(Player p, int amount) {
        int have = MyInventory.getItemAmount(p, goods);
        if (have < amount) {
            p.sendMessage("没有足够多的商品!!!");
            return false;
        }
        MyInventory.takeItemUncheck(p, goods, amount);
        MyEconomy.giveMoney(p, price * amount);
        String str = String.format("%.2f", price * amount);
        p.sendMessage("出售物品获得 " + str + " 金币");
        return true;
    }
}
