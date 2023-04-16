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

  //**********
  // INIT FROM LOCALSTORAGE
  //**********
  const stompClientRef = useRef(() => {
    const saved = localStorage.getItem("stompClient");
    const initialValue = JSON.parse(saved);
    return initialValue || null;
  });

  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem("username");
    const initialValue = saved;
    return initialValue || "";
  });

  const [gameID, setGameID] = useState(() => {
    const saved = localStorage.getItem("activeRoom");
    const initialValue = saved;
    return initialValue || "";
  });

  const session = () => {
    const saved = localStorage.getItem("session");
    const initialValue = saved;
    return initialValue || "";
  };

  // **********
  // GAME STATES
  // **********

  const [gameState, setGameState] = useState(null);

  const [playDirection, setPlayDirection] = useState("");
  const [playerTurn, setPlayerTurn] = useState("");
  const [players, setPlayers] = useState([]);

  // Aktiv spiller
  const [yourPlayer, setYourPlayer] = useState(null);
  const [yourPlayerHand, setYourPlayerHand] = useState(null);

  // Motstandere
  const [opponent1, setOpponent1] = useState(null);
  const [opponent2, setOpponent2] = useState(null);
  const [opponent3, setOpponent3] = useState(null);
  const [opponent4, setOpponent4] = useState(null);
  const [opponent5, setOpponent5] = useState(null);
  const [opponent6, setOpponent6] = useState(null);
  const [opponent7, setOpponent7] = useState(null);
  const [opponent8, setOpponent8] = useState(null);
  const [opponent9, setOpponent9] = useState(null);

  // Bordet
  const [currentTopCard, setCurrentTopCard] = useState("");
  const [discardPile, setDiscardPile] = useState("");
  const [currentColor, setCurrentColor] = useState("");
  const [deck, setDeck] = useState("");

  // **********
  // WEBSOCKET
  // **********
  const ENDPOINT = "http://localhost:8080";

  // message state
  const [message, setMessage] = useState(null);

  // Create and connect to socket
  useEffect(() => {
    // If there is no connection, connect
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
      // send message to backend to receive initial game state
      stompClient.send(`/api/gameroom/${gameID}/start`, {}, {});
    });
    stompClientRef.current = stompClient;
  }, []);

  useEffect(() => {
    localStorage.setItem("stompClient", stompClientRef.current);
  }, [stompClientRef]);

  // Message handler
  // IMPORTANT! This callback method can not read states, only update them. This includes functions called from here
  const onMessageReceived = (message) => {
    const messageObj = JSON.parse(message.body);
    console.log(messageObj);
    // handle all message types:

    // errorMessage-property
    if (messageObj.hasOwnProperty("errorMessage")) {
      console.log("Error Message: " + message);
    } else {
      // START_GAME_EVENT
      const gameStateObj = messageObj.gameState;
      updateGame(gameStateObj);
    }
  };

  // **********
  // GAME
  // **********

  let turnPlayed;
  // Update the game state when you receive a start game message or update message, called from message handler
  const updateGame = (gamestateObject) => {
    setPlayDirection(gamestateObject.playDirection);
    const statePlayers = gamestateObject.players;
    let id = 1;
    setPlayers(statePlayers);
    statePlayers.forEach((player) => {
      // Check if this is your player
      if (player.name === localStorage.getItem("username")) {
        setYourPlayer(player);
        setYourPlayerHand(player.hand);
      } else {
        switch (id) {
          case 1:
            setOpponent1(player);
            break;
          case 2:
            setOpponent2(player);
            break;
          case 3:
            setOpponent3(player);
            break;
          case 4:
            setOpponent4(player);
            break;
          case 5:
            setOpponent5(player);
            break;
          case 6:
            setOpponent6(player);
            break;
          case 7:
            setOpponent7(player);
            break;
          case 8:
            setOpponent8(player);
            break;
          case 9:
            setOpponent9(player);
            break;
          default:
            break;
        }

        id++;
      }
      if (gamestateObject.playerTurn === player.name) {
        setPlayerTurn(player);
      }
    });

    setDeck(gamestateObject.deck);
    setDiscardPile(gamestateObject.discard);
    setCurrentTopCard(gamestateObject.discard.at(0));
    setCurrentColor(gamestateObject.discard.at(0).cardColor);
  };

  useEffect(() => {
    if (turnPlayed === true) {
      setGameState(
        JSON.stringify({
          gameState: {
            gameID: gameID,
            playerTurn: playerTurn.name,
            playDirection: playDirection,
            players,
            deck: deck,
            discard: discardPile,
          },
        })
      );
      console.log("Game state changed");
      onTurnPlayedHandler();
      turnPlayed = false;
    }
  }, [yourPlayerHand]);

  // Konstruer et nytt message-objekt ut fra klientens state etter at spilleren har gjort et trekk
  const onTurnPlayedHandler = () => {
    // Rekkefølge: gameID, playerTurn, playDirection, player1-10, deck, discard
    // Checks if the message is empty and if the user is currently logged in
    stompClientRef.current.send(`/api/gameroom/${gameID}`, {}, gameState);
  };

  // Will redirect the user to the log in page if not logged in
  if (!isLoggedIn) {
    stompClientRef.current.disconnect(() => {
      console.log("User disconnected");
    });
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
          <Opponent opponent={opponent3}></Opponent>
          <Opponent opponent={opponent2}></Opponent>
          <Opponent opponent={opponent1}></Opponent>
        </div>
        <div className="middleTable">
          <div className="flexRow opponentsTop" style={{ width: "70%" }}>
            <Opponent opponent={opponent4}></Opponent>
            <Opponent opponent={opponent5}></Opponent>
            <Opponent opponent={opponent6}></Opponent>
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
            players={players}
            player={yourPlayer}
            hand={yourPlayerHand}
            deck={deck}
            discard={discardPile}
            setDiscard={setDiscardPile}
            changeHand={setYourPlayerHand}
            currentCard={currentTopCard}
            setCurrentCard={setCurrentTopCard}
            currentColor={currentColor}
            setCurrentColor={setCurrentColor}
            direction={playDirection}
            setDirection={setPlayDirection}
            playerTurn={playerTurn}
            setPlayerTurn={setPlayerTurn}
            turnPlayed={turnPlayed}
          ></PlayerHand>
        </div>
        <div className="sideColumn opponentsRight">
          <Opponent opponent={opponent7}></Opponent>
          <Opponent opponent={opponent8}></Opponent>
          <Opponent opponent={opponent9}></Opponent>
        </div>
      </div>
    </div>
  );
};

export default GameRoom;
