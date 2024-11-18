package dev.lynxie.webapi.document.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateDocumentRequestDto {

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "userId")
    private Long userId;

    @JsonProperty(value = "isArchived")
    private Boolean isArchived;

    @JsonProperty(value = "parentDocumentId")
    private Long parentDocumentId;

    @JsonProperty(value = "content")
    private String content;

    @JsonProperty(value = "coverImage")
    private String coverImage;

    @JsonProperty(value = "icon")
    private String icon;

    @JsonProperty(value = "isPublished")
    private Boolean isPublished;
}