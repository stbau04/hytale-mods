package org.hymods.hygames.core.effects;

import org.hymods.hygames.core.IEffect;
import org.hymods.hygames.core.runtime.GameContext;

import javax.annotation.Nullable;

public class MultiStageEffect implements IEffect {
    private final IEffect[] effects;

    @Nullable
    private IEffect currentEffect = null;
    private boolean isCanceled = false;

    public MultiStageEffect(IEffect... effects) {
        this.effects = effects;
    }

    @Override
    public void run(GameContext context) {
        for (var effect : effects) {
            currentEffect = effect;
            if (isCanceled)
                return;

            currentEffect.run(context);
        }
    }

    @Override
    public void cancel(GameContext context) {
        isCanceled = true;

        if (currentEffect == null)
            return;
        currentEffect.cancel(context);
    }
}
