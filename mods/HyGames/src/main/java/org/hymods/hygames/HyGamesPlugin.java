package org.hymods.hygames;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.ecs.DamageBlockEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import org.hymods.hygames.core.GameBuilder;
import org.hymods.hygames.core.effects.*;
import org.hymods.hygames.core.events.WorldInteractionEvents;
import org.hymods.hygames.core.passive.MultiPassiveEffect;

import javax.annotation.Nonnull;
import java.awt.*;

@SuppressWarnings("unused")
public class HyGamesPlugin extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    @SuppressWarnings("unused")
    public HyGamesPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("Setting up plugin " + this.getName());

        var lobbyHub = new LobbyEffect.LobbyHub(1);
        new GameBuilder()
            .setEffect(() -> new MultiStageEffect(
                new PassiveEffect(
                    new ParallelEffect(
                        new MessageEffect(Message.raw("Joining lobby!")
                                .bold(true)
                                .color(Color.GREEN)),
                        new LobbyEffect(
                                lobbyHub,
                                new MultiPassiveEffect(),
                                new MessageEffect(Message.raw("Game started!").color(Color.RED)))
                    )
                )
            ))
            .build("pvp",  "Play a simple PvP match",this);
    }
}