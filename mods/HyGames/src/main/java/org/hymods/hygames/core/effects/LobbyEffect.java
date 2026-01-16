package org.hymods.hygames.core.effects;

import com.hypixel.hytale.server.core.entity.entities.Player;
import org.hymods.hygames.core.IEffect;
import org.hymods.hygames.core.algorithms.Knapsack;
import org.hymods.hygames.core.runtime.GameContext;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LobbyEffect implements IEffect {
    private final LobbyHub lobbyHub;
    private final IEffect effect;

    public LobbyEffect(LobbyHub lobbyHub, IEffect effect) {
        this.lobbyHub = lobbyHub;
        this.effect = effect;
    }

    @Override
    public void run(GameContext context) {
        lobbyHub.join(context, effect);
    }

    @Override
    public void cancel(GameContext context) {
        effect.cancel(context);
        lobbyHub.leave(context);
    }

    public static class LobbyHub {
        private final List<GameContext> gameContexts = new LinkedList<>();
        private final int playerCount;

        public LobbyHub(int playerCount) {
            this.playerCount = playerCount;
        }

        public void join(GameContext gameContext, IEffect effect) {
            synchronized (gameContexts) {
                gameContexts.add(gameContext);

                var matchup = findMatchup(gameContexts);
                if (matchup == null)
                    return;

                var players = new ArrayList<Player>(playerCount);
                for (var matchupContext : matchup) {
                    players.addAll(Arrays.stream(matchupContext.players()).toList());
                }

                var context = new GameContext(players.toArray(Player[]::new));
                effect.run(context);
            }
        }

        public void leave(GameContext gameContext) {
            synchronized (gameContexts) {
                gameContexts.remove(gameContext);
            }
        }

        @Nullable
        private List<GameContext> findMatchup(List<GameContext> gameContexts) {
            var partySizes = gameContexts.stream().map(context -> context.players().length);
            var matchup = findMatchupByPartySizes(partySizes.toList());
            if (matchup == null)
                return null;

            var result = new ArrayList<GameContext>(matchup.size());
            for (var i : matchup) {
                result.add(gameContexts.remove((int)i));
            }

            return result;
        }

        @Nullable
        private List<Integer> findMatchupByPartySizes(List<Integer> partySizes) {
            var playerSizeArray = partySizes.toArray(Integer[]::new);
            var result = Knapsack.solve(playerSizeArray, playerSizeArray, playerCount);

            var selectedPartySizes = new LinkedList<Integer>();
            var totalPartySize = 0;

            for (var i=0; i<result.length; i++) {
                if (result[i] == 1)
                {
                    selectedPartySizes.add(i);
                    totalPartySize += partySizes.get(i);
                }
            }

            if (totalPartySize < playerCount)
                return null;
            return  selectedPartySizes;
        }
    }
}
