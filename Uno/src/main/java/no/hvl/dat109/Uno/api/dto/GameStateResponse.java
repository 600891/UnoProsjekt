package no.hvl.dat109.Uno.api.dto;

import java.util.List;

public record GameStateResponse(
        String gameID,
        String playerTurn,
        String playDirection,
        List<PlayerResponse> players,
        List<CardResponse> deck,
        List<CardResponse> discard
) { }
