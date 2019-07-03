package ru.sbrf.commissions.calculatorservice.common.call.sbbol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sbrf.commissions.calculatorservice.common.AbstractDto;

/**
 * Класс выступает как пример. На этапе расширения функционала будет уделен
 * TODO: Удалить при расширении функционала в случае добавления реальной DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class RequestFromSbbolDto extends AbstractDto {
    private String name;
    private String model;
}