var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/uno');
    stompClient = Stomp.over(socket);
    stompClient.connect({'username': $("#name").val()}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        showGreeting('Connected: ' + frame);
        stompClient.subscribe('/user/topic/lobby', function (greeting) {
            showGreeting(greeting.body);
        });
        stompClient.subscribe('/topic/lobby', function (response) {
           //var parsedResponse = JSON.parse(response.body);
           //console.log(response.body);
            showGreeting(response.body);
        });
        lobby(); // update with existing games at startup
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

//function sendInput() {
//    stompClient.send("/api/join", {}, JSON.stringify({'input': $("#input").val()}));
//}

function createGame() {
    stompClient.send("/api/lobby/game/create", {}, {});
}

function lobby() {
    stompClient.send("/api/lobby", {}, {});
}

function joinGameInLobby() {
    stompClient.send("/api/lobby/game/join/" + $("#gameId").val(), {}, {})
}

function leaveGameInLobby() {
    stompClient.send("/api/lobby/game/leave", {}, {});
}

function startGameInLobby() {
    stompClient.send("/api/lobby/game/start/" + $("#gameId").val(), {}, {})
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { createGame(); });
    $( "#check-lobby" ).click(function() { lobby(); });
    $( "#join-game-lobby" ).click(function() { joinGameInLobby(); })
    $( "#leave-game-lobby" ).click(function() { leaveGameInLobby(); })
    $( "#start-game-lobby" ).click(function() { startGameInLobby(); })
    //$( "#send" ).click(function() { sendInput(); });
});

