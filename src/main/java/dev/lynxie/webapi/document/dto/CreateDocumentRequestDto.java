package dev.lynxie.webapi.document.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateDocumentRequestDto {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "userId")
    private Long userId;

    @JsonProperty(value = "parentDocumentId")
    private Long parentDocumentId;
}