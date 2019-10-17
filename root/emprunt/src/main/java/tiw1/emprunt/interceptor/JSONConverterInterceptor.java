package tiw1.emprunt.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.picocontainer.Startable;

import java.util.Map;

public class JSONConverterInterceptor implements Interceptor {

    private ObjectMapper mapper;

    public JSONConverterInterceptor() {}

    public JSONConverterInterceptor(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Map<String, Object> input(String method, Map<String, Object> params) {
        return params;
    }

    @Override
    public Object output(String method, Map<String, Object> params, Object result) {
        try {
            this.mapper = new ObjectMapper();
            return this.mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
