package no.hvl.dat109.Uno.api.dto;

import java.util.List;

public record ListOfGamesResponse(
   List<GameResponse> listOfGames
){ }
