package dev.lynxie.webapi.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ListResponseDto<T> {
    @JsonProperty(value = "list")
    private List<T> list;

    @JsonProperty(value = "totalRows")
    private Long totalRows;

    public static <T> ListResponseDto<T> createEmpty() {
        return new ListResponseDto<T>()
                .setList(Collections.emptyList())
                .setTotalRows(0L);
    }
}