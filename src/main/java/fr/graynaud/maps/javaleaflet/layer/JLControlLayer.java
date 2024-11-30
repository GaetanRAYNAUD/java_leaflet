package fr.graynaud.maps.javaleaflet.layer;

import fr.graynaud.maps.javaleaflet.JLMapCallbackHandler;
import fr.graynaud.maps.javaleaflet.layer.leaflet.LeafletControlLayerInt;
import fr.graynaud.maps.javaleaflet.model.JLBounds;
import fr.graynaud.maps.javaleaflet.model.JLLatLng;
import javafx.scene.web.WebEngine;

import java.util.Locale;

/**
 * Represents the Control layer on Leaflet map.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLControlLayer extends JLLayer implements LeafletControlLayerInt {

    public JLControlLayer(WebEngine engine, JLMapCallbackHandler callbackHandler) {
        super(engine, callbackHandler);
    }

    @Override
    public void zoomIn(int delta) {
        this.engine.executeScript(String.format(Locale.US, "getMap().zoomIn(%d)", delta));
    }

    @Override
    public void zoomOut(int delta) {
        this.engine.executeScript(String.format(Locale.US, "getMap().zoomOut(%d)", delta));
    }

    @Override
    public void setZoom(int level) {
        this.engine.executeScript(String.format(Locale.US, "getMap().setZoom(%d)", level));
    }

    @Override
    public void setZoomAround(JLLatLng latLng, int zoom) {
        this.engine.executeScript(String.format(Locale.US, "getMap().setZoomAround(L.latLng(%f, %f), %d)", latLng.lat(), latLng.lng(), zoom));
    }

    @Override
    public void fitBounds(JLBounds bounds) {
        this.engine.executeScript(String.format(Locale.US, "getMap().fitBounds(%s)", bounds.toString()));
    }

    @Override
    public void fitWorld() {
        this.engine.executeScript("getMap().fitWorld()");
    }

    @Override
    public void panTo(JLLatLng latLng) {
        this.engine.executeScript(String.format(Locale.US, "getMap().panTo(L.latLng(%f, %f))", latLng.lat(), latLng.lng()));
    }

    @Override
    public void flyTo(JLLatLng latLng, int zoom) {
        this.engine.executeScript(String.format(Locale.US, "getMap().flyTo(L.latLng(%f, %f), %d)", latLng.lat(), latLng.lng(), zoom));
    }

    @Override
    public void flyToBounds(JLBounds bounds) {
        this.engine.executeScript(String.format(Locale.US, "getMap().flyToBounds(%s)", bounds.toString()));
    }

    @Override
    public void setMaxBounds(JLBounds bounds) {
        this.engine.executeScript(String.format(Locale.US, "getMap().setMaxBounds(%s)", bounds.toString()));
    }

    @Override
    public void setMinZoom(int zoom) {
        this.engine.executeScript(String.format(Locale.US, "getMap().setMinZoom(%d)", zoom));
    }

    @Override
    public void setMaxZoom(int zoom) {
        this.engine.executeScript(String.format(Locale.US, "getMap().setMaxZoom(%d)", zoom));
    }

    @Override
    public void panInsideBounds(JLBounds bounds) {
        this.engine.executeScript(String.format(Locale.US, "getMap().panInsideBounds(%s)", bounds.toString()));
    }

    @Override
    public void panInside(JLLatLng latLng) {
        this.engine.executeScript(String.format(Locale.US, "getMap().panInside(L.latLng(%f, %f))", latLng.lat(), latLng.lng()));
    }
}
