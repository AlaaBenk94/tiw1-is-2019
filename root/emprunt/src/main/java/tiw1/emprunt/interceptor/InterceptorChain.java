package tiw1.emprunt.interceptor;

import tiw1.emprunt.controleur.Processable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class InterceptorChain {
    protected List<Interceptor> interceptorsList;
    protected Processable target;

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

    public Object execute(String method, Map<String, Object> params) throws IOException {
        Map<String, Object> alteredInput = params;
        for(Interceptor i: interceptorsList) {
            alteredInput = i.input(method, alteredInput);
        }

        Object alteredOutput = target.process(method, alteredInput);

        for(Interceptor i: interceptorsList) {
            alteredOutput = i.output(method, alteredInput, alteredOutput);
        }

        return alteredOutput;
    }

    public InterceptorChain setTarget(Processable target) {
        this.target = target;
        return this;
    }
}
