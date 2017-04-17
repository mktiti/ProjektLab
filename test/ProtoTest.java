import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projlab.rail.Proto;
import projlab.rail.exception.*;
import projlab.rail.logic.*;

import static projlab.rail.logic.StaticEntity.ConnectionType.*;
import static projlab.rail.Proto.RailType.*;
import static projlab.rail.logic.Color.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProtoTest {

    private Proto proto = null;

    private int tunnelLeft;
    private int tunnelRight1;

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

    private int createRails(int toConnect, StaticEntity.ConnectionType connectionType, int number) {
        int current;
        for (int i = 0; i < number; i++) {
            current = proto.createRail(Proto.RailType.PLAIN);
            proto.connect(toConnect, i == 0 ? connectionType : StaticEntity.ConnectionType.A, current, StaticEntity.ConnectionType.B);
            toConnect = current;
        }
        return toConnect;
    }

    private int createSampleTrain(){
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
    public void crashTest(){
        int train1 = createSampleTrain();
        int train2 = createSampleTrain();
        //
        //55 össz, 27-nél indít a második

        boolean result = false;
        try{
            int stepNumber = 0;
            proto.activateTunnel(tunnelLeft);
            proto.activateTunnel(tunnelRight1);
            proto.launch(train1);
            while(true){
                System.out.println("Step: " + stepNumber);
                if(stepNumber == 27) {
                    //int train2 = createSampleTrain();
                    //proto.launch(train2);
                }
                proto.step();
                stepNumber++;
            }
        }catch (TrainException e) {
            e.printStackTrace();
            result = true;
        }
        assertEquals(true,result );
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