package tiw1.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Trottinette read-only à partir de la liste de trottinettes
 */
public class Trottinette {
    private long id;

    private boolean disponible = true;

    private List<Intervention> interventions;

    public Trottinette() {
    }

    public Trottinette(long id) {
        this.id = id;
    }

    public Trottinette(long id, boolean disponible) {
        this.id = id;
        this.disponible = disponible;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Intervention> getInterventions() {
        return interventions != null ? interventions : Collections.emptyList();
    }

    public void setInterventions(List<Intervention> interventions) {
        this.interventions = interventions;
    }

    public long getId() {
        return id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trottinette that = (Trottinette) o;
        return id == that.id &&
                disponible == that.disponible &&
                Objects.equals(interventions, that.interventions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, disponible, interventions);
    }
}

