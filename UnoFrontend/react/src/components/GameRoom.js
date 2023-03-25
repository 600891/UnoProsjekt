import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PlayerHand from "./PlayerHand";
import DiscardPile from "./DiscardPile";
import SockJS from "sockjs-client";
import Stomp from "react-stomp";
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
  // Create and connect to socket

  /*const connect = () => {
    const connectionOptions = {
      timeout: 10000,
      transports: ["websocket"],
    };
    socket = new SockJS(ENDPOINT, connectionOptions);
    stompClient = Stomp.over(socket);
    setGameState(data);
    console.log(gameState);
    stompClient.connect({}, onConnected(), onError());
  };*/

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

  const [yourPlayer, setYourPlayer] = useState("");
  const [yourPlayerHand, setYourPlayerHand] = useState(null);
  const [currentTopCard, setCurrentTopCard] = useState("");

  // Runs once on component mount, sets up game data from initial game state
  useEffect(() => {
    setGameState(data);

    //setYourPlayer(gameState.player1.name);
    //setYourPlayerHand(gameState.player1.hand);
    //setCurrentColor(gameState.discard.at(0));
  }, []);

  useEffect(() => {
    if (gameState != null) {
      console.log(gameState);
      setYourPlayer(gameState.player1.name);
      setYourPlayerHand(gameState.player1.hand);
      setCurrentTopCard(gameState.discard[gameState.discard.length - 1]);
    }
  }, [gameState]);

  return (
    <div
      className={`Game backgroundColorR backgroundColor${currentTopCard.cardColor}`}
    >
      <h1>You have entered the game room!</h1>
      <button onClick={returnToLobby}>Go back to lobby</button>
      <DiscardPile></DiscardPile>
      <PlayerHand
        name={yourPlayer}
        hand={yourPlayerHand}
        changeCards={setYourPlayerHand}
        currentTopCard={currentTopCard}
      ></PlayerHand>
    </div>
  );
};

export default GameRoom;
