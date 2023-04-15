import { useEffect, useState, useRef } from "react";
import { useNavigate, Navigate } from "react-router-dom";
import PlayerHand from "./PlayerHand";
import DiscardPile from "./DiscardPile";
import Deck from "./Deck";
import Opponent from "./Opponent";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import testData from "../data/testdata.json";

const GameRoom = () => {
  //Check if the user is logged in. //TODO Should implement a call to a controller to check if user is logged in
  let isLoggedIn = localStorage.getItem("session");
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
  const ENDPOINT = "http://localhost:8080";

  // init stompClient from localStorage
  const stompClientRef = useRef(() => {
    const saved = localStorage.getItem("stompClient");
    const initialValue = JSON.parse(saved);
    return initialValue || null;
  });

  // init username from localStorage
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem("username");
    const initialValue = saved;
    return initialValue || "";
  });

  // init gameID from localStorage
  const [gameID, setGameID] = useState(() => {
    const saved = localStorage.getItem("activeRoom");
    const initialValue = saved;
    return initialValue || "";
  });

  // init session from localStorage
  const session = () => {
    const saved = localStorage.getItem("session");
    const initialValue = saved;
    return initialValue || "";
  };
  // message state
  const [message, setMessage] = useState(null);
  // Create and connect to socket

  useEffect(() => {
    const socket = new SockJS(ENDPOINT + "/uno");
    const stompClient = Stomp.over(socket);
    stompClient.connect({ username: localStorage.username }, (frame) => {
      console.log("Connected to the websocket server " + frame);
      stompClient.subscribe(`/user/topic/gameroom/${gameID}`, (message) =>
        onMessageReceived(message)
      );
      stompClient.subscribe(`/topic/gameroom/${gameID}`, (message) =>
        onMessageReceived(message)
      );
      stompClient.send(`/api/gameroom/${gameID}`, {}, {});
    });
    stompClientRef.current = stompClient;
  }, []);
  const onMessageReceived = (payload) => {
    console.log("onMessageReceived");
    var message = JSON.parse(payload.body);
  };

  // **********
  // GAME
  // **********
  const [gameState, setGameState] = useState(null);

  const [playDirection, setPlayDirection] = useState("");
  const [playerTurn, setPlayerTurn] = useState("");

  // Aktiv spiller
  const [yourPlayer, setYourPlayer] = useState(null);
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
  const onTurnPlayedHandler = (e) => {
    e.preventDefault();

    // Rekkefølge: gameID, playerTurn, playDirection, player1-10, deck, discard
    // Checks if the message is empty and if the user is currently logged in
    if (message.trim() && localStorage.getItem("userName")) {
      stompClientRef.send("message", {
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
    }

    setMessage("");
  };

  // Will redirect the user to the log in page if not logged in
  if (!isLoggedIn) {
    return <Navigate to="/" />;
  }

  //Will render the GameRoom if user is logged in
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
