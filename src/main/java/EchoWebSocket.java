import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 拿客 www.coderknock.com
 * 微信公众号 coderknock
 * 作者：三产
 */
@WebSocket
public class EchoWebSocket {
    private static Logger logger = LoggerFactory.getLogger(EchoWebSocket.class);
    // Store sessions if you want to, for example, broadcast a message to all users
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    @OnWebSocketConnect
    public void connected(Session session) {
        sessions.add(session);
        //建立连接的时候
        logger.debug("新增了Session" + session.toString());
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
        //关闭连接或者浏览器关闭时
        logger.debug("删除了Session" + session.toString());
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("Got: " + message);   // Print message
        session.getRemote().sendString(message + "1231"); // and send it back
    }

    public static void send(int i) {
        sessions.forEach(session -> {
                    try {
                        session.getRemote().sendString("第" + i + "次主动推送");
                    } catch (Exception e) {
                        logger.error("主动推送失败", e);
                    }
                }
        );
    }
}