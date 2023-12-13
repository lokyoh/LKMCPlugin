package com.lkmc.lkmcplugin.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DailyRefreshEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public DailyRefreshEvent(){
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
