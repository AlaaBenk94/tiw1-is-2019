package tiw1.emprunt.contexte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AnnuaireImpl extends Observable implements Annuaire {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    Map<String, Object> references;

    public AnnuaireImpl() {}

    public AnnuaireImpl(Map<String, Object> references) {
        this.references = references;
    }

    public void setReferences(Map<String, Object> references) {
        this.references = references;
    }

    @Override
    public void rebind(String name, Object resource) {
        this.references.put(name.toLowerCase(), resource);
        notifyObservers();
    }

    @Override
    public Object lookup(String name) {
//        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//        String caller = stackTraceElements[2].getClassName();
//
//        if(caller.contains("Resource"))
//            return references.get(name);

        return references.get(name.toLowerCase());
    }



}
