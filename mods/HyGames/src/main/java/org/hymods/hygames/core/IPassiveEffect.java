package org.hymods.hygames.core;

import org.hymods.hygames.core.runtime.GameContext;

public interface IPassiveEffect {
    void join(GameContext context);
    void dispose(GameContext context);
}
