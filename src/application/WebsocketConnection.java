package application;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

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
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
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
        System.out.println(message);
        Helper.setSocketMessage(message);
    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) + " Code: " + code + " Reason: " + reason );
    }

    @Override
    public void onError( Exception ex ) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
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
        WebsocketConnection c = new WebsocketConnection( new URI( "ws://gossip-ict.tk/websocket?token=" + token)); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        c.connect();
    }

}