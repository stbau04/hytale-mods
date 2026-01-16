package org.hymods.hygames.core;

import org.hymods.hygames.core.runtime.GameContext;

public interface IEffect {
    void run(GameContext context);
    void cancel(GameContext context);
}
