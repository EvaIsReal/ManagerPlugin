package de.iv.manager.text;

import org.bukkit.entity.Player;

import java.util.Collection;

public abstract class AbstractTextChannel {

    abstract Collection<? extends Player> getRecievers();
    abstract String name();



}
