package ming.framework.util;

import org.slf4j.Logger;

/**
 * 异常处理类
 * Created by Jrick on 2018/1/1.
 */
public class ExceptionUtil {

    public static void logAndThrow(Logger log, String message, Throwable e) {
        log.info(message, e);
        throw new RuntimeException(message, e);
    }
}