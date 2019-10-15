package tiw1.emprunt.contexte;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiw1.emprunt.controleur.AbonneResource;
import tiw1.emprunt.persistence.AbonneDAO;

import java.util.Map;

public class ContextImpl implements Context {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    Map<String, Object> references;

    public ContextImpl(Map<String, Object> references) {
        this.references = references;
    }

    @Override
    public void setReference(String name, Object resource) {
        this.references.put(name, resource);
    }

    @Override
    public Object getReference(String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String caller = stackTraceElements[2].getClassName();

        if(caller.contains("Resource"))
            return references.get(name);

        return references.get(name);
    }
}
