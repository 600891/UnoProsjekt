import React, { useEffect, useState } from "react";
import { over } from "stompjs";
import SockJS from "sockjs-client";

const SOCKET_URL = "http://localhost:8080/uno";
var stompClient = null;

const Uno = () => {
  const [gameData, setgameData] = useState({
    gameID: "",
    player1: "",
    player2: "",
    deck: "",
    discard: "",
  });

  // Logger all gameData hver gang den oppdateres
  useEffect(() => {
    console.log(gameData);
  }, [gameData]);

  const connect = () => {
    let Sock = new SockJS(SOCKET_URL);
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  /*
  Når en bruker kobler seg til, skal de subscribes til spillet med spillID-en som matcher.
*/
  const onConnected = () => {
    stompClient.subscribe("/" + gameData.gameID, onGameUpdate);
  };

  /*
  Tar inn en STOMP message, parser JSON til et JavaScript-objekt,
  pusher dataen til gameData
*/
  const onGameUpdate = (payload) => {
    var payloadData = JSON.parse(payload.body);
    gameData.push(payloadData);
    setgameData([...gameData]);
  };

  // --------------------------------
  // HANDLE & SEND MESSAGES
  // --------------------------------

  /*
  Trenger handlers for forskjellige ting en bruker kan gjøre, i første omgang bare å trekke kort. Når en spill trekker kort blir handleDrawCard kalt og oppdaterer deck og hånden til spilleren.
*/
  const handleDrawCard = () => {
    // Man vil ha en onClick på Deck-componenten som kaller handleDrawCard
  };

  const sendMove = () => {
    if (stompClient) {
      var move = {};
    }
  };

  return <div></div>;
};

export default Uno;
