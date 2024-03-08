package common;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class containing constants to be used by the main subsystems.
 */
public class NetworkConstants {

    // Port that the Scheduler will listen on
    public static final int SCHEDULER_PORT = 5000;

    public static InetAddress localHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
