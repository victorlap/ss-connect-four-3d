package utwente.ss.connect.common;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class Util {
	
	/**
	 * Checks to see if a specific port is available.
	 * http://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
	 *
	 * @param port
	 *            the port to check for availability
	 */
	public static boolean available(int port) {
		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}
}
