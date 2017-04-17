import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projlab.rail.Proto;
import projlab.rail.exception.*;
import projlab.rail.logic.*;

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

    @Test
    public void unBoardTest(){



    }

    @Test
    public void unBoardFailedTest(){

    }

    @Test
    public void boardTest(){

    }


}