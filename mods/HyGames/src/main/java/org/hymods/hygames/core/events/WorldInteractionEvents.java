package org.hymods.hygames.core.events;

import com.hypixel.hytale.server.core.event.events.ecs.DamageBlockEvent;

public class WorldInteractionEvents {
    public void onWorldInteraction(DamageBlockEvent event) {
        event.setCancelled(true);
    }
}
