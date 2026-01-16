package org.hymods.hygames.core.effects;

import com.hypixel.hytale.server.core.Message;
import org.hymods.hygames.core.IEffect;
import org.hymods.hygames.core.runtime.GameContext;

public class MessageEffect implements IEffect {
    private final  Message message;

    public  MessageEffect(Message message) {
        this.message = message;
    }

    @Override
    public void run(GameContext context) {
        for (var player : context.players()) {
            player.sendMessage(message);
        }
    }

    @Override
    public void cancel(GameContext context) {

    }
}
