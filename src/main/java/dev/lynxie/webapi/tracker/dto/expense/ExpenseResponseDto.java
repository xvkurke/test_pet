package dev.lynxie.webapi.tracker.dto.expense;

import dev.lynxie.webapi.tracker.dto.ResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ExpenseResponseDto extends ResponseDto {
    private Long id;
    private Double amount;
    private String currency;
    private String description;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createDate;
}
