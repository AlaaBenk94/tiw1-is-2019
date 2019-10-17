package tiw1.emprunt.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LoggerInterceptor implements Interceptor {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<String, Object> input(String method, Map<String, Object> params) {
        LOG.info(" [ REQUEST ] : " + method + " :: " + params.toString());
        return params;
    }

    @Override
    public Object output(String method, Map<String, Object> params, Object result) {
        return result;
    }
}
