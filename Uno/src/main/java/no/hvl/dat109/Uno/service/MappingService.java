package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.api.GameResponse;
import no.hvl.dat109.Uno.api.ListOfGamesResponse;
import no.hvl.dat109.Uno.persistence.entity.Game;
import no.hvl.dat109.Uno.persistence.entity.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MappingService {

    public ListOfGamesResponse map(List<Game> games) {
        List<GameResponse> result = new ArrayList<>();
        games.forEach(game -> result.add(map(game)));
        return new ListOfGamesResponse(result);
    }

    public GameResponse map(Game game) {
        List<String> playerNames = game.getPlayers().stream().map(Player::getName).toList();
        return new GameResponse(game.getUuid(), game.getGameCreator().getName(), playerNames);
    }
}
