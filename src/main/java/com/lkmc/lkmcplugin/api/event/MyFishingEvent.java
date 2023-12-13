package com.lkmc.lkmcplugin.api.event;

import com.pixelmonmod.pixelmon.api.events.FishingEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MyFishingEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    public UUID uuid;

    public MyFishingEvent(UUID uuid){
        this.uuid = uuid;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
