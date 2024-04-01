package common;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Constants to be used by the primary subsystems
 */
public class NetworkConstants {

    // Port that the Scheduler will listen on
    public static final int SCHEDULER_PORT = 5000;

    // Port that the Floor will listen on
    public static final int FLOOR_PORT = 5001;

    /**
     * Wrapper method for getting localhost
     *
     * @return localhost address
     */

    public static InetAddress localHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
