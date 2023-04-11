import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PlayerHand from "./PlayerHand";
import DiscardPile from "./DiscardPile";
import Deck from "./Deck";
import Opponent from "./Opponent";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import testData from "../data/testdata.json";

const ENDPOINT = "http://localhost:8080";

const GameRoom = (props) => {
  // Get data from mock JSON for testing
  const data = testData;

  // **********
  // NAVIGATION
  // **********
  const navigate = useNavigate();

  const returnToLobby = () => {
    navigate("/lobby", { replace: true });
  };

  // **********
  // WEBSOCKET
  // **********
  let socket = null;
  let stompClient = null;

  // init socket state
  const [gameState, setGameState] = useState(null);

  // message state
  const [message, setMessage] = useState(null);
  // Create and connect to socket

  useEffect(() => {
    socket = new WebSocket("ws://localhost:8080/uno");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      console.log("Connected to the websocket server");

      stompClient.subscribe("/gameroom/1", (message) => {
        console.log("Received message:", message);
      });
    });
  }, []);

  const onConnected = () => {
    console.log("onConnected");
    // Subscribe to the Game Room API
    stompClient.subscribe(
      ENDPOINT + "/gameroom/" + gameState.gameID,
      this.onMessageReceived
    );
    stompClient.subscribe(
      ENDPOINT + "/api/gameroom/" + gameState.gameID,
      this.onMessageReceived
    );
  };
  const onMessageReceived = (payload) => {
    console.log("onMessageReceived");
    var message = JSON.parse(payload.body);
  };
  const onError = (error) => {
    this.setState({
      error:
        "Could not connect you to the Uno Server. Please refresh this page and try again!",
    });
  };
  const sendMessage = (msg) => {
    var messageContent = "test";
    if (messageContent && stompClient) {
      var gameStateMessage = {
        sender: this.state.name,
        content: "Heey there",
        type: "GAMESTATE",
      };
      stompClient.send(
        "/uno/" + gameState.gameID,
        { name: yourPlayer.name },
        JSON.stringify(gameStateMessage)
      );
    }
  };

  // **********
  // GAME
  // **********

  const [gameID, setGameID] = useState(null);
  const [playDirection, setPlayDirection] = useState("");
  const [playerTurn, setPlayerTurn] = useState("");

  // Aktiv spiller
  const [yourPlayer, setYourPlayer] = useState("");
  const [yourPlayerHand, setYourPlayerHand] = useState(null);

  // Motstandere, finnes kanskje en bedre måte å gjøre dette på men for nå har vi en state per motstander (9 motstandere)
  const [opponentOne, setOpponentOne] = useState(null);
  const [opponentTwo, setOpponentTwo] = useState(null);
  const [opponentThree, setOpponentThree] = useState(null);
  const [opponentFour, setOpponentFour] = useState(null);
  const [opponentFive, setOpponentFive] = useState(null);
  const [opponentSix, setOpponentSix] = useState(null);
  const [opponentSeven, setOpponentSeven] = useState(null);
  const [opponentEight, setOpponentEight] = useState(null);
  const [opponentNine, setOpponentNine] = useState(null);

  // Bordet
  const [currentTopCard, setCurrentTopCard] = useState("");
  const [discardPile, setDiscardPile] = useState("");
  const [currentColor, setCurrentColor] = useState("");
  const [deck, setDeck] = useState("");

  // Runs once on component mount, sets up game data from initial game state
  useEffect(() => {
    setGameState(data);
  }, []);

  // Denne useEffecten skal kun hente info fra gameState! Henter man info fra andre ting kan det være det ikke blir lastet
  // Kan feks ikke sette currentColor fra currentTopCard, den må settes fra gameState.
  useEffect(() => {
    if (gameState != null) {
      setMessage(null); // Tilbakestill message hver gang man får oppdatering
      console.log(gameState);

      setGameID(gameState.gameID);
      setPlayerTurn(gameState.playerTurn);
      setPlayDirection(gameState.playDirection);

      setYourPlayer(gameState.player1);
      setYourPlayerHand(gameState.player1.hand);

      setOpponentOne(gameState.player2);
      setOpponentTwo(gameState.player3);
      setOpponentThree(gameState.player4);
      setOpponentFour(gameState.player5);
      setOpponentFive(gameState.player6);
      setOpponentSix(gameState.player7);
      setOpponentSeven(gameState.player8);
      setOpponentEight(gameState.player9);
      setOpponentNine(gameState.player10);

      setDeck(gameState.deck);
      setCurrentTopCard(gameState.discard[0]);
      setDiscardPile(gameState.discard);
      setCurrentColor(gameState.discard[0].cardColor);
    }
  }, [gameState]);

  // Konstruer et nytt message-objekt ut fra klientens state etter at spilleren har gjort et trekk
  const onTurnPlayedHandler = () => {
    // Rekkefølge: gameID, playerTurn, playDirection, player1-10, deck, discard
    setMessage({
      gameID: gameID,
      playerTurn: playerTurn,
      playDirection: playDirection,
      player1: yourPlayer,
      player2: opponentOne,
      player3: opponentTwo,
      player4: opponentThree,
      player5: opponentFour,
      player6: opponentFive,
      player7: opponentSix,
      player8: opponentSeven,
      player9: opponentEight,
      player10: opponentNine,
      deck: deck,
      discard: discardPile,
    });
  };
  useEffect(() => {
    // Send denne meldingen til backend
    console.log(message);
  }, [message]);

  return (
    <div className={`Game backgroundColorR backgroundColor${currentColor}`}>
      <div className="navBar">
        <h1>You are in game {gameID}</h1>
        <button className="button" onClick={returnToLobby}>
          Go back to lobby
        </button>
      </div>
      <div className="gameTable">
        <div className="sideColumn opponentsLeft">
          <Opponent opponent={opponentOne}></Opponent>
          <Opponent opponent={opponentTwo}></Opponent>
          <Opponent opponent={opponentThree}></Opponent>
        </div>
        <div className="middleTable">
          <div className="flexRow opponentsTop" style={{ width: "70%" }}>
            <Opponent opponent={opponentFour}></Opponent>
            <Opponent opponent={opponentFive}></Opponent>
            <Opponent opponent={opponentSix}></Opponent>
          </div>
          <div className="flexRow" style={{ width: "40%" }}>
            <DiscardPile
              name="discardPile"
              changeCards={setCurrentTopCard}
              currentTopCard={currentTopCard}
            ></DiscardPile>
            <Deck
              name="deck"
              setDeck={setDeck}
              deck={deck}
              setYourPlayerHand={setYourPlayerHand}
              yourPlayerHand={yourPlayerHand}
            ></Deck>
          </div>
          <PlayerHand
            player={yourPlayer}
            hand={yourPlayerHand}
            changeHand={setYourPlayerHand}
            currentCard={currentTopCard}
            setCurrentCard={setCurrentTopCard}
            currentColor={currentColor}
            setCurrentColor={setCurrentColor}
            direction={playDirection}
            setDirection={setPlayDirection}
            turnHandler={onTurnPlayedHandler}
          ></PlayerHand>
        </div>
        <div className="sideColumn opponentsRight">
          <Opponent opponent={opponentSeven}></Opponent>
          <Opponent opponent={opponentEight}></Opponent>
          <Opponent opponent={opponentNine}></Opponent>
        </div>
      </div>
    </div>
  );
};

export default GameRoom;
