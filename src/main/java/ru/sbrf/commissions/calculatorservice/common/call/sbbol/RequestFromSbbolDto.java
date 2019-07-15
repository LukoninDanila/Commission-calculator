package ru.sbrf.commissions.calculatorservice.common.call.sbbol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ru.sbrf.commissions.calculatorservice.common.AbstractDto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс выступает как пример. На этапе расширения функционала будет уделен
 * TODO: Удалить при расширении функционала в случае добавления реальной DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "createdDate",
        "updatedDate",
        "name",
        "model"
})
@JsonRootName(value = "requestFromSbbol")
public class RequestFromSbbolDto extends AbstractDto {

    private static final String CLASS_HEADER_NAME = "RequestFromSbbolDto{";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("name")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("model")
    private String model;

    public RequestFromSbbolDto() {
    }

    @JsonCreator
    public RequestFromSbbolDto(final UUID id,
                               final LocalDateTime createdDate,
                               final LocalDateTime updatedDate,
                               final String name,
                               final String model) {
        setId(id);
        setCreatedDate(createdDate);
        setUpdatedDate(updatedDate);
        setName(name);
        setModel(model);
    }

    @Override
    public String createHeader() {
        return CLASS_HEADER_NAME;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(createHeader())
                .append(fieldsToString())
                .append("name='")
                .append(getName()).append('\'')
                .append(", model='").append(getModel()).append('\'')
                .append(createEnding())
                .toString();
    }
}