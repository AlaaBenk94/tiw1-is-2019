package tiw1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Objects;

/**
 * Trottinette read-only à partir de la liste de trottinettes
 */
@ApiModel(description = "The trottinette object")
public class TrottinetteDto {

    @ApiModelProperty(notes = "Id of trottinette")
    private Long id;

    @ApiModelProperty(notes = "Availability of trottinette")
    private Boolean disponible = true;

    @ApiModelProperty(notes = "List of interventions of trottinette")
    private List<InterventionDto> interventions;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public List<InterventionDto> getInterventions() {
        return interventions;
    }

    public void setInterventions(List<InterventionDto> interventionDtos) {
        this.interventions = interventionDtos;
    }

    public Long getId() {
        return id;
    }

    public Boolean isDisponible() {
        return disponible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrottinetteDto that = (TrottinetteDto) o;
        return id == that.id &&
                disponible == that.disponible &&
                Objects.equals(interventions, that.interventions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, disponible, interventions);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        TrottinetteDto trottinetteDto = new TrottinetteDto();

        public Builder withId(long id) {
            trottinetteDto.id = id;
            return this;
        }

        public Builder withDisponible(boolean disponible) {
            trottinetteDto.disponible = disponible;
            return this;
        }

        public Builder withIntervention(List<InterventionDto> intervention) {
            trottinetteDto.interventions = intervention;
            return this;
        }

        public TrottinetteDto build() {
            return trottinetteDto;
        }

    }
}

