package fr.graynaud.maps.javaleaflet.model;

import java.util.Arrays;

/**
 * A class for drawing polyline overlays on a map
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLMultiPolyline extends JLObject<JLMultiPolyline> {

    /**
     * id of JLMultiPolyline! this is an internal id for JLMap Application and not related to Leaflet!
     */
    private int id;
    /**
     * theming options for JLMultiPolyline. all options are not available!
     */
    private JLOptions options;
    /**
     * The array of {@link JLLatLng} points of JLMultiPolyline
     */
    private JLLatLng[][] vertices;

    public JLMultiPolyline() {
    }

    public JLMultiPolyline(int id, JLOptions options, JLLatLng[][] vertices) {
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

    public JLLatLng[][] getVertices() {
        return vertices;
    }

    public void setVertices(JLLatLng[][] vertices) {
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        return "JLMultiPolyline{" +
               "id=" + id +
               ", options=" + options +
               ", vertices=" + Arrays.toString(vertices) +
               '}';
    }
}
