package com.flightgearcontroller;

// importing the required libraries
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import java.net.UnknownHostException;

/**
 * ViewModel class - the bridge between the View and the Model.
 */
public class ViewModel extends BaseObservable {
    /* declaring a mdl field to hold a reference to the model, and rudder, throttle, ip, port, message and
       showControls fields to data-bind between them and the view */
    private final Model mdl;
    private int rudder;
    private int throttle;
    private String ip;
    private String port;
    private String message;
    private boolean showControls;

    /**
     * ViewModel - constructor of the ViewModel class.
     * @param m The business logic behind the application.
     */
    public ViewModel(Model m) {
        mdl = m;
        rudder = 1000;
        message = "Please connect to machine running the Flight Gear application using the machine's IP " +
                "address and port number in order to proceed";
    }

    /**
     * setIp - sets the ip address using the given value.
     * @param ipAddress The IP address to connect to it later.
     */
    public void setIp(String ipAddress) {
        ip = ipAddress;
        notifyPropertyChanged(BR.ip);
    }

    /**
     * getIp - returns the ip address.
     * @return The entered IP address.
     */
    @Bindable
    public String getIp() {
        return ip;
    }

    /**
     * setPort - sets the port number using the given value.
     * @param portNumber The port number to connect via it later.
     */
    public void setPort(String portNumber) {
        port = portNumber;
        notifyPropertyChanged(BR.port);
    }

    /**
     * getPort - returns the port number.
     * @return The entered port number.
     */
    @Bindable
    public String getPort() {
        return port;
    }

    /**
     * setAileron - sets the aileron using the given value.
     * @param val The aileron value to set the aircraft to.
     */
    public void setAileron(double val) {
        mdl.setAileron(val);
    }

    /**
     * setElevator - sets the elevator using the given value.
     * @param val The elevator value to set the aircraft to.
     */
    public void setElevator(double val) {
        mdl.setElevator(val);
    }

    /**
     * setThrottle - sets the throttle using the given value.
     * @param val The throttle value to set the aircraft to.
     */
    public void setThrottle(int val) {
        throttle = val;
        notifyPropertyChanged(BR.throttle);
        // converting the value to fit the required range
        mdl.setThrottle(throttle / 1000.0);
    }

    /**
     * getThrottle - returns the throttle value.
     * @return The current throttle value.
     */
    @Bindable
    public int getThrottle() {
        return throttle;
    }

    /**
     * setRudder - sets the rudder using the given value.
     * @param val The rudder value to set the aircraft to.
     */
    public void setRudder(int val) {
        rudder = val;
        notifyPropertyChanged(BR.rudder);
        // converting the value to fit the required range
        mdl.setRudder((rudder - 1000) / 1000.0);
    }

    /**
     * getRudder - returns the rudder value.
     * @return The current rudder value.
     */
    @Bindable
    public int getRudder() {
        return rudder;
    }

    /**
     * setShowControls - shows/hides the controls based on the given value.
     * @param bool true to show the controls, false to hide the controls.
     */
    public void setShowControls(boolean bool) {
        showControls = bool;
        notifyPropertyChanged(BR.showControls);
    }

    /**
     * getShowControls - returns the showControls field's value.
     * @return The value of the showControls field.
     */
    @Bindable
    public boolean getShowControls() {
        return showControls;
    }

    /**
     * setMessage - sets a message to be displayed to the user.
     * @param newMessage A message to display to the user.
     */
    public void setMessage(String newMessage) {
        message = newMessage;
        notifyPropertyChanged(BR.message);
    }

    /**
     * getMessage - returns the message.
     * @return The current message.
     */
    @Bindable
    public String getMessage() {
        return message;
    }

    /**
     * connectToSimulator - connects the client to the server (the simulator) over TCP/IP protocol.
     */
    public void connectToSimulator() {
        // checking if the user entered a valid ip address and port number
        if (!isValidIP() || !isValidPort()) {
            return;
        }
        try {
            // after input validation - connecting to the simulator and showing the controls and an appropriate message
            mdl.connectToSimulator(ip, Integer.parseInt(port));
            showControls = true;
            notifyPropertyChanged(BR.showControls);
            message = "Connection established";
            notifyPropertyChanged(BR.message);
        } catch (UnknownHostException uhe) {
            // hiding the controls and showing an appropriate message if exception have been thrown
            message = "Connection to simulator failed, please verify your ip address and port number";
            notifyPropertyChanged(BR.message);
            showControls = false;
            notifyPropertyChanged(BR.showControls);
        }
    }

    /**
     * isValidPort - checks if the entered port number is valid.
     * @return True if the entered port number is valid, false otherwise.
     */
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

    /**
     * isValidIP - checks if the entered IP address is valid.
     * @return True if the entered IP address is valid, false otherwise.
     */
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
}
