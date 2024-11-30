package fr.graynaud.maps.javaleaflet.layer;

import fr.graynaud.maps.javaleaflet.JLMapCallbackHandler;
import javafx.scene.web.WebEngine;

/**
 * Represents the basic layer.
 *
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public abstract class JLLayer {

    protected WebEngine engine;

    protected JLMapCallbackHandler callbackHandler;

    protected JLLayer(WebEngine engine, JLMapCallbackHandler callbackHandler) {
        this.engine = engine;
        this.callbackHandler = callbackHandler;
    }

    /**
     * Forces the subclasses to implement {@link #JLLayer(WebEngine, JLMapCallbackHandler)} constructor!
     */
    private JLLayer() {
        // NO-OP
    }
}
