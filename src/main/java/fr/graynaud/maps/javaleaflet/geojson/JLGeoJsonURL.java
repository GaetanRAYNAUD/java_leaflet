package fr.graynaud.maps.javaleaflet.geojson;

import fr.graynaud.maps.javaleaflet.exception.JLGeoJsonParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLGeoJsonURL extends JLGeoJsonSource<String> {

    @Override
    public String load(String url) throws JLGeoJsonParserException {
        try {
            URL jsonUrl = URI.create(url).toURL();
            // Open a connection to the URL and create a BufferedReader to read the content
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(jsonUrl.openStream()))) {
                StringBuilder content = new StringBuilder();
                String line;
                // Read the JSON data line by line
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

                String s = content.toString();
                validateJson(s);
                return s;
            }
        } catch (IOException e) {
            throw new JLGeoJsonParserException(e.getMessage());
        }

    }
}
