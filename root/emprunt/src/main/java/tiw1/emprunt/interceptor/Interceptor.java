package tiw1.emprunt.interceptor;

import java.util.Map;

public interface Interceptor {
    Map<String, Object> input(String method, Map<String, Object> params);
    Object output(String method, Map<String, Object> params, Object result);
}
