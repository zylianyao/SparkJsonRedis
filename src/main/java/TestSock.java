import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.net.SocketHttpConnectionProvider;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 拿客 www.coderknock.com
 * 微信公众号 coderknock
 * 作者：三产
 */
public class TestSock {
    public static void main(String[] args) {

        HttpConnectionProvider connectionProvider = new MyConnectionProvider();
        HttpRequest request = HttpRequest.get("http://localhost:9090/socke");
        HttpResponse response = request.open(connectionProvider).send();
        System.out.println(request.body());
    }
}

class MyConnectionProvider extends SocketHttpConnectionProvider {
    protected Socket createSocket(
            SocketFactory socketFactory, String host, int port)
            throws IOException {
        Socket socket = super.createSocket(host, port, 1000);
        return socket;
    }
}

