package tiw1.emprunt.contexte;

import tiw1.emprunt.persistence.AbonneDAO;

public interface AbonneContext {
    void setAbonneDAO(AbonneDAO abonneDAO);
    AbonneDAO getAbonneDAO();
}
