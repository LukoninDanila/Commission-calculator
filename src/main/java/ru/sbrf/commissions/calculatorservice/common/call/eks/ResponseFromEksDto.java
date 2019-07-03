package ru.sbrf.commissions.calculatorservice.common.call.eks;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.sbrf.commissions.calculatorservice.common.AbstractDto;

/**
 * Класс выступает как пример. На этапе расширения функционала будет уделен
 * TODO: Удалить при расширении функционала в случае добавления реальной DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseFromEksDto extends AbstractDto {
    private String name;
    private String model;
}