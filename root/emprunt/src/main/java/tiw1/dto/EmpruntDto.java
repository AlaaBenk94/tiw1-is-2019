package tiw1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 *
 */
@ApiModel(description = "Emprunt general information")
public class EmpruntDto {

    @ApiModelProperty(notes = "Id of Emprunt")
    private Long id;

    @ApiModelProperty(notes = "Date of emprunt")
    private Timestamp date;

    @ApiModelProperty(notes = "Cost of emprunt")
    private Double cost;

    @ApiModelProperty(notes = "Id of abonne", required = true)
    private Long idAbonne;

    @ApiModelProperty(notes = "Id of trottinette", required = true)
    private Long idTrottinette;

    @ApiModelProperty(notes = "auto generated activation number")
    private String activationNumber;

    @ApiModelProperty(notes = "whether the emprunt is activated or not ")
    private Boolean activated;

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Long getIdAbonne() {
        return idAbonne;
    }

    public Long getIdTrottinette() {
        return idTrottinette;
    }

    public String getActivationNumber() {
        return activationNumber;
    }

    public Boolean getActivated() {
        return activated;
    }

    public Double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpruntDto that = (EmpruntDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(idAbonne, that.idAbonne) &&
                Objects.equals(idTrottinette, that.idTrottinette);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, idAbonne, idTrottinette);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        EmpruntDto empruntDto = new EmpruntDto();

        public Builder withId(Long id) {
            empruntDto.id = id;
            return this;
        }

        public Builder withDate(Timestamp date) {
            empruntDto.date = date;
            return this;
        }

        public Builder withCost(Double cost) {
            empruntDto.cost = cost;
            return this;
        }

        public Builder withIdAbonne(Long idAbonne) {
            empruntDto.idAbonne = idAbonne;
            return this;
        }

        public Builder withIdTrottinette(Long idTrottinette) {
            empruntDto.idTrottinette = idTrottinette;
            return this;
        }

        public Builder withActivationNumber(String activationNumber) {
            empruntDto.activationNumber = activationNumber;
            return this;
        }

        public Builder withActivated(Boolean activated) {
            empruntDto.activated = activated;
            return this;
        }

        public EmpruntDto build() {
            return empruntDto;
        }
    }
}
