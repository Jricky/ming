package ming.framework.core.service.cache;

/**
 * Created by Jrick on 2018/1/11.
 */
public interface CacheProvider {

    String getType();

    void init();

    void forceInit();

    Object get();
}
