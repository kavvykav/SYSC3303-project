import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorDataTest {
        FloorData testPacket = new FloorData("10:25:35",15,true,15);
        @Test
        void getTime() {
            assertEquals(testPacket.returnTimeStamp(),"10:25:35");
        }

        @Test
        void getFloor() {
            assertEquals(testPacket.returnFloorNumber(),15);
        }

        @Test
        void getDirection() {
            assertTrue(testPacket.returnDirection());
        }

        @Test
        void getCarButton() {
            assertEquals(testPacket.returnCarButton(),15);
        }
}