package dev.lynxie.webapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JsonUtils {

    private final ObjectMapper jsonMapper;

    public <T> T parse(JsonNode node, Class<T> clazz) {
        try {
            return jsonMapper.treeToValue(node, clazz);
        } catch (JsonProcessingException ignored) {
            throw new ValidationException(String.format("Text: '%s', can not parse to JsonNode", node.toPrettyString()));
        }
    }

    public String convertToString(Object dto) {
        if (dto == null) {
            return null;
        }
        try {
            return jsonMapper.writeValueAsString(dto);
        } catch (JsonProcessingException ignored) {
            throw new ValidationException(String.format("Error on convert dto to json %s", dto));
        }
    }

    public List<String> mapToListString(String json) {
        try {
            return jsonMapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new ValidationException(String.format("Error on List parse - %s", Arrays.toString(e.getStackTrace())));
        }
    }

    public String parseJsonArrayToString(List<String> list) {
        try {
            return jsonMapper.writeValueAsString(list);
        } catch (JsonProcessingException ignored) {
            throw new ValidationException(String.format("Text: '%s', can not parse to String", list.toString()));
        }
    }

    public <T> Map<String, Object> convertDtoToMap(T dto) {
        return jsonMapper.convertValue(dto, new TypeReference<>() {});
    }

    public <T> String convertDtoToString(T dto) {
        try {
            return jsonMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new ValidationException(String.format("Error during convert dto to string -> %s", e.getMessage()));
        }
    }
}
