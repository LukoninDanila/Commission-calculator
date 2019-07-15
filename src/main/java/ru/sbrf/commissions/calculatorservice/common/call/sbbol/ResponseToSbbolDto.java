package ru.sbrf.commissions.calculatorservice.common.call.sbbol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sbrf.commissions.calculatorservice.common.AbstractDto;

/**
 * Класс выступает как пример. На этапе расширения функционала будет уделен
 * TODO: Удалить при расширении функционала в случае добавления реальной DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseToSbbolDto extends AbstractDto {
    private String name;
    private String model;

    @Override
    public String createHeader() {
        return null;
    }

    @Override
    public String toString() {
        return /*new StringBuilder()
                .append(createHeader())
                .append(fieldsToString())
                .append("name='")
                .append(getName()).append('\'')
                .append(", model='").append(getModel()).append('\'')
                .append(createEnding())
                .toString();*/ null;
    }
}