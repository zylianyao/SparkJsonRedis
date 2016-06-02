import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.WebAppResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 拿客 www.coderknock.com
 * 微信公众号 coderknock
 * 作者：三产
 */
public class Tmpl {
    private static GroupTemplate gt;
    private static Logger logger = LoggerFactory.getLogger(Tmpl.class);

    static {
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            WebAppResourceLoader resourceLoader = new WebAppResourceLoader();
            gt = new GroupTemplate(resourceLoader, cfg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String render(String tmplPath) {
        Template t = gt.getTemplate(tmplPath);
        return t.render();
    }

    public static String render(String tmplPath, Map<String, String> param) {
        Template t = gt.getTemplate(tmplPath);
        Map<String, String> convertMap = new HashMap<>();
        try {
            param.forEach((x, y) -> {
                if (x.startsWith(":")) {
                    convertMap.put(x.substring(1), y);
                }
            });
        } catch (Exception e) {
            logger.error("转换失败", e);
        }
        t.binding(convertMap);
        return t.render();
    }
}
