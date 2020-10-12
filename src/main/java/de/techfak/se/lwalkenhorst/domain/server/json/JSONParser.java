package de.techfak.se.lwalkenhorst.domain.server.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONParser {

    private final ObjectMapper objectMapper;

    public JSONParser() {
        this.objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    public String toJSON(Object object) throws SerialisationException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SerialisationException("Serialisation failed");
        }
    }

    public <T> T parseJSON(String json, Class<T> clazz) throws SerialisationException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new SerialisationException("Deserialisation failed");
        }
    }
}
