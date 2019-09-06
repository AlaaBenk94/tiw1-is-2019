package tiw1.maintenance.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Entity
@NamedQueries({
        @NamedQuery(name = "allTrottinettes", query = "SELECT t FROM Trottinette t"),
        @NamedQuery(name = "trottinetteById", query = "SELECT t FROM Trottinette t where t.id=:id")
})
public class Trottinette {
    @Id
    @GeneratedValue
    private long id;

    private boolean disponible = true;

    @OneToMany
    private Collection<Intervention> interventions = new ArrayList<>();

    public Trottinette() {
    }

    public Trottinette(long id) {
        this.id = id;
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

    public Collection<Intervention> getInterventions() {
        return Collections.unmodifiableCollection(interventions);
    }

    public void ajouterIntervention(Intervention intervention) {
        interventions.add(intervention);
    }

    public void supprimerIntervention(Intervention intervention) {
        interventions.remove(intervention);
    }
}
