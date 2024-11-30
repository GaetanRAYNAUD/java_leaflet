package fr.graynaud.maps.javaleaflet.geojson;

/**
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLGeoJsonObject {

    private final String geoJsonContent;

    private int id;

    public JLGeoJsonObject(int id, String geoJsonContent) {
        this.id = id;
        this.geoJsonContent = geoJsonContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGeoJsonContent() {
        return geoJsonContent;
    }
}
