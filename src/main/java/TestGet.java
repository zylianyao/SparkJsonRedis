/**
 * 拿客 www.coderknock.com
 * 微信公众号 coderknock
 * 作者：三产
 */

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class TestGet {
    private static int thread_num = 500;
    private static int client_num = 500;

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(thread_num);
        for (int index = 0; index < client_num; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        semp.acquire();
                        System.out.println("------------------|" + NO + "|------------------");
                        HttpResponse response = HttpRequest
                                .get("http://localhost:9090/say/Hello-" + NO + "/to/World" + NO
                                )
                                .acceptEncoding("gzip")
                                .send();

                        System.out.println(response.unzip());
                        System.out.println("------------------&" + NO + "&------------------");
                        //业务逻辑
                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        exec.shutdown();
    }
}
