package tiw1.emprunt.interceptor;

import java.util.List;

public abstract class InterceptorChain implements Interceptor{
    protected List<Interceptor> interceptorsList;

    public InterceptorChain() {}

    public InterceptorChain(List<Interceptor> interceptorsList) {
        this.interceptorsList = interceptorsList;
    }

    public void setInterceptorsList(List<Interceptor> interceptorsList) {
        this.interceptorsList = interceptorsList;
    }

    public InterceptorChain addInterceptor(Interceptor interceptor) {
        interceptorsList.add(interceptor);
        return this;
    }

}
