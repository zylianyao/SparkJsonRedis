/**
 * 拿客 www.coderknock.com
 * 微信公众号 coderknock
 * 作者：三产
 */

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

import static spark.Spark.get;

public class Test {

    public static void main(String[] args) {
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
            return "Hello: " + request.params(":name");
        });
        // matches "GET /say/hello/to/world"
        // request.splat()[0] is 'hello' and request.splat()[1] 'world'
        get("/say/*/to/*", (request, response) -> {
            response.type("application/json");
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("1", request.splat()[0]);
            map.put("2", request.splat()[1]);
            return JSON.toJSON(map);
        });
    }

}
