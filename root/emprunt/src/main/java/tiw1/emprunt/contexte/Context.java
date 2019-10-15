package tiw1.emprunt.contexte;

public interface Context {
    void setReference(String name, Object resource);
    Object getReference(String name);
}
