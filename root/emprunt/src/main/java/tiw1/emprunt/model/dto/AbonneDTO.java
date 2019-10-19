package tiw1.emprunt.model.dto;

import tiw1.emprunt.model.Abonne;
import tiw1.emprunt.model.Emprunt;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class AbonneDTO implements Serializable {

    private Long id;
    private Date dateDebut;
    private Date dateFin;
    private String name;

    public AbonneDTO() {}

    public AbonneDTO(Abonne abonne) {
        this.id = abonne.getId();
        this.dateDebut = abonne.getDateDebut();
        this.dateFin = abonne.getDateFin();
        this.name = abonne.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
