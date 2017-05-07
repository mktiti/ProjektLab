import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projlab.rail.GameState;
import projlab.rail.Proto;
import projlab.rail.exception.*;
import projlab.rail.logic.*;

import static projlab.rail.logic.StaticEntity.ConnectionType.*;
import static projlab.rail.Proto.RailType.*;
import static projlab.rail.logic.Color.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProtoTest {
/*
    private Proto proto = null;

    private int tunnelLeft;
    private int tunnelRight1;
    private int tunnelRight2;

    private int switch1;

    @BeforeEach
    public void initProto() {
        proto = new Proto();

        int prev = createRails(proto, -1, A, 10);

        switch1 = proto.createRail(SWITCH);
        proto.connect(prev, A, switch1, A);

        int leftLine = createRails(proto, switch1, B, 4);
        int station = proto.createStation(RED);
        proto.connect(leftLine, A, station, B);
        leftLine = createRails(proto, station, A, 4);

        int rightLine = createRails(proto, switch1, IN, 10);

        int cross = proto.createRail(CROSS);
        proto.connect(leftLine, A, cross, A);
        proto.connect(rightLine, A, cross, X);

        leftLine = createRails(proto, cross, Y, 15);
        tunnelLeft = proto.createRail(TUNNEL);
        proto.connect(leftLine, A, tunnelLeft, VISIBLE);

        rightLine = createRails(proto, cross, B, 3);
        int s2 = proto.createRail(SWITCH);
        proto.connect(rightLine, A, s2, IN);

        tunnelRight1 = proto.createRail(TUNNEL);
        tunnelRight2 = proto.createRail(TUNNEL);

        proto.connect(s2, A, tunnelRight1, VISIBLE);
        proto.connect(s2, B, tunnelRight2, VISIBLE);
    }

    static int createRails(Proto proto, int toConnect, StaticEntity.ConnectionType connectionType, int number) {
        int current;
        for (int i = 0; i < number; i++) {
            current = proto.createRail(Proto.RailType.PLAIN);
            proto.connect(toConnect, i == 0 ? connectionType : StaticEntity.ConnectionType.A, current, StaticEntity.ConnectionType.B);
            toConnect = current;
        }
        return toConnect;
    }

    private int createSampleTrain() {
        int current, prev;
        int locoId = proto.createLocomotive();
        prev = locoId;
        Color[] colors = Color.values();
        for(int i = 0; i < colors.length; i++){
            current = proto.createCar(colors[i]);
            proto.addPerson(current);
            proto.connectToTrain(prev, current);
            prev = current;
        }
        return locoId;
    }

    @Test
    public void obstacleCrashTest() throws TrainException {
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
    public void stepTest() throws TrainException {
        int locoId = createSampleTrain();

        Locomotive loco = proto.locomotives.get(locoId);
        StaticEntity first = proto.engine.entryPoint.next(proto.engine.entrySecond);
        proto.launch(locoId);
        StaticEntity prev = proto.engine.entryPoint;
        int cnt = Color.values().length+1;
        StaticEntity[] firstFive = new StaticEntity[cnt];
        firstFive[0] = first;
        for(int i = 1; i < firstFive.length; i++){
            firstFive[i] = firstFive[i-1].next(prev);
            prev = firstFive[i-1];
        }
        boolean matched = true;
        for(int i = 0; i < cnt; i++)
            proto.step();
        Car active = loco.next;
        for(int i = 0; i < cnt; i++){
            if(i == 0)
                matched = (loco.currentPosition == firstFive[cnt-(i+1)]);
            else {
                matched = active.currentPosition == firstFive[cnt - (i + 1)];
                active = active.next;
            }
            if(!matched)
                break;
        }
        assertEquals(true, matched);
    }

    @Test
    public void tunnelTest() throws TrainException {
        int locoId = proto.createLocomotive();
        Locomotive loco = proto.locomotives.get(locoId);
        proto.activateTunnel(tunnelLeft);
        proto.activateTunnel(tunnelRight1);
        Tunnel tun1 = proto.tunnels.get(tunnelRight1);
        Tunnel tun2 = proto.tunnels.get(tunnelLeft);

        loco.setPosition(tun1, tun1.visibleConnection);
        for(int i = 0; i < 10; i++){
            proto.step();
        }
        assertEquals(loco.currentPosition, tun2.hiddenConnection);

    }

    @Test
    public void crashTest() throws TrainException {
        assertThrows(CrashException.class, () -> {
            int train1 = createSampleTrain();
            int train2 = createSampleTrain();
            //
            // 53 össz, 31-nél indít a második
            //
            int stepNumber = 0;
            proto.activateTunnel(tunnelLeft);
            proto.activateTunnel(tunnelRight1);

            proto.launch(train1);
            while (true) {
                if(stepNumber == 31) {
                    proto.launch(train2);
                }
                proto.step();
                stepNumber++;
            }
        });
    }

    @Test
    public void toggleCrashTest(){
        assertThrows(IllegalSwitchStateException.class, () -> {
            proto.toggle(switch1);
            proto.launch(createSampleTrain());
            for (int i = 0; i < 12; i++) {
                proto.step();
            }
        });
    }

    @Test
    public void tunnelCreationTest(){
        Tunnel t1 = proto.tunnels.get(tunnelLeft);
        Tunnel t2 = proto.tunnels.get(tunnelRight1);

        proto.activateTunnel(tunnelLeft);
        proto.activateTunnel(tunnelRight1);

        assertNotEquals(null, t1.hiddenConnection);
        assertNotEquals(null, t2.hiddenConnection);
    }

    @Test
    public void tunnelDeletionTest(){
        Tunnel t1 = proto.tunnels.get(tunnelLeft);
        Tunnel t2 = proto.tunnels.get(tunnelRight1);

        proto.activateTunnel(tunnelLeft);
        proto.activateTunnel(tunnelRight1);

        proto.deactivateTunnel(tunnelLeft);

        assertEquals(null, t1.hiddenConnection);
        assertEquals(null, t2.hiddenConnection);
    }

    @Test
    public void defeatTest(){
        try {
            proto.toggle(switch1);
            proto.launch(createSampleTrain());
            for (int i = 0; i < 12; i++) {
                proto.step();
            }
        } catch (TrainException e) {}
        assertEquals(GameState.DEFEAT,proto.engine.state);
    }

    @Test
    public void toggleTest() throws IllegalSwitchStateException, IllegalMoveException {
        Switch sw = proto.switches.get(switch1);
        sw.toggle();

        StaticEntity se = sw.next(sw.input);

        assertEquals(sw.outputB, se);
    }
*/
}