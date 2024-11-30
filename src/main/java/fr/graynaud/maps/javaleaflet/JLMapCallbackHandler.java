package fr.graynaud.maps.javaleaflet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.graynaud.maps.javaleaflet.listener.OnJLMapViewListener;
import fr.graynaud.maps.javaleaflet.listener.OnJLObjectActionListener;
import fr.graynaud.maps.javaleaflet.listener.event.ClickEvent;
import fr.graynaud.maps.javaleaflet.listener.event.MoveEvent;
import fr.graynaud.maps.javaleaflet.listener.event.ZoomEvent;
import fr.graynaud.maps.javaleaflet.model.JLBounds;
import fr.graynaud.maps.javaleaflet.model.JLCircleMarker;
import fr.graynaud.maps.javaleaflet.model.JLLatLng;
import fr.graynaud.maps.javaleaflet.model.JLMarker;
import fr.graynaud.maps.javaleaflet.model.JLMultiPolyline;
import fr.graynaud.maps.javaleaflet.model.JLObject;
import fr.graynaud.maps.javaleaflet.model.JLPolygon;
import fr.graynaud.maps.javaleaflet.model.JLPolyline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mehdi Akbarian Rastaghi (@makbn)
 */
public class JLMapCallbackHandler implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(JLMapCallbackHandler.class);

    private final transient JLMapView mapView;

    private final transient Map<Class<? extends JLObject<?>>, Map<Integer, JLObject<?>>> jlObjects;

    private final transient ObjectMapper objectMapper;

    private final Map<String, Class<? extends JLObject<?>>[]> classMap;

    public JLMapCallbackHandler(JLMapView mapView) {
        this.mapView = mapView;
        this.jlObjects = new HashMap<>();
        this.objectMapper = new ObjectMapper();
        this.classMap = new HashMap<>();
        initClassMap();
    }

    @SuppressWarnings("unchecked")
    private void initClassMap() {
        this.classMap.put("marker", new Class[] {JLMarker.class});
        this.classMap.put("marker_circle", new Class[] {JLCircleMarker.class});
        this.classMap.put("polyline", new Class[] {JLPolyline.class, JLMultiPolyline.class});
        this.classMap.put("polygon", new Class[] {JLPolygon.class});
    }

    /**
     * @param functionName name of source function from js
     * @param param1       name of object class
     * @param param2       id of object
     * @param param3       additional param
     * @param param4       additional param
     * @param param5       additional param
     */
    @SuppressWarnings("unchecked")
    public void functionCalled(String functionName, Object param1, Object param2, Object param3, Object param4, Object param5) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("function: {} \tparam1: {} \tparam2: {} " + "\tparam3: {} param4: {} \tparam5: {}", functionName, param1, param2, param3, param4,
                         param5);
        }

        try {
            //get target class of Leaflet layer in JL Application
            Class<?>[] targetClasses = this.classMap.get(param1);

            //function called by an known class
            if (targetClasses != null) {
                //one Leaflet class may map to multiple class in JL Application
                // like ployLine mapped to JLPolyline and JLMultiPolyline
                for (Class<?> targetClass : targetClasses) {
                    if (targetClass != null) {
                        //search for the other JLObject class if available
                        if (!this.jlObjects.containsKey(targetClass)) {
                            break;
                        }

                        JLObject<?> jlObject = this.jlObjects.get(targetClass).get(Integer.parseInt(String.valueOf(param2)));

                        //search for the other JLObject object if available
                        if (jlObject == null) {
                            break;
                        }

                        if (jlObject.getOnActionListener() == null) {
                            return;
                        }

                        //call listener and exit loop
                        if (callListenerOnObject(functionName, (JLObject<JLObject<?>>) jlObject, param1, param2, param3, param4, param5)) {
                            return;
                        }
                    }
                }
            } else if (param1.equals("main_map") && this.mapView.getMapListener().isPresent()) {
                switch (functionName) {
                    case "move" -> this.mapView.getMapListener()
                                               .get()
                                               .onAction(new MoveEvent(OnJLMapViewListener.Action.MOVE,
                                                                       this.objectMapper.readValue(String.valueOf(param4), JLLatLng.class),
                                                                       this.objectMapper.readValue(String.valueOf(param5), JLBounds.class),
                                                                       Integer.parseInt(String.valueOf(param3))));
                    case "movestart" -> this.mapView.getMapListener()
                                                    .get()
                                                    .onAction(new MoveEvent(OnJLMapViewListener.Action.MOVE_START,
                                                                            this.objectMapper.readValue(String.valueOf(param4), JLLatLng.class),
                                                                            this.objectMapper.readValue(String.valueOf(param5), JLBounds.class),
                                                                            Integer.parseInt(String.valueOf(param3))));
                    case "moveend" -> this.mapView.getMapListener()
                                                  .get()
                                                  .onAction(new MoveEvent(OnJLMapViewListener.Action.MOVE_END,
                                                                          this.objectMapper.readValue(String.valueOf(param4), JLLatLng.class),
                                                                          this.objectMapper.readValue(String.valueOf(param5), JLBounds.class),
                                                                          Integer.parseInt(String.valueOf(param3))));
                    case "click" ->
                            this.mapView.getMapListener().get().onAction(new ClickEvent(this.objectMapper.readValue(String.valueOf(param3), JLLatLng.class)));
                    case "zoom" -> this.mapView.getMapListener()
                                               .get()
                                               .onAction(new ZoomEvent(OnJLMapViewListener.Action.ZOOM, Integer.parseInt(String.valueOf(param3))));
                    default -> LOGGER.error("{} not implemented!", functionName);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private boolean callListenerOnObject(String functionName, JLObject<JLObject<?>> jlObject, Object... params) throws JsonProcessingException {
        switch (functionName) {
            case "move" -> {
                jlObject.getOnActionListener().move(jlObject, OnJLObjectActionListener.Action.MOVE);
                return true;
            }
            case "movestart" -> {
                jlObject.getOnActionListener().move(jlObject, OnJLObjectActionListener.Action.MOVE_START);
                return true;
            }
            case "moveend" -> {
                //update coordinate of the JLObject
                jlObject.update("moveend", this.objectMapper.readValue(String.valueOf(params[3]), JLLatLng.class));
                jlObject.getOnActionListener().move(jlObject, OnJLObjectActionListener.Action.MOVE_END);
                return true;
            }
            case "click" -> {
                jlObject.getOnActionListener().click(jlObject, OnJLObjectActionListener.Action.CLICK);
                return true;
            }
            default -> LOGGER.error("{} not implemented!", functionName);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void addJLObject(JLObject<?> object) {
        this.jlObjects.putIfAbsent((Class<? extends JLObject<?>>) object.getClass(), new HashMap<>());
        this.jlObjects.get(object.getClass()).put(object.getId(), object);
    }

    public void remove(Class<? extends JLObject<?>> targetClass, int id) {
        if (!this.jlObjects.containsKey(targetClass)) {
            return;
        }

        JLObject<?> object = this.jlObjects.get(targetClass).remove(id);

        if (object != null) {
            LOGGER.error("{} id: {} removed", targetClass.getSimpleName(), object.getId());
        }
    }
}
