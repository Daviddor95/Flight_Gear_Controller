package com.flightgearcontroller;

// importing the required libraries
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// Model class - the main business logic behind the project
public class Model {
    // declaring a taskQueue to hold the tasks, printIt to interact with the simulator, and terminate flag
    private final Executor executor;
    private PrintWriter printIt;

    // Model - constructor of the Model class
    public Model() {
        executor = Executors.newSingleThreadExecutor();
    }

    // setAileron - adds the "set aileron" send to simulator command task to the queue
    public void setAileron(double val) {
        executor.execute(() -> {
            if (printIt != null) {
                printIt.print("set /controls/flight/aileron " + val + "\r\n");
                printIt.flush();
            }
        });
    }

    // setThrottle - adds the "set throttle" send to simulator command task to the queue
    public void setThrottle(double val) {
        executor.execute(() -> {
            if (printIt != null) {
                printIt.print("set /controls/engines/current-engine/throttle " + val + "\r\n");
                printIt.flush();
            }
        });
    }

    // setRudder - adds the "set rudder" send to simulator command task to the queue
    public void setRudder(double val) {
        executor.execute(() -> {
            if (printIt != null) {
                printIt.print("set /controls/flight/rudder " + val + "\r\n");
                printIt.flush();
            }
        });
    }

    // setElevator - adds the "set elevator" send to simulator command task to the queue
    public void setElevator(double val) {
        executor.execute(() -> {
            if (printIt != null) {
                printIt.print("set /controls/flight/elevator " + val + "\r\n");
                printIt.flush();
            }
        });
    }

    // connectToSimulatorInModel - adds the connect to simulator task to the queue
    public void connectToSimulatorInModel(String ip, int port) throws UnknownHostException {
        executor.execute(() -> {
            Socket fg = null;
            try {
                // creating a socket using the given ip address and port number
                fg = new Socket(ip, port);
                // creating a PrintWriter object to communicate with the simulator
                printIt = new PrintWriter(fg.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
