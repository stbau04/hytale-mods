package org.hymods.hygames.core;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import org.hymods.hygames.core.commands.GameCommand;

import java.util.function.Supplier;

public class GameBuilder {
    private Supplier<IEffect> effect;

    public void build(String name, String description, JavaPlugin plugin) {
        var command = new GameCommand(name, description, effect);
        plugin.getCommandRegistry().registerCommand(command);
    }

    public GameBuilder setEffect(Supplier<IEffect> effect) {
        this.effect = effect;
        return this;
    }
}
