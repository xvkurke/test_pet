package dev.lynxie.webapi.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {
    
    @Override
    public void serialize(BigDecimal value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(isIntegerValue(value)) {
            jsonGenerator.writeNumber(value.setScale(1, BigDecimal.ROUND_HALF_UP));
        } else {
            jsonGenerator.writeNumber(value);
        }
    }
    
    private boolean isIntegerValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }
}
