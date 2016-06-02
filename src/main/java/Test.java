/**
 * 拿客 www.coderknock.com
 * 微信公众号 coderknock
 * 作者：三产
 */

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static spark.Spark.*;

public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        port(9090);
        //EchoWebSocket不能是内部类
        webSocket("/echo", EchoWebSocket.class);
        init();
//        //获取redis连接池
//        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
//        try (Jedis jedis = pool.getResource()) {
//            /// ... do stuff here ... for example
//            jedis.set("foo", "bar");
//            String foobar = jedis.get("foo");
//            jedis.zadd("sose", 0, "car");
//            jedis.zadd("sose", 0, "bike");
//            Set<String> sose = jedis.zrange("sose", 0, -1);
//            System.out.println("");
//        }
//        /// ... when closing your application:
//        pool.destroy();
        // matches "GET /hello/foo" and "GET /hello/bar"
        // request.params(":name") is 'foo' or 'bar'
        get("/hello/:name", (request, response) -> {
            return Tmpl.render("hello.html", request.params());
        });
        // matches "GET /say/hello/to/world"
        // request.splat()[0] is 'hello' and request.splat()[1] 'world'
        get("/say/*/to/*", (request, response) -> {
            response.type("application/json");
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("1", request.splat()[0]);
            map.put("2", request.splat()[1]);
            logger.debug("$$$$$$$$$$$$$$$$$" + JSON.toJSON(map).toString());
            return JSON.toJSON(map);
        });
        get("/home", (request, response) -> {
            return Tmpl.render("index.html");
        });
        int i = 0;
        while (true) {
            try {
                Thread.currentThread().sleep(1000);
                i++;
                logger.debug("--->" + i);
                if (i % 5 == 1) {
                    EchoWebSocket.send(i);
                    logger.debug("--->第" + i + "次发送");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}