package org.hymods.hygames.core.passive;

import org.hymods.hygames.core.IPassiveEffect;
import org.hymods.hygames.core.runtime.GameContext;

public class MultiPassiveEffect implements IPassiveEffect {
    private final IPassiveEffect[] effects;
    private boolean isCanceled = false;

    public MultiPassiveEffect(IPassiveEffect... effects) {
        this.effects = effects;
    }

    @Override
    public void join(GameContext context) {
        if (isCanceled)
            return;

        for (var effect : effects) {
            effect.join(context);
        }
    }

    @Override
    public void dispose(GameContext context) {
        isCanceled = true;

        for (var effect : effects) {
            effect.dispose(context);
        }
    }
}
