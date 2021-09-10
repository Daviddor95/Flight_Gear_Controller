package com.flightgearcontroller;

// importing the required libraries
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import com.flightgearcontroller.databinding.ActivityMainBinding;

/**
 * MainActivity class - the logic behind the user interface.
 */
public class MainActivity extends AppCompatActivity {
    // declaring a field to hold the ViewModel
    private ViewModel vm;

    /**
     * onCreate - creates the user interface and the logic behind it.
     * @param savedInstanceState The previously frozen state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // creating the ViewModel, and setting data binding to it
        vm = new ViewModel(new Model());
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(vm);
        // setting a function to be called when the joystick moves
        Joystick js = findViewById(R.id.joystick);
        js.onChange = (aileron, elevator) -> {
            vm.setAileron(aileron);
            vm.setElevator(elevator);
        };
        // setting listeners to the IP address and port number EditTexts
        EditText ipAddress = findViewById(R.id.ip);
        ipAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vm.setIp(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        EditText portNumber = findViewById(R.id.port);
        portNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vm.setPort(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        // setting listeners to the rudder's and throttle's SeekBars
        SeekBar rudderBar = findViewById(R.id.rudder);
        rudderBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vm.setRudder(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        SeekBar throttleBar = findViewById(R.id.throttle);
        throttleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vm.setThrottle(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        // setting a listener to the connect button
        Button connect = findViewById(R.id.connect);
        connect.setOnClickListener(v -> {
            vm.connectToSimulator();
            findViewById(R.id.appLayout).requestFocus();
            // Hiding the keyboard
            if (getCurrentFocus().getWindowToken() != null) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }
}
