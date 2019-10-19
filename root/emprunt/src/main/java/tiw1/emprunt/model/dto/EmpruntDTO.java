package tiw1.emprunt.model.dto;

import tiw1.emprunt.model.Emprunt;

import java.util.Date;

public class EmpruntDTO {

    private Date date;
    private Long idAbonne;
    private Long idTrottinette;

    public EmpruntDTO() {}

    public EmpruntDTO(Emprunt emprunt) {
        this.date = emprunt.getDate();
        this.idAbonne = emprunt.getIdAbonne();
        this.idTrottinette = emprunt.getIdTrottinette();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
