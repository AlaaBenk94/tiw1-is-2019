package tiw1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

/**
 *
 */
@ApiModel(description = "Intervention information of trottinette")
public class InterventionDto {

    @ApiModelProperty(notes = "Id of intervention")
    private Long id;

    @ApiModelProperty(notes = "Date of intervention")
    private Date date;

    @ApiModelProperty(notes = "Short description of intervention")
    private String description;

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterventionDto that = (InterventionDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, description);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        InterventionDto interventionDto = new InterventionDto();

        public Builder withId(Long id) {
            interventionDto.id = id;
            return this;
        }

        public Builder withDate(Date date) {
            interventionDto.date = date;
            return this;
        }

        public Builder withDescription(String description) {
            interventionDto.description = description;
            return this;
        }

        public InterventionDto build() {
            return interventionDto;
        }
    }
}
