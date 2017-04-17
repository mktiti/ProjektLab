import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projlab.rail.GameState;
import projlab.rail.Proto;
import projlab.rail.exception.*;
import projlab.rail.logic.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static projlab.rail.logic.StaticEntity.ConnectionType.*;
import static projlab.rail.Proto.RailType.*;
import static projlab.rail.logic.Color.*;

import static org.junit.jupiter.api.Assertions.*;

public class StationTest {

    private Proto proto = null;

    private int station;
    private Color stationColor;

    @BeforeEach
    public void initProto() {
        proto = new Proto();
        stationColor = Color.values()[new Random().nextInt(Color.values().length)];

        int prev = ProtoTest.createRails(proto, -1, A, 10);
        station = proto.createStation(stationColor);
        proto.connect(prev, A, station, B);
        ProtoTest.createRails(proto, station, A, 10);

    }

    private int createTrain(boolean hasPassengers) {
        List<Color> colors = new ArrayList<>(Color.values().length - 1);
        for (Color c : Color.values()) {
            if (c != stationColor) {
                colors.add(c);
            }
        }

        int current, prev;
        int locoId = proto.createLocomotive();
        prev = locoId;
        for (int i = 0; i < Color.values().length; i++) {
            current = proto.createCar(i == 0 ? stationColor : colors.get(i - 1));
            if (!hasPassengers) {
                proto.cars.get(current).hasPassengers = false;
            }
            proto.connectToTrain(prev, current);
            prev = current;
        }
        return locoId;
    }

    @Test
    public void unBoardTest() throws TrainException {
        int loco = createTrain(true);
        Car firstCar = proto.locomotives.get(loco).next;
        proto.launch(loco);
        for (int i = 0; i < 11; i++) {
            assertEquals(true, firstCar.hasPassengers);
            proto.step();
        }
        assertEquals(false, firstCar.hasPassengers);
    }

    // TODO
    @Test
    public void unBoardFailedTest() throws TrainException {
        int loco = createTrain(true);
        Car secondCar = proto.locomotives.get(loco).next;
        int firstCarId = proto.createCar(stationColor);
        Car firstCar = proto.cars.get(firstCarId);
        firstCar.hasPassengers = false;
        proto.locomotives.get(loco).next = firstCar;
        firstCar.next = secondCar;
        secondCar.next = secondCar.next.next;

        proto.launch(loco);
        for (int i = 0; i < 12; i++) {
            assertEquals(false, firstCar.hasPassengers);
            assertEquals(true, secondCar.hasPassengers);
            proto.step();
        }
    }

    @Test
    public void boardTest() throws TrainException {
        int locoId = createTrain(true);
        Locomotive loco = proto.locomotives.get(locoId);
        loco.next.hasPassengers = false;
        proto.addPerson(station, stationColor);
        proto.launch(locoId);
        for (int i = 0; i < 11; i++) {
            proto.step();
        }
        assertEquals(true,loco.next.hasPassengers);
    }

    @Test
    public void victoryTest() throws TrainException {
        int loco = createTrain(false);
        Car firstCar = proto.locomotives.get(loco).next;
        firstCar.hasPassengers = true;
        proto.launch(loco);
        for (int i = 0; i < 11; i++) {
            proto.step();
        }
        assertEquals(GameState.VICTORY, proto.engine.state);
    }

}