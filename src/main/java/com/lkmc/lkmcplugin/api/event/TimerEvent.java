package com.lkmc.lkmcplugin.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TimerEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    public UUID uuid;

    public TimerEvent(UUID uuid){
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
