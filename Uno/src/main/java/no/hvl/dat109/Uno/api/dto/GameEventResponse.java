package no.hvl.dat109.Uno.api.dto;

import no.hvl.dat109.Uno.enums.GameEvent;

public record GameEventResponse(
    GameStateResponse gameState,
    GameEvent event
) {}
