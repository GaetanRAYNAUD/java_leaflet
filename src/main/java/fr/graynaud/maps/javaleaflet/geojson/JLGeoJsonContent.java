package fr.graynaud.maps.javaleaflet.geojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.graynaud.maps.javaleaflet.exception.JLGeoJsonParserException;

/**
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLGeoJsonContent extends JLGeoJsonSource<String> {

    @Override
    public String load(String content) throws JLGeoJsonParserException {
        if (content == null || content.isEmpty()) {
            throw new JLGeoJsonParserException("json is empty!");
        }

        try {
            validateJson(content);
            return content;
        } catch (JsonProcessingException e) {
            throw new JLGeoJsonParserException(e.getMessage());
        }
    }
}
