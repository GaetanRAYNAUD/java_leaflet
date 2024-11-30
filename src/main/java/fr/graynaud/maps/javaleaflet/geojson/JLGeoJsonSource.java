package fr.graynaud.maps.javaleaflet.geojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.graynaud.maps.javaleaflet.exception.JLGeoJsonParserException;

/**
 * The base abstract class for a GeoJSON data source. Implementations of this class are expected to provide functionality for loading and accessing GeoJSON
 * objects.
 *
 * @param <S> source type
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public abstract class JLGeoJsonSource<S> {

    /**
     * Gson object for JSON serialization and deserialization.
     */
    protected final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * The GeoJSON object loaded from this source.
     */
    protected JLGeoJsonObject geoJsonObject;

    /**
     * Initializes a new instance of {@code JLGeoJsonSource}.
     */
    protected JLGeoJsonSource() {
    }

    /**
     * Loads the GeoJSON data from the source and parses it into a GeoJSON object.
     *
     * @throws JLGeoJsonParserException If an error occurs during data loading or parsing.
     */
    public abstract String load(S source) throws JLGeoJsonParserException;

    protected void validateJson(String jsonInString) throws JsonProcessingException {
        this.objectMapper.readValue(jsonInString, Object.class);
    }

}
