import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import projlab.rail.Proto;
import projlab.rail.logic.Color;
import projlab.rail.logic.StaticEntity;

import static org.junit.jupiter.api.Assertions.*;

public class ProtoTest {

    private Proto proto = new Proto();

    @BeforeAll
    public void initProto() {
        proto = new Proto();

        int prev = createRails(-1, StaticEntity.ConnectionType.A, 10);

        int s1 = proto.createRail(Proto.RailType.SWITCH);
        proto.connect(prev, StaticEntity.ConnectionType.A, s1, StaticEntity.ConnectionType.A);

        int leftLine = createRails(s1, StaticEntity.ConnectionType.B, 4);
        int station = proto.createStation(Color.RED);
        proto.connect(leftLine, StaticEntity.ConnectionType.A, station, StaticEntity.ConnectionType.B);
        leftLine = createRails(station, StaticEntity.ConnectionType.A, 4);

        int rightLine = createRails(s1, StaticEntity.ConnectionType.IN, 10);

        int cross = proto.createRail(Proto.RailType.CROSS);
        proto.connect(leftLine, StaticEntity.ConnectionType.A, cross, StaticEntity.ConnectionType.A);
        proto.connect(rightLine, StaticEntity.ConnectionType.A, cross, StaticEntity.ConnectionType.X);

        leftLine = createRails(cross, StaticEntity.ConnectionType.B, 15);
        int tunnelLeft = proto.createRail(Proto.RailType.TUNNEL);
        proto.connect(leftLine, StaticEntity.ConnectionType.A, tunnelLeft, StaticEntity.ConnectionType.VISIBLE);

        rightLine = createRails(rightLine, StaticEntity.ConnectionType.A, 3);
        int s2 = proto.createRail(Proto.RailType.SWITCH);
        proto.connect(rightLine, StaticEntity.ConnectionType.A, s2, StaticEntity.ConnectionType.IN);

        int tunnelRight1 = proto.createRail(Proto.RailType.TUNNEL);
        int tunnelRight2 = proto.createRail(Proto.RailType.TUNNEL);

        proto.connect(s2, StaticEntity.ConnectionType.A, tunnelRight1, StaticEntity.ConnectionType.VISIBLE);
        proto.connect(s2, StaticEntity.ConnectionType.B, tunnelRight2, StaticEntity.ConnectionType.VISIBLE);
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

    @Test
    public void stepTest() {

    }

}