package tiw1.emprunt.model.dto;

import tiw1.emprunt.model.Intervention;
import tiw1.emprunt.model.Trottinette;

import java.io.Serializable;
import java.util.List;

public class TrottinetteDTO implements Serializable {
    private long id;
    private boolean disponible = true;
    private List<Intervention> interventions;

    public TrottinetteDTO() {}

    public TrottinetteDTO(Trottinette trottinette) {
        this.id = trottinette.getId();
        this.disponible = trottinette.isDisponible();
        this.interventions = trottinette.getInterventions();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(List<Intervention> interventions) {
        this.interventions = interventions;
    }
}
