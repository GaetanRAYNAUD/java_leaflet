package fr.graynaud.maps.javaleaflet.model;

/**
 * Used to open popups in certain places of the map.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLPopup {

    /**
     * id of JLPopup! this is an internal id for JLMap Application and not related to Leaflet!
     */
    private int id;
    /**
     * Content of the popup.
     */
    private String text;
    /**
     * Coordinates of the popup on the map.
     */
    private JLLatLng latLng;
    /**
     * Theming options for JLPopup. all options are not available!
     */
    private JLOptions options;

    public JLPopup() {
    }

    public JLPopup(int id, String text, JLLatLng latLng, JLOptions options) {
        this.id = id;
        this.text = text;
        this.latLng = latLng;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JLLatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(JLLatLng latLng) {
        this.latLng = latLng;
    }

    public JLOptions getOptions() {
        return options;
    }

    public void setOptions(JLOptions options) {
        this.options = options;
    }
}
