package ru.sbrf.commissions.calculatorservice.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public abstract class AbstractDto implements Serializable {

    private static final String END_CLASS_LINE = "}";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("id")
    @NotNull
    private UUID id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonProperty("createdDate")
    @NotNull
    private LocalDateTime createdDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonProperty("updatedDate")
    @NotNull
    private LocalDateTime updatedDate;

    public final String fieldsToString() {
        return "id='" + getId() + '\'' +
                ", createdDate='" + getCreatedDate() + '\'' +
                ", updatedDate='" + getUpdatedDate() + '\'';
    }

    abstract public String createHeader();

    public final String createEnding() {
        return END_CLASS_LINE;
    }
}