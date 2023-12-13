package com.lkmc.lkmcplugin.events;

import com.lkmc.lkmcplugin.api.MyPokemon;
import com.lkmc.lkmcplugin.module.dailyQuest.DailyQuestBase;
import com.mohistmc.api.event.BukkitHookForgeEvent;
import com.pixelmonmod.pixelmon.api.battles.BattleEndCause;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.events.FishingEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleTickEvent;
import com.pixelmonmod.pixelmon.api.events.moveskills.UseMoveSkillEvent;
import com.pixelmonmod.pixelmon.api.events.quests.FinishQuestEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.LegendarySpawnEvent;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.Event;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class BukkitHookForgeEvents implements Listener {
    @EventHandler
    public static void bukkitHookForgeEvents(BukkitHookForgeEvent forgeEvent) {
        Event event = forgeEvent.getEvent();
        if (event instanceof BattleEndEvent) {
            BattleEndEvent e = (BattleEndEvent) event;
            BattleController battle = e.getBattleController();
            for (PlayerParticipant p : battle.getPlayers()) {
                for (PixelmonWrapper pokemon : p.getTeamPokemonList()) {
                    if (pokemon.getSpecies().is(PixelmonSpecies.ZACIAN)) {
                        MyPokemon.changeBackMove(pokemon);
                    }
                }
            }
            if (e.getCause().compareTo(BattleEndCause.NORMAL) == 0) {
                if (!e.getBattleController().isPvP()) {
                    for (PlayerParticipant p : e.getBattleController().getPlayers()) {
                        Player player = Bukkit.getPlayer(p.getDisplayName().replaceAll("§.", ""));
                        if (player != null) {
                            if (!p.isDefeated) {
                                DailyQuestBase.data.get(player.getUniqueId()).addBattle();
                            }
                        }
                    }
                } else if (e.getCause().compareTo(BattleEndCause.NORMAL) == 0 && e.getBattleController().isPvP()) {
                    for (PlayerParticipant p : e.getBattleController().getPlayers()) {
                        Player player = Bukkit.getPlayer(p.getDisplayName().replaceAll("§.", ""));
                        if (player != null) {
                            if (!p.isDefeated) {
                                DailyQuestBase.data.get(player.getUniqueId()).addPvp();
                            }
                        }
                    }
                }
            }
            return;
        }
        if (event instanceof FishingEvent.Catch) {
            FishingEvent.Catch e = (FishingEvent.Catch) event;
            Player p = Bukkit.getPlayer(e.player.displayName.replaceAll("§.", ""));
            if (p != null) {
                DailyQuestBase.data.get(p.getUniqueId()).addFishing();
            }
            return;
        }
        if (event instanceof FinishQuestEvent.Complete) {
            FinishQuestEvent.Complete e = (FinishQuestEvent.Complete) event;
            Player p = Bukkit.getPlayer(e.player.displayName.replaceAll("§.", ""));
            if (p != null) {
                DailyQuestBase.data.get(p.getUniqueId()).addQuest();
            }
            return;
        }
        if (event instanceof CaptureEvent.SuccessfulCapture) {
            CaptureEvent.SuccessfulCapture e = (CaptureEvent.SuccessfulCapture) event;
            Player p = Bukkit.getPlayer(e.getPlayer().displayName.replaceAll("§.", ""));
            if (p != null) {
                DailyQuestBase.data.get(p.getUniqueId()).addCatch();
            }
            return;
        }
        if (event instanceof LegendarySpawnEvent.ChoosePlayer) {
            LegendarySpawnEvent.ChoosePlayer e = (LegendarySpawnEvent.ChoosePlayer) event;
            Player p = Bukkit.getPlayer(e.player.displayName.replaceAll("§.", ""));
            if (p != null) {
                Bukkit.broadcastMessage("§6本次神兽刷新的天选之子是 " + e.player.displayName.replaceAll("§.", ""));
                DailyQuestBase.data.get(p.getUniqueId()).addLegendary();
            }
            return;
        }
        if (event instanceof BattleStartedEvent.Post) {
            BattleStartedEvent.Post e = (BattleStartedEvent.Post) event;
            BattleController battle = e.getBattleController();
            for (PlayerParticipant p : battle.getPlayers()) {
                for (PixelmonWrapper pokemon : p.getTeamPokemonList()) {
                    if (pokemon.getSpecies().is(PixelmonSpecies.ZACIAN)) {
                        Item held = pokemon.getHeldItem();
                        CraftItemStack cis = CraftItemStack.asNewCraftStack(held);
                        if (cis.isSimilar(new ItemStack(Material.getMaterial("PIXELMON_RUSTED_SWORD"))))
                            MyPokemon.changeMove(pokemon);
                    }
                }
            }
            return;
        }
        if (event instanceof BattleTickEvent.Pre) {
            BattleTickEvent.Pre e = (BattleTickEvent.Pre) event;
            BattleController battle = e.getBattleController();
            for (PlayerParticipant p : battle.getPlayers()) {
                for (PixelmonWrapper pokemon : p.getTeamPokemonList()) {
                    if (pokemon.getSpecies().is(PixelmonSpecies.ZACIAN)) {
                        if (pokemon.getForm().is("crowned"))
                            MyPokemon.changeMove(pokemon);
                    }
                    if (p.canMegaEvolve() && pokemon.getSpecies().is(PixelmonSpecies.RAYQUAZA)) {
                        MyPokemon.mega(pokemon);
                    }
                }
            }
            return;
        }
        if (event instanceof UseMoveSkillEvent) {
            UseMoveSkillEvent e = (UseMoveSkillEvent) event;
            if (e.moveSkill.id.equalsIgnoreCase("ignite")) {
                e.setCanceled(true);
                return;
            }
            if(e.moveSkill.id.equalsIgnoreCase("smelt")){
                e.setCanceled(true);
            }
        }
    }
}