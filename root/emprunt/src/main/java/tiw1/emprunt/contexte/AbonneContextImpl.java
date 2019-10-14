package tiw1.emprunt.contexte;

import tiw1.emprunt.persistence.AbonneDAO;

public class AbonneContextImpl implements AbonneContext {

    AbonneDAO abonneDAO;

    @Override
    public void setContext(AbonneDAO abonneDAO) {
        this.abonneDAO = abonneDAO;
    }

    @Override
    public AbonneDAO getContext() {
        return this.abonneDAO;
    }
}
