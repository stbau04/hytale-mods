package org.hymods.hygames.core.commands;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.hymods.hygames.core.IEffect;
import org.hymods.hygames.core.runtime.GameContext;

import java.util.function.Supplier;

public class GameCommand extends CommandBase {
    private final Supplier<IEffect> effect;

    public GameCommand(String name, String description, Supplier<IEffect> effect) {
        super(name, description);
        this.effect = effect;
    }

    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {
        if (!commandContext.isPlayer()) {
            var message = Message.raw("This command can only be called by players");
            commandContext.sender().sendMessage(message);
            return;
        }

        var players = new Player[]{ (Player)commandContext.sender() };
        var context = new GameContext(players);
        effect.get().run(context);
    }
}
