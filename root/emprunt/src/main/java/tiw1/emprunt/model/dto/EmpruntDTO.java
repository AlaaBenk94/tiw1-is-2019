package tiw1.emprunt.model.dto;

import java.util.Date;

public class EmpruntDTO {

    private Date date;
    private Long idAbonne;
    private Long idTrottinette;

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
