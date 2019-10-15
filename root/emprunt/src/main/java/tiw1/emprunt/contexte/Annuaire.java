package tiw1.emprunt.contexte;

public interface Annuaire {
    void rebind(String name, Object resource);
    Object lookup(String name);
}
