package com.nikolas.umbraldualblades.combat;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ComboTracker {
    private static final Map<UUID, State> STATES = new ConcurrentHashMap<>();

    private ComboTracker() { }

    /**
     * Records a successful impact and returns a stage in the inclusive range 1..7.
     * The stage resets after seven or when the configured gap is exceeded.
     */
    public static int recordSuccessfulImpact(UUID playerId, long gameTime, int resetTicks) {
        State state = STATES.computeIfAbsent(playerId, ignored -> new State());

        synchronized (state) {
            if (state.lastImpactTick == Long.MIN_VALUE || gameTime - state.lastImpactTick > resetTicks) {
                state.stage = 1;
            } else {
                state.stage = state.stage >= 7 ? 1 : state.stage + 1;
            }

            state.lastImpactTick = gameTime;
            return state.stage;
        }
    }

    public static void clear(UUID playerId) {
        STATES.remove(playerId);
    }

    private static final class State {
        private int stage;
        private long lastImpactTick = Long.MIN_VALUE;
    }
}
