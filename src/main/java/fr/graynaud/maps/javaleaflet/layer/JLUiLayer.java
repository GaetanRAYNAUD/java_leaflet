package fr.graynaud.maps.javaleaflet.layer;

import fr.graynaud.maps.javaleaflet.JLMapCallbackHandler;
import fr.graynaud.maps.javaleaflet.layer.leaflet.LeafletUILayerInt;
import fr.graynaud.maps.javaleaflet.model.JLLatLng;
import fr.graynaud.maps.javaleaflet.model.JLMarker;
import fr.graynaud.maps.javaleaflet.model.JLOptions;
import fr.graynaud.maps.javaleaflet.model.JLPopup;
import javafx.scene.web.WebEngine;

import java.util.Locale;

/**
 * Represents the UI layer on Leaflet map.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLUiLayer extends JLLayer implements LeafletUILayerInt {

    public JLUiLayer(WebEngine engine, JLMapCallbackHandler callbackHandler) {
        super(engine, callbackHandler);
    }

    /**
     * Add a {{@link JLMarker}} to the map with given text as content and {{@link JLLatLng}} as position.
     *
     * @param latLng position on the map.
     * @param text   content of the related popup if available!
     *
     * @return the instance of added {{@link JLMarker}} on the map.
     */
    @Override
    public JLMarker addMarker(JLLatLng latLng, String text, boolean draggable) {
        int index = Integer.parseInt(
                this.engine.executeScript(String.format(Locale.US, "addMarker(%f, %f, '%s', %b)", latLng.lat(), latLng.lng(), text, draggable)).toString());

        JLMarker marker = new JLMarker(index, text, latLng);
        this.callbackHandler.addJLObject(marker);

        return marker;
    }

    /**
     * Remove a {{@link JLMarker}} from the map.
     *
     * @param id of the marker for removing.
     *
     * @return {{@link Boolean#TRUE}} if removed successfully.
     */
    @Override
    public boolean removeMarker(int id) {
        String result = this.engine.executeScript(String.format(Locale.US, "removeMarker(%d)", id)).toString();

        this.callbackHandler.remove(JLMarker.class, id);

        return Boolean.parseBoolean(result);
    }

    /**
     * Add a {{@link JLPopup}} to the map with given text as content and {@link JLLatLng} as position.
     *
     * @param latLng  position on the map.
     * @param text    content of the popup.
     * @param options see {{@link JLOptions}} for customizing
     *
     * @return the instance of added {{@link JLPopup}} on the map.
     */
    @Override
    public JLPopup addPopup(JLLatLng latLng, String text, JLOptions options) {
        int index = Integer.parseInt(this.engine.executeScript(
                                                 String.format(Locale.US, "addPopup(%f, %f, \"%s\", %b , %b)", latLng.lat(), latLng.lng(), text, options.isCloseButton(), options.isAutoClose()))
                                                .toString());

        return new JLPopup(index, text, latLng, options);
    }

    /**
     * Add popup with {{@link JLOptions#DEFAULT}} options
     *
     * @see JLUiLayer#addPopup(JLLatLng, String, JLOptions)
     */
    @Override
    public JLPopup addPopup(JLLatLng latLng, String text) {
        return addPopup(latLng, text, JLOptions.DEFAULT);
    }

    /**
     * Remove a {@link JLPopup} from the map.
     *
     * @param id of the marker for removing.
     *
     * @return true if removed successfully.
     */
    @Override
    public boolean removePopup(int id) {
        return Boolean.parseBoolean(this.engine.executeScript(String.format(Locale.US, "removePopup(%d)", id)).toString());
    }
}
