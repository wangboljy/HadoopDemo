package hmr.tutorial.customization.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * No interface. just a string based demo.
 */
public class JsonCodex<T> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public T decode(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    public String encode(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
