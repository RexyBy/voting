package by.rexy.voting.util;

import by.rexy.voting.model.Restaurant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class JsonSerializers {
    public static class RestaurantSerializer extends JsonSerializer<Restaurant> {
        @Override
        public void serialize(Restaurant restaurant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            String jsonValue = restaurant.getId() == null ? null : restaurant.getId().toString();
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", jsonValue);
            jsonGenerator.writeEndObject();
        }
    }
}