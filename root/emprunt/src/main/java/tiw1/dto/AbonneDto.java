package tiw1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

/**
 *
 */
@ApiModel(description = "Abonne information")
public class AbonneDto {

    @ApiModelProperty(notes = "Id of abonne")
    private Long id;

    @ApiModelProperty(notes = "Subscription start date")
    private Date dateDebut;

    @ApiModelProperty(notes = "Subscription end date")
    private Date dateFin;

    @ApiModelProperty(notes = "Name of abonne")
    private String name;

    public Long getId() {
        return id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbonneDto abonneDto = (AbonneDto) o;
        return Objects.equals(id, abonneDto.id) &&
                Objects.equals(dateDebut, abonneDto.dateDebut) &&
                Objects.equals(dateFin, abonneDto.dateFin) &&
                Objects.equals(name, abonneDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateDebut, dateFin, name);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AbonneDto abonneDto = new AbonneDto();

        public Builder withId(Long id) {
            abonneDto.id = id;
            return this;
        }

        public Builder withDateDebut(Date dateDebut) {
            abonneDto.dateDebut = dateDebut;
            return this;
        }

        public Builder withDateFin(Date dateFin) {
            abonneDto.dateFin = dateFin;
            return this;
        }

        public Builder withName(String name) {
            abonneDto.name = name;
            return this;
        }

        public AbonneDto build() {
            return abonneDto;
        }
    }

}
