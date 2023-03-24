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
        stompClient.subscribe('/user/topic/reply', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/foo', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendInput() {
    stompClient.send("/app/join", {}, JSON.stringify({'input': $("#input").val()}));
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
    $( "#send" ).click(function() { sendInput(); });
});

//var stompClient = null;
//
//function connect(username) {
//    var socket = new SockJS('/uno');
//    stompClient = Stomp.over(socket);
//    stompClient.connect({ username: username, }, function() {
//        console.log('Web Socket is connected');
//        stompClient.subscribe('/users/topic/reply', function(message) {
//            $("#message").append("<tr><td>" + message.body + "</td></tr>");
//        });
//        stompClient.subscribe('/topic/foo', function(message) {
//            $("#message").append("<tr><td>" + message.body + "</td></tr>");
//        });
//    });
//}
//
//$(function() {
//    $("form").on('submit', function(e) {
//        e.preventDefault();
//    });
//    $("#connect").click(function() {
//        connect($("#username").val());
//    });
//    $("#send").click(function() {
//        stompClient.send("/app/join", {}, $("#input").val());
//    });
//});