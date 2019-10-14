package tiw1.emprunt.contexte;

import tiw1.emprunt.persistence.AbonneDAO;

public interface AbonneContext {
    void setContext(AbonneDAO abonneDAO);
    AbonneDAO getContext();
}
