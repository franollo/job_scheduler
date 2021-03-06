package main.java.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import main.java.model.Order;
import main.java.model.OrderProduct;

import java.io.IOException;

/**
 * Produces json from Order object
 * @author Marcin Frankowski
 */
public class OrderSerializer extends StdSerializer<Order> {

    public OrderSerializer() {
        this(null);
    }

    public OrderSerializer(Class<Order> t) {
        super(t);
    }

    @Override
    public void serialize(Order order, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("id", order.getId());
            jsonGenerator.writeObjectField("name", order.getName());
            jsonGenerator.writeObjectField("description", order.getDescription());
            jsonGenerator.writeObjectField("dueDate", order.getDueDate());
            jsonGenerator.writeObjectField("createdOn", order.getCreatedOn());
            jsonGenerator.writeObjectField("editedOn", order.getEditedOn());
            jsonGenerator.writeArrayFieldStart("products");
            for(OrderProduct orderProduct : order.getOrderProducts()) {
                jsonGenerator.writeStartObject();
                    jsonGenerator.writeObjectField("id", orderProduct.getProduct().getId());
                    jsonGenerator.writeObjectField("name", orderProduct.getProduct().getName());
                    jsonGenerator.writeObjectField("description", orderProduct.getProduct().getDescription());
                    jsonGenerator.writeObjectField("attribute1", orderProduct.getProduct().getAttribute1());
                    jsonGenerator.writeObjectField("attribute2", orderProduct.getProduct().getAttribute2());
                    jsonGenerator.writeObjectField("attribute3", orderProduct.getProduct().getAttribute3());
                    jsonGenerator.writeObjectField("amount", orderProduct.getAmount());
                    jsonGenerator.writeObjectField("createdOn", orderProduct.getProduct().getCreatedOn());
                    jsonGenerator.writeObjectField("editedOn", orderProduct.getProduct().getEditedOn());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
