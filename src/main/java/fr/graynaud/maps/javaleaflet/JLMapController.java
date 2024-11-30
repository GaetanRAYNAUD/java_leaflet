package fr.graynaud.maps.javaleaflet;

import fr.graynaud.maps.javaleaflet.exception.JLMapNotReadyException;
import fr.graynaud.maps.javaleaflet.layer.JLControlLayer;
import fr.graynaud.maps.javaleaflet.layer.JLGeoJsonLayer;
import fr.graynaud.maps.javaleaflet.layer.JLLayer;
import fr.graynaud.maps.javaleaflet.layer.JLUiLayer;
import fr.graynaud.maps.javaleaflet.layer.JLVectorLayer;
import fr.graynaud.maps.javaleaflet.model.JLLatLng;
import fr.graynaud.maps.javaleaflet.model.JLMapOption;
import javafx.concurrent.Worker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
abstract class JLMapController extends AnchorPane {

    protected JLMapOption mapOption;

    JLMapController(JLMapOption mapOption) {
        this.mapOption = mapOption;
    }

    protected abstract WebView getWebView();

    protected abstract void addControllerToDocument();

    protected abstract Map<Class<? extends JLLayer>, JLLayer> getLayers();

    /**
     * handle all functions for add/remove layers from UI layer
     *
     * @return current instance of {{@link JLUiLayer}}
     */
    public JLUiLayer getUiLayer() {
        checkMapState();
        return (JLUiLayer) getLayers().get(JLUiLayer.class);
    }

    /**
     * handle all functions for add/remove layers from Vector layer
     *
     * @return current instance of {{@link JLVectorLayer}}
     */
    public JLVectorLayer getVectorLayer() {
        checkMapState();
        return (JLVectorLayer) getLayers().get(JLVectorLayer.class);
    }

    public JLControlLayer getControlLayer() {
        checkMapState();
        return (JLControlLayer) getLayers().get(JLControlLayer.class);
    }

    public JLGeoJsonLayer getGeoJsonLayer() {
        checkMapState();
        return (JLGeoJsonLayer) getLayers().get(JLGeoJsonLayer.class);
    }

    /**
     * Sets the view of the map (geographical center).
     *
     * @param latLng Represents a geographical point with a certain latitude and longitude.
     */
    public void setView(JLLatLng latLng) {
        checkMapState();
        getWebView().getEngine().executeScript(String.format(Locale.US, "jlmap.panTo([%f, %f]);", latLng.lat(), latLng.lng()));
    }

    /**
     * Sets the view of the map (geographical center) with animation duration.
     *
     * @param duration Represents the duration of transition animation.
     * @param latLng   Represents a geographical point with a certain latitude and longitude.
     */
    public void setView(JLLatLng latLng, int duration) {
        checkMapState();
        getWebView().getEngine().executeScript(String.format(Locale.US, "setLatLng(%f, %f,%d);", latLng.lat(), latLng.lng(), duration));
    }

    private void checkMapState() {
        if (getWebView() == null || getWebView().getEngine().getLoadWorker().getState() != Worker.State.SUCCEEDED) {
            throw new JLMapNotReadyException();
        }
    }

}
