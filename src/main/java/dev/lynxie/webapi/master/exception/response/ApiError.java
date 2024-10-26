package dev.lynxie.webapi.master.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError implements Serializable {
    
    @JsonProperty(value = "code")
    private Integer code;

    @JsonProperty(value = "message")
    private String message;
}
