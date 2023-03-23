import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import Stomp from "react-stomp";

const ENDPOINT = "http://localhost:8080";

const GameRoom = (props) => {
  // Mock JSON for testing
  const testdata = {
    gameID: 1,
    playerTurn: "player1",
    player1: {
      name: "Nora",
      hand: [
        {
          cardColor: "red",
          cardValue: "zero",
        },
        {
          cardColor: "blue",
          cardValue: "skip",
        },
        {
          cardColor: "blue",
          cardValue: "skip",
        },
      ],
    },
    player2: {
      name: "Stian",
      hand: [
        {
          cardColor: "blue",
          cardValue: "five",
        },
        {
          cardColor: "black",
          cardValue: "wild",
        },
        {
          cardColor: "blue",
          cardValue: "skip",
        },
      ],
    },
    deck: [
      {
        cardColor: "blue",
        cardValue: "five",
      },
      {
        cardColor: "black",
        cardValue: "wild",
      },
      {
        cardColor: "blue",
        cardValue: "skip",
      },
    ],
    discard: [
      {
        cardColor: "blue",
        cardValue: "five",
      },
      {
        cardColor: "black",
        cardValue: "wild",
      },
      {
        cardColor: "blue",
        cardValue: "skip",
      },
    ],
  };

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
  const connect = () => {
    const connectionOptions = {
      timeout: 10000,
      transports: ["websocket"],
    };
    socket = new SockJS(ENDPOINT, connectionOptions);
    stompClient = Stomp.over(socket);
    setGameState(testdata);
    console.log(gameState);
    stompClient.connect({}, onConnected(), onError());
  };

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
    // Tell your username to the server
    /*stompClient.send(
      "/uno/" + gameState.gameID,
      {},
      JSON.stringify({ sender: "Ali", type: "JOIN" })
    );*/
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
  const [yourPlayerHand, setYourPlayerHand] = useState([]);

  const onCardPlayedHandler = (played_card) => {};

  return (
    <div>
      <h1>You have entered the game room!</h1>
      <button onClick={returnToLobby}>Go back to lobby</button>
      <div>
        <div className="yourPlayer">
          <p className="playerDeckText">You</p>
          {yourPlayerHand.map((item, i) => (
            <img
              key={i}
              className="Card"
              onClick={() => onCardPlayedHandler(item)}
              src={require(`../assets/cards-front/${item}.png`).default}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default GameRoom;
