package com.flightgearcontroller;

// importing the required libraries
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import java.net.UnknownHostException;

// ViewModel class - the bridge between the View and the Model
public class ViewModel extends BaseObservable {
    // declaring a mdl field to hold a reference to the model, and rudder, throttle, ip and port fields to data-bind to
    private final Model mdl;
    private int rudder;
    private int throttle;
    private String ip;
    private String port;
    private String message;
    private boolean showControls;

    // ViewModel - constructor of the ViewModel class
    public ViewModel(Model m) {
        mdl = m;
        rudder = 1000;
        showControls = false;
        message = "Please connect to machine running the Flight Gear application using the machine's IP " +
                "address and port number in order to proceed";
        notifyPropertyChanged(BR.message);
    }

    // setIp - sets the ip address using the given value
    public void setIp(String ipAddress) {
        ip = ipAddress;
        notifyPropertyChanged(BR.ip);
    }

    // getIp - returns the ip address
    @Bindable
    public String getIp() {
        return ip;
    }

    // setPort - sets the port number using the given value
    public void setPort(String portNumber) {
        port = portNumber;
        notifyPropertyChanged(BR.port);
    }

    // getPort - returns the port number
    @Bindable
    public String getPort() {
        return port;
    }

    // setAileron - sets the aileron using the given value
    public void setAileron(double val) {
        mdl.setAileron(val);
    }

    // setElevator - sets the elevator using the given value
    public void setElevator(double val) {
        mdl.setElevator(val);
    }

    // setThrottle - sets the throttle using the given value
    public void setThrottle(int val) {
        throttle = val;
        notifyPropertyChanged(BR.throttle);
        // converting the value to fit the required range
        mdl.setThrottle(throttle / 1000.0);
    }

    // getThrottle - returns the throttle value
    @Bindable
    public int getThrottle() {
        return throttle;
    }

    // setRudder - sets the rudder using the given value
    public void setRudder(int val) {
        rudder = val;
        notifyPropertyChanged(BR.rudder);
        // converting the value to fit the required range
        mdl.setRudder((rudder - 1000) / 1000.0);
    }

    // getRudder - returns the rudder value
    @Bindable
    public int getRudder() {
        return rudder;
    }

    public void setShowControls(boolean bool) {
        showControls = bool;
        notifyPropertyChanged(BR.showControls);
    }

    @Bindable
    public boolean getShowControls() {
        return showControls;
    }

    public void setMessage(String newMessage) {
        message = newMessage;
        notifyPropertyChanged(BR.message);
    }

    @Bindable
    public String getMessage() {
        return message;
    }

    private boolean isValidPort() {
        try {
            int portNum = port == null ? -1 : Integer.parseInt(port);
            if (portNum < 0 || portNum > 65535) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            message = "Please enter a valid port number";
            notifyPropertyChanged(BR.message);
            return false;
        }
        return true;
    }

    private boolean isValidIP() {
        String[] ipCheck = ip == null ? null : ip.split("\\.");
        try {
            if (ipCheck == null || ipCheck.length != 4) {
                throw new NumberFormatException();
            } else {
                for (String ipPart : ipCheck) {
                    int part = Integer.parseInt(ipPart);
                    if (part < 0 || part > 255) {
                        throw new NumberFormatException();
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            message = "Please enter a valid IP address";
            notifyPropertyChanged(BR.message);
            return false;
        }
        return true;
    }

    // connectToSimulator - connects the client to the server (the simulator) over TCP/IP protocol
    public void connectToSimulator() {
        // checking if the user entered a valid ip address and port number
        if (!isValidIP() || !isValidPort()) {
            return;
        }
        try {
            // after input validation - connecting to the simulator
            mdl.connectToSimulatorInModel(ip, Integer.parseInt(port));
            showControls = true;
            notifyPropertyChanged(BR.showControls);
            message = "Connection established";
            notifyPropertyChanged(BR.message);
        } catch (UnknownHostException uhe) {
            message = "Connection to simulator failed, please verify your ip address and port number";
            notifyPropertyChanged(BR.message);
            showControls = false;
            notifyPropertyChanged(BR.showControls);
        }
    }
}
