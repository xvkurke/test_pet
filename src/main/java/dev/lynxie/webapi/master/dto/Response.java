package dev.lynxie.webapi.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Response {

    @JsonProperty(value = "code")
    private Integer code;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "data")
    private Object data;
}
