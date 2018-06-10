package application;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import message.Message;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
public class WebsocketConnection extends WebSocketClient {

    public WebsocketConnection( URI serverUri , Draft draft ) {
        super( serverUri, draft );
    }

    public WebsocketConnection( URI serverURI ) {
        super( serverURI );
    }

    public WebsocketConnection( URI serverUri, Map<String, String> httpHeaders ) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        System.out.println( "opened connection" );
    }

    /*message: {
                    "roomID": 1,
                   "message": "aloo",
                   "from_userID": 10,
                   "sending_time": ......
                }
    */
    @Override
    public void onMessage( String message ) {
        System.out.println("onMessage " + message);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        if (!message.contains("session"))
        {
            try {
                jsonNode = mapper.readTree(message);

                Message message1 = new Message();
                message1.setRoomID(jsonNode.get("roomID").asInt());
                message1.setFromID(jsonNode.get("from_userID").asInt());
                message1.setMsg(jsonNode.get("message").toString());
                message1.setSendingTime(jsonNode.get("sending_time").toString());
                Helper.setSocketMessage(message1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) + " Code: " + code + " Reason: " + reason );
    }

    @Override
    public void onError( Exception ex ) {
        ex.printStackTrace();
    }

    /*
    sending_message: {
                        "roomID": 1,
                        "message": "alooooo"
                        }
     */
    public void sendMessage(String message){
        send(message);
    }

    public static void connects(String token) throws URISyntaxException {
        WebsocketConnection c = new WebsocketConnection( new URI( "ws://202.182.118.224:8080/ws?token=" + token));
        c.connect();
    }

}