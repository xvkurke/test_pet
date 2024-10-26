package dev.lynxie.webapi.tracker.dto.expense;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateExpenseRequestDto {
    private Double amount;
    private String currency;
    private String description;
    private Long categoryId;
}
