package tiw1.emprunt.interceptor;

import java.util.List;
import java.util.Map;

public class InterceptorChainImpl extends InterceptorChain {

    public InterceptorChainImpl() {}

    public InterceptorChainImpl(List<Interceptor> interceptorsList) {
        super(interceptorsList);
    }

    @Override
    public Map<String, Object> input(String method, Map<String, Object> params) {
        Map<String, Object> result = params;

        for(Interceptor i: interceptorsList) {
            result = i.input(method, result);
        }

        return result;
    }

    @Override
    public Object output(String method, Map<String, Object> params, Object result) {

        for(Interceptor i: interceptorsList) {
            result = i.output(method, params, result);
        }

        return result;
    }
}
