package fr.graynaud.maps.javaleaflet.model;

import fr.graynaud.maps.javaleaflet.listener.OnJLObjectActionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("JLMarker")
class JLMarkerTest implements ModelTest{
    private static final String MOVE_END_ACTION = OnJLObjectActionListener.Action.MOVE_END.getJsEventName();

    private JLMarker jlMarker;

    @BeforeEach
    void setUp() {
        jlMarker = new JLMarker(0, "", null);
    }

    @Test
    @DisplayName("Test update by moveend action")
    void testUpdateWithMoveEndEventAndNonNullLatLng() {
        JLLatLng expectedLatLng = new JLLatLng(1.0, 2.0);
        jlMarker.update(MOVE_END_ACTION, expectedLatLng);
        assertEquals(expectedLatLng, jlMarker.getLatLng());
    }

    @Test
    @DisplayName("Test update by moveend action with null point")
    void testUpdateWithMoveEndEventAndNullLatLng() {
        jlMarker.setLatLng(new JLLatLng(0, 0));
        jlMarker.update(MOVE_END_ACTION, null);
        assertNotNull(jlMarker.getLatLng());
    }

    @Test
    @DisplayName("Test update by wrong action")
    void testUpdateWithNonMoveEndEvent() {
        JLLatLng originalLatLng = new JLLatLng(3.0, 4.0);
        jlMarker.setLatLng(originalLatLng);

        jlMarker.update("click", new Object());
        assertEquals(originalLatLng, jlMarker.getLatLng());
    }

    @Test
    @DisplayName("Test setId method")
    void testSetAndGetId() {
        int expectedId = 123;
        jlMarker.setId(expectedId);
        assertEquals(expectedId, jlMarker.getId());
    }

    @Test
    @DisplayName("Test setText method")
    void testSetAndGetText() {
        String expectedText = "Marker Text";
        jlMarker.setText(expectedText);
        assertEquals(expectedText, jlMarker.getText());
    }

    @Test
    @DisplayName("Test setLatLng method")
    void testSetAndGetLatLng() {
        JLLatLng expectedLatLng = new JLLatLng(5.0, 6.0);
        jlMarker.setLatLng(expectedLatLng);
        assertEquals(expectedLatLng, jlMarker.getLatLng());
    }
}
