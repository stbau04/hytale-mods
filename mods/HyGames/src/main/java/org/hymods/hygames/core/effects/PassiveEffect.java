package org.hymods.hygames.core.effects;

import org.hymods.hygames.core.IEffect;
import org.hymods.hygames.core.IPassiveEffect;
import org.hymods.hygames.core.runtime.GameContext;

public class PassiveEffect implements IEffect {
    private final IEffect effect;
    private final IPassiveEffect[] passiveEffects;

    private boolean isCanceled = false;

    public PassiveEffect(IEffect effect, IPassiveEffect... passiveEffects) {
        this.effect = effect;
        this.passiveEffects = passiveEffects;
    }

    @Override
    public void run(GameContext context) {
        if (isCanceled)
            return;

        for (var passiveEffect : passiveEffects) {
            passiveEffect.join(context);
        }

        if (!isCanceled)
            effect.run(context);

        for (var passiveEffect : passiveEffects) {
            passiveEffect.dispose(context);
        }
    }

    @Override
    public void cancel(GameContext context) {
        isCanceled = true;
        effect.cancel(context);
    }
}
