package fr.graynaud.maps.javaleaflet;

import com.sun.javafx.webkit.WebConsoleListener;
import fr.graynaud.maps.javaleaflet.layer.JLControlLayer;
import fr.graynaud.maps.javaleaflet.layer.JLGeoJsonLayer;
import fr.graynaud.maps.javaleaflet.layer.JLLayer;
import fr.graynaud.maps.javaleaflet.layer.JLUiLayer;
import fr.graynaud.maps.javaleaflet.layer.JLVectorLayer;
import fr.graynaud.maps.javaleaflet.listener.OnJLMapViewListener;
import fr.graynaud.maps.javaleaflet.model.JLMapOption;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLMapView extends JLMapController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JLMapView.class);

    private final WebView webView;

    private final JLMapCallbackHandler jlMapCallbackHandler;

    private Map<Class<? extends JLLayer>, JLLayer> layers;

    private boolean controllerAdded = false;

    private OnJLMapViewListener mapListener;

    private final JavaBridge bridge = new JavaBridge();

    public JLMapView(JLMapOption jlMapOption) {
        super(jlMapOption);
        this.webView = new WebView();
        this.jlMapCallbackHandler = new JLMapCallbackHandler(this);
        initialize();
    }

    private void removeMapBlur() {
        Transition gt = new MapTransition(this.webView);
        gt.play();
    }

    private void initialize() {
        this.webView.getEngine()
                    .onStatusChangedProperty()
                    .addListener(
                            (observable, oldValue, newValue) -> LOGGER.debug(String.format(Locale.US, "Old Value: %s\tNew Value: %s", oldValue, newValue)));
        this.webView.getEngine()
                    .onErrorProperty()
                    .addListener(
                            (observable, oldValue, newValue) -> LOGGER.debug(String.format(Locale.US, "Old Value: %s\tNew Value: %s", oldValue, newValue)));
        this.webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            checkForBrowsing(this.webView.getEngine());

            switch (newValue) {
                case Worker.State.FAILED -> LOGGER.info("failed to load!");
                case Worker.State.SUCCEEDED -> {
                    removeMapBlur();
                    this.webView.getEngine().executeScript("removeNativeAttr()");
                    addControllerToDocument();

                    if (this.mapListener != null) {
                        this.mapListener.mapLoadedSuccessfully(this);
                    }
                }
                default -> setBlurEffectForMap();
            }
        });

        this.webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            JSObject window = (JSObject) this.webView.getEngine().executeScript("window");
            window.setMember("java", bridge);
            this.webView.getEngine().executeScript("""
                                                           console.log = function(message)
                                                           {
                                                               java.log(message);
                                                           };""");
        });

        WebConsoleListener.setDefaultListener(
                (view, message, lineNumber, sourceId) -> LOGGER.error(String.format(Locale.US, "ln: %d m:%s", lineNumber, message)));

        Path tmpDirectory;
        Path tmpIndex = null;
        try (InputStream index = getClass().getResourceAsStream("/fr/graynaud/maps/javaleaflet/index.html");
             InputStream css = getClass().getResourceAsStream("/fr/graynaud/maps/javaleaflet/leaflet.css");
             InputStream js = getClass().getResourceAsStream("/fr/graynaud/maps/javaleaflet/leaflet.js")) {
            tmpDirectory = Files.createTempDirectory("jlmap");
            tmpIndex = tmpDirectory.resolve("index.html");
            Files.copy(index, tmpIndex);
            Files.copy(css, tmpDirectory.resolve("leaflet.css"));
            Files.copy(js, tmpDirectory.resolve("leaflet.js"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        this.webView.getEngine().load(String.format(Locale.US, "file:%s%s", tmpIndex.toFile().getAbsolutePath(), this.mapOption.toQueryString()));

        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().add(this.webView);
        customizeWebviewStyles();
    }

    private void checkForBrowsing(WebEngine engine) {
        String address = engine.getLoadWorker().getMessage().trim();

        if (address.contains("http://") || address.contains("https://")) {
            engine.getLoadWorker().cancel();

            try {
                String os = System.getProperty("os.name", "generic");
                if (os.toLowerCase().contains("mac")) {
                    Runtime.getRuntime().exec(new String[] {"open", address});
                } else if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(URI.create(address));
                } else {
                    Runtime.getRuntime().exec(new String[] {"xdg-open", address});
                }
            } catch (IOException e) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
    }

    private void setBlurEffectForMap() {
        if (webView.getEffect() == null) {
            GaussianBlur gaussianBlur = new GaussianBlur();
            gaussianBlur.setRadius(JLProperties.START_ANIMATION_RADIUS);
            webView.setEffect(gaussianBlur);
        }
    }

    private void customizeWebviewStyles() {
        setLeftAnchor(this.webView, 0.0);
        setRightAnchor(this.webView, 0.0);
        setTopAnchor(this.webView, 0.0);
        setBottomAnchor(this.webView, 0.0);

        setLeftAnchor(this, 0.5);
        setRightAnchor(this, 0.5);
        setTopAnchor(this, 0.5);
        setBottomAnchor(this, 0.5);
    }

    @Override
    protected Map<Class<? extends JLLayer>, JLLayer> getLayers() {
        if (this.layers == null) {
            this.layers = new HashMap<>();
            this.layers.put(JLUiLayer.class, new JLUiLayer(getWebView().getEngine(), this.jlMapCallbackHandler));
            this.layers.put(JLVectorLayer.class, new JLVectorLayer(getWebView().getEngine(), this.jlMapCallbackHandler));
            this.layers.put(JLControlLayer.class, new JLControlLayer(getWebView().getEngine(), this.jlMapCallbackHandler));
            this.layers.put(JLGeoJsonLayer.class, new JLGeoJsonLayer(getWebView().getEngine(), this.jlMapCallbackHandler));
        }

        return this.layers;
    }

    @Override
    protected WebView getWebView() {
        return this.webView;
    }

    @Override
    protected void addControllerToDocument() {
        if (!this.controllerAdded) {
            JSObject window = (JSObject) this.webView.getEngine().executeScript("window");
            window.setMember("app", this.jlMapCallbackHandler);
            LOGGER.debug("controller added to js scripts");
            this.controllerAdded = true;
        }

        this.webView.getEngine().setOnError(webErrorEvent -> LOGGER.error(webErrorEvent.getMessage()));
    }

    public Optional<OnJLMapViewListener> getMapListener() {
        return Optional.ofNullable(this.mapListener);
    }

    public void setMapListener(OnJLMapViewListener mapListener) {
        this.mapListener = mapListener;
    }

    private static class MapTransition extends Transition {

        private final WebView webView;

        public MapTransition(WebView webView) {
            this.webView = webView;
            setCycleDuration(Duration.millis(1000));
            setInterpolator(Interpolator.EASE_IN);
        }

        @Override
        protected void interpolate(double frac) {
            GaussianBlur eff = ((GaussianBlur) webView.getEffect());
            eff.setRadius(JLProperties.START_ANIMATION_RADIUS * (1 - frac));
        }
    }

    public static class JavaBridge {

        public void log(String text) {
            LOGGER.info(text);
        }
    }
}
