package vn.cmcati.eid.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ApiRequestsUtils {

    public JsonNode mergeJsonList(List<JsonNode> responseDataList) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode combinedNode = objectMapper.createObjectNode();

        for (JsonNode responseData : responseDataList) {
            responseData.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode newValue = entry.getValue();

                if ("id".equals(key)) {
                    return;
                }

                if (combinedNode.has(key)) {
                    JsonNode existingValue = combinedNode.get(key);

                    if (existingValue == null ||
                            existingValue.isNull() ||
                            existingValue.isTextual() && existingValue.asText().isEmpty() ||
                            existingValue.isNumber() && existingValue.asInt() == 0 ||
                            existingValue.isDouble() && existingValue.asDouble() == 0.0) {
                        combinedNode.set(key, newValue);
                    } else {
                        ArrayNode arrayNode = objectMapper.createArrayNode();
                        arrayNode.add(existingValue);
                        arrayNode.add(newValue);
                        combinedNode.set(key, arrayNode);
                    }
                } else {
                    combinedNode.set(key, newValue);
                }
            });
        }

        return combinedNode;
    }
}
