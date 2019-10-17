package tiw1.emprunt.interceptor;

import java.util.List;
import java.util.Map;

public class InterceptorChainImpl extends InterceptorChain {

    public InterceptorChainImpl() {}

    public InterceptorChainImpl(List<Interceptor> interceptorsList) {
        super(interceptorsList);
    }

}
