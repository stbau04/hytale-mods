package org.hymods.hygames.core.effects;

import org.hymods.hygames.core.IEffect;
import org.hymods.hygames.core.runtime.GameContext;

import java.util.ArrayList;

public class ParallelEffect implements IEffect {
    private final IEffect[] effects;

    private boolean isCanceled = false;

    public ParallelEffect(IEffect... effects) {
        this.effects = effects;
    }

    @Override
    public void run(GameContext context) {
        if (isCanceled)
            return;

        var threads = new ArrayList<Thread>(effects.length);
        for (var effect : effects) {
            var thread = new Thread(() -> effect.run(context));
            thread.start();

            threads.add(thread);
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException _) { }
        }
    }

    @Override
    public void cancel(GameContext context) {
        isCanceled = true;

        for (var effect : effects) {
            effect.cancel(context);
        }
    }
}
