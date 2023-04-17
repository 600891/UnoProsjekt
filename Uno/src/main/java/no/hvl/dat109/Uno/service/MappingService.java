package no.hvl.dat109.Uno.service;

import no.hvl.dat109.Uno.api.dto.*;
import no.hvl.dat109.Uno.persistence.entity.Card;
import no.hvl.dat109.Uno.persistence.entity.Game;
import no.hvl.dat109.Uno.persistence.entity.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MappingService {

    // Lobby responses

    public ListOfGamesResponse map(List<Game> games) {
        List<GameResponse> result = new ArrayList<>();
        games.forEach(game -> result.add(map(game)));
        return new ListOfGamesResponse(result);
    }

    public GameResponse map(Game game) {
        List<String> playerNames = game.getPlayers().stream().map(Player::getName).toList();
        return new GameResponse(game.getGameId(), game.getGameCreator().getName(), playerNames);
    }

    // Game room responses

    public GameStateResponse mapGameState(Game game){
        List<PlayerResponse> players = new ArrayList<>();
        for(Player player : game.getPlayers()){
            players.add(mapPlayer(player));
        }
        List<CardResponse> deck = new ArrayList<>();
        for(Card card: game.getDeck().getDeck()){
            deck.add(mapCard(card));
        }
        List<CardResponse> discard = new ArrayList<>();
        for(Card card: game.getDiscard().getDeck()){
            discard.add(mapCard(card));
        }
        String playColor = game.getDiscard().getDeck().get(0).getColor().toString().toLowerCase();
        return new GameStateResponse(game.getUuid(), game.getActivePlayer().getName(), game.getPlayDirection(), playColor, players,deck,discard);
    }

    public PlayerResponse mapPlayer(Player player){
        List<CardResponse> hand = new ArrayList<>();
        for(Card card: player.getHand()){
            hand.add(mapCard(card));
        }
        return new PlayerResponse(player.getName(), hand);
    }

    public CardResponse mapCard(Card card){
        String cardID = Integer.toString(card.getId());
        String color = card.getColor().toString().toLowerCase();
        String value = card.getValue().toString().toLowerCase();
        return new CardResponse(cardID, color, value);
    }

}
