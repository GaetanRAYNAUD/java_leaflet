package fr.graynaud.maps.javaleaflet.model;

import java.util.Arrays;

/**
 * A class for drawing polygon overlays on the map. Note that points you pass when creating a polygon shouldn't have an additional last point equal to the first
 * one.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLPolygon extends JLObject<JLPolygon> {

    /**
     * id of JLPolygon! this is an internal id for JLMap Application and not related to Leaflet!
     */
    private int id;
    /**
     * theming options for JLMultiPolyline. all options are not available!
     */
    private JLOptions options;

    /**
     * The arrays of latlngs, with the first array representing the outer shape and the other arrays representing holes in the outer shape. Additionally, you
     * can pass a multi-dimensional array to represent a MultiPolygon shape.
     */
    private JLLatLng[][][] vertices;

    public JLPolygon() {
    }

    public JLPolygon(int id, JLOptions options, JLLatLng[][][] vertices) {
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

    public JLLatLng[][][] getVertices() {
        return vertices;
    }

    public void setVertices(JLLatLng[][][] vertices) {
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        return "JLPolygon{" +
               "id=" + id +
               ", options=" + options +
               ", vertices=" + Arrays.toString(vertices) +
               '}';
    }
}
