package fr.graynaud.maps.javaleaflet.layer;

import fr.graynaud.maps.javaleaflet.JLMapCallbackHandler;
import fr.graynaud.maps.javaleaflet.exception.JLException;
import fr.graynaud.maps.javaleaflet.geojson.JLGeoJsonContent;
import fr.graynaud.maps.javaleaflet.geojson.JLGeoJsonFile;
import fr.graynaud.maps.javaleaflet.geojson.JLGeoJsonObject;
import fr.graynaud.maps.javaleaflet.geojson.JLGeoJsonURL;
import fr.graynaud.maps.javaleaflet.layer.leaflet.LeafletGeoJsonLayerInt;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

/**
 * Represents the GeoJson (other) layer on Leaflet map.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLGeoJsonLayer extends JLLayer implements LeafletGeoJsonLayerInt {

    private static final String MEMBER_PREFIX = "geoJson";

    private static final String WINDOW_OBJ = "window";

    private final JLGeoJsonURL fromUrl;

    private final JLGeoJsonFile fromFile;

    private final JLGeoJsonContent fromContent;

    private final JSObject window;

    public JLGeoJsonLayer(WebEngine engine, JLMapCallbackHandler callbackHandler) {
        super(engine, callbackHandler);
        this.fromUrl = new JLGeoJsonURL();
        this.fromFile = new JLGeoJsonFile();
        this.fromContent = new JLGeoJsonContent();
        this.window = (JSObject) engine.executeScript(WINDOW_OBJ);
    }

    @Override
    public JLGeoJsonObject addFromFile(File file) throws JLException {
        String json = this.fromFile.load(file);
        return addGeoJson(json);
    }

    @Override
    public JLGeoJsonObject addFromUrl(String url) throws JLException {
        String json = this.fromUrl.load(url);
        return addGeoJson(json);
    }

    @Override
    public JLGeoJsonObject addFromContent(String content) throws JLException {
        String json = this.fromContent.load(content);
        return addGeoJson(json);
    }

    @Override
    public boolean removeGeoJson(JLGeoJsonObject object) {
        return Boolean.parseBoolean(this.engine.executeScript(String.format(Locale.US, "removeGeoJson(%d)", object.getId())).toString());
    }

    private JLGeoJsonObject addGeoJson(String jlGeoJsonObject) {
        try {
            String identifier = MEMBER_PREFIX + UUID.randomUUID();
            this.window.setMember(identifier, jlGeoJsonObject);
            String returnedId = this.engine.executeScript(String.format(Locale.US, "addGeoJson(\"%s\")", identifier)).toString();

            return new JLGeoJsonObject(Integer.parseInt(returnedId), jlGeoJsonObject);
        } catch (Exception e) {
            throw new JLException(e);
        }

    }
}
