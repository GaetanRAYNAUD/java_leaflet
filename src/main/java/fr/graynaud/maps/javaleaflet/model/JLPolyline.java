package fr.graynaud.maps.javaleaflet.model;

/**
 * A class for drawing polyline overlays on the map.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLPolyline extends JLObject<JLPolyline> {

    /**
     * id of JLPolyline! this is an internal id for JLMap Application and not related to Leaflet!
     */
    private int id;
    /**
     * theming options for JLPolyline. all options are not available!
     */
    private JLOptions options;
    /**
     * The array of {@link JLLatLng} points of JLPolyline
     */
    private JLLatLng[] vertices;

    public JLPolyline() {
    }

    public JLPolyline(int id, JLOptions options, JLLatLng[] vertices) {
        this.id = id;
        this.options = options;
        this.vertices = vertices;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JLOptions getOptions() {
        return options;
    }

    public void setOptions(JLOptions options) {
        this.options = options;
    }

    public JLLatLng[] getVertices() {
        return vertices;
    }

    public void setVertices(JLLatLng[] vertices) {
        this.vertices = vertices;
    }
}
