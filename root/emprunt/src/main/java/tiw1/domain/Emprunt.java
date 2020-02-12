package tiw1.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "empruntById", query = "select e from Emprunt e where e.id = :id"),
        @NamedQuery(name = "allEmprunts", query = "select e from Emprunt e"),
        @NamedQuery(name = "empruntByDate", query = "select e from Emprunt e where e.date = :date")

})
public class Emprunt {
    @Id
    @GeneratedValue
    private Long id;

    private Timestamp date;

    private Long idAbonne, idTrottinette;

    private String owner;

    private String activationNumber;

    private Boolean activated;

    public Emprunt() {
    }

    public Emprunt(Timestamp date, Long idAbonne, Long idTrottinette) {
        this.date = date;
        this.idAbonne = idAbonne;
        this.idTrottinette = idTrottinette;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Boolean getActivated() {
        return activated;
    }

    public Long getIdAbonne() {
        return idAbonne;
    }

    public void setIdAbonne(Long idAbonne) {
        this.idAbonne = idAbonne;
    }

    public Long getIdTrottinette() {
        return idTrottinette;
    }

    public void setIdTrottinette(Long idTrottinette) {
        this.idTrottinette = idTrottinette;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getActivationNumber() {
        return activationNumber;
    }

    public void setActivationNumber(String activationNumber) {
        this.activationNumber = activationNumber;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprunt emprunt = (Emprunt) o;
        return Objects.equals(id, emprunt.id) &&
                Objects.equals(date, emprunt.date) &&
                Objects.equals(idAbonne, emprunt.idAbonne) &&
                Objects.equals(idTrottinette, emprunt.idTrottinette) &&
                Objects.equals(owner, emprunt.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, idAbonne, idTrottinette, owner);
    }
}
