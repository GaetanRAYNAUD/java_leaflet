package fr.graynaud.maps.javaleaflet.geojson;

import fr.graynaud.maps.javaleaflet.exception.JLGeoJsonParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLGeoJsonFile extends JLGeoJsonSource<File> {

    @Override
    public String load(File file) throws JLGeoJsonParserException {
        try {
            String fileContent = Files.readString(file.toPath());
            validateJson(fileContent);
            return fileContent;
        } catch (IOException e) {
            throw new JLGeoJsonParserException(e.getMessage());
        }
    }
}
