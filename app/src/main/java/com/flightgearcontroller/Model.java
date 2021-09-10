package com.flightgearcontroller;

// importing the required libraries
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Model class - the main business logic behind the application.
 */
public class Model {
    // declaring an executor field to manage tasks and threads, and a printIt field to communicate with the simulator
    private final Executor executor;
    private PrintWriter printIt;

    /**
     * Model - constructor of the Model class.
     */
    public Model() {
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * setAileron - sets the aileron value of the aircraft in the simulator to the given value.
     * @param val The value to set the aircraft's aileron value to.
     */
    public void setAileron(double val) {
        executor.execute(() -> {
            if (printIt != null) {
                printIt.print("set /controls/flight/aileron " + val + "\r\n");
                printIt.flush();
            }
        });
    }

    /**
     * setThrottle - sets the throttle value of the aircraft in the simulator to the given value.
     * @param val The value to set the aircraft's throttle value to.
     */
    public void setThrottle(double val) {
        executor.execute(() -> {
            if (printIt != null) {
                printIt.print("set /controls/engines/current-engine/throttle " + val + "\r\n");
                printIt.flush();
            }
        });
    }

    /**
     * setRudder - sets the rudder value of the aircraft in the simulator to the given value.
     * @param val The value to set the aircraft's rudder value to.
     */
    public void setRudder(double val) {
        executor.execute(() -> {
            if (printIt != null) {
                printIt.print("set /controls/flight/rudder " + val + "\r\n");
                printIt.flush();
            }
        });
    }

    /**
     * setElevator - sets the elevator value of the aircraft in the simulator to the given value.
     * @param val The value to set the aircraft's elevator value to.
     */
    public void setElevator(double val) {
        executor.execute(() -> {
            if (printIt != null) {
                printIt.print("set /controls/flight/elevator " + val + "\r\n");
                printIt.flush();
            }
        });
    }

    /**
     * connectToSimulator - connects to the simulator.
     * @param ip The IP address of the host to connect to.
     * @param port The port number of the host to connect via.
     * @throws UnknownHostException If the given IP address can't be reached.
     */
    public void connectToSimulator(String ip, int port) throws UnknownHostException {
        executor.execute(() -> {
            try {
                // creating a socket using the given IP address and port number
                Socket fg = new Socket(ip, port);
                // creating a PrintWriter object to communicate with the simulator
                printIt = new PrintWriter(fg.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
