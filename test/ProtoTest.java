import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projlab.rail.Proto;
import projlab.rail.exception.CrashException;
import projlab.rail.exception.IllegalSwitchStateException;
import projlab.rail.exception.InactiveTunnelException;
import projlab.rail.exception.TrainException;
import projlab.rail.logic.Color;
import projlab.rail.logic.StaticEntity;

import static projlab.rail.logic.StaticEntity.ConnectionType.*;
import static projlab.rail.Proto.RailType.*;
import static projlab.rail.logic.Color.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProtoTest {

    private static Proto proto = null;

    private static int tunnelLeft;
    private static int tunnelRight1;

    private int tunnelRight2;

    @BeforeEach
    public void initProto() {
        proto = new Proto();

        int prev = createRails(-1, A, 10);

        int s1 = proto.createRail(SWITCH);
        proto.connect(prev, A, s1, A);

        int leftLine = createRails(s1, B, 4);
        int station = proto.createStation(RED);
        proto.connect(leftLine, A, station, B);
        leftLine = createRails(station, A, 4);

        int rightLine = createRails(s1, IN, 10);

        int cross = proto.createRail(CROSS);
        proto.connect(leftLine, A, cross, A);
        proto.connect(rightLine, A, cross, X);

        leftLine = createRails(cross, Y, 15);
        tunnelLeft = proto.createRail(TUNNEL);
        proto.connect(leftLine, A, tunnelLeft, VISIBLE);

        rightLine = createRails(cross, B, 3);
        int s2 = proto.createRail(SWITCH);
        proto.connect(rightLine, A, s2, IN);

        tunnelRight1 = proto.createRail(TUNNEL);
        tunnelRight2 = proto.createRail(TUNNEL);

        proto.connect(s2, A, tunnelRight1, VISIBLE);
        proto.connect(s2, B, tunnelRight2, VISIBLE);
    }

    private static int createRails(int toConnect, StaticEntity.ConnectionType connectionType, int number) {
        int current;
        for (int i = 0; i < number; i++) {
            current = proto.createRail(Proto.RailType.PLAIN);
            proto.connect(toConnect, i == 0 ? connectionType : StaticEntity.ConnectionType.A, current, StaticEntity.ConnectionType.B);
            toConnect = current;
        }
        return toConnect;
    }

    private static int createSampleTrain(){
        int current, prev;
        int locoId = proto.createLocomotive();
        prev = locoId;
        Color[] colors = Color.values();
        for(int i = 0; i < colors.length; i++){
            current = proto.createCar(colors[i]);
            proto.connectToTrain(prev, current);
            prev = current;
        }
        return locoId;
    }

    @Test
    public void levelTest() throws TrainException {
        int loco = proto.createLocomotive();
        int stepNumber = 0;
        try {
            proto.launch(loco);
            while (true) {
                proto.step();
                stepNumber++;
            }
        } catch (InactiveTunnelException ite) {
            // running until hitting tunnel
        }

        assertEquals(38, stepNumber);
    }

    @Test
    public void levelTest2() throws TrainException {
        int loco = proto.createLocomotive();
        int stepNumber = 0;
        try {
            proto.activateTunnel(tunnelLeft);
            proto.activateTunnel(tunnelRight1);
            proto.launch(loco);
            while (true) {
                proto.step();
                stepNumber++;
            }
        } catch (IllegalSwitchStateException isse) {
            // running until hitting 2nd switch
        }

        assertEquals(64, stepNumber);
    }

    @Test
    public void stepTest() throws CrashException {
        int locoId = createSampleTrain();


    }

    @Test
    public void tunnelTest() {

    }

    @Test
    public void crashTest(){

    }

    @Test
    public void obstacleCrashTest(){

    }

    @Test
    public void toggleCrashTest(){

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

    @Test
    public void victoryTest(){

    }

    @Test
    public void tunnelCreationTest(){

    }

    @Test
    public void tunnelDeletionTest(){

    }

    @Test
    public void defeatTest(){

    }

    @Test
    public void toggleTest(){

    }

}