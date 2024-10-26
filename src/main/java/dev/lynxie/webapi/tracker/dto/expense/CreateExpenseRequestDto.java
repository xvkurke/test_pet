package dev.lynxie.webapi.tracker.dto.expense;

import dev.lynxie.webapi.tracker.dto.RequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateExpenseRequestDto extends RequestDto {
    private Double amount;
    private String currency;
    private String description;
    private Long categoryId;
    private LocalDateTime createDate;
}
