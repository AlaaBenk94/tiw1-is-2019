package tiw1.emprunt.controleur;

import org.picocontainer.Startable;
import tiw1.emprunt.model.dto.Response;

import java.io.IOException;
import java.util.Map;

public abstract class ControlersStartable implements Startable,ProcessResp {
    private static final String ADD = "ADD";
    private static final String REMOVE = "REMOVE";
    private static final String GET = "GET";
    private static final String UPDATE ="UPDATE";

    protected static final String ID = "ID";
    protected static final String DATE = "DATE";
    protected static final String EMPRUNT = "EMPRUNT";


    @Override
    public Response process(String cmd, Map<String, Object> params) throws IOException{
        if(cmd.toUpperCase().equals(ControlersStartable.GET))
            return get(params);
        if(cmd.toUpperCase().equals(ControlersStartable.ADD))
            return add(params);
        if(cmd.toUpperCase().equals(ControlersStartable.REMOVE))
            return remove(params);
        if(cmd.toUpperCase().equals(ControlersStartable.UPDATE))
            return update(params);

        return Response.create(Response.UNKNOWN_CMD, cmd + " command is UNKNOWN");
    }

    public abstract Response get(Map<String, Object> params);
    public abstract Response remove(Map<String, Object> params);
    public abstract Response add(Map<String, Object> params);
    public abstract Response update(Map<String, Object> params);

}
