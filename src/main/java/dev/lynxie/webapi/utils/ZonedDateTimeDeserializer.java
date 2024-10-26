package dev.lynxie.webapi.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, 
                                     DeserializationContext deserializationContext) throws IOException {

        LocalDateTime localDateTime = LocalDateTime.parse(
                jsonParser.getText(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        return localDateTime.atZone(ZoneId.systemDefault());
    }
}
