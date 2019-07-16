package ru.sbrf.commissions.calculatorservice.common.call.sbbol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sbrf.commissions.calculatorservice.common.AbstractDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Класс выступает как пример. На этапе расширения функционала будет уделен
 * TODO: Удалить при расширении функционала в случае добавления реальной DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RequestFromSbbolDto extends AbstractDto {

    private static final String CLASS_HEADER_NAME = "RequestFromSbbolDto{";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(AbstractDto.DATE_TIME_FORMAT);

    private String name;

    private String model;

    @JsonCreator
    public RequestFromSbbolDto(
            @JsonProperty("id") final String id,
            @JsonProperty("createdDate") final LocalDateTime createdDate,
            @JsonProperty("updatedDate") final LocalDateTime updatedDate,
            @JsonProperty("name") final String name,
            @JsonProperty("model") final String model) {
        setId(UUID.fromString(id));
        setCreatedDate(createdDate);
        setUpdatedDate(updatedDate);
        setName(name);
        setModel(model);
    }

    @Override
    public String toString() {
        return CLASS_HEADER_NAME +
                "id='" + getId() + '\'' +
                ", createdDate='" + getCreatedDate() + '\'' +
                ", updatedDate='" + getUpdatedDate() + '\'' +
                ", name='" + getName() + '\'' +
                ", model=" + getModel() +
                '}';
    }
}