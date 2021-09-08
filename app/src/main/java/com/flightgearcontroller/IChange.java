package com.flightgearcontroller;

// IChange interface - declares the method that will be called when change detected
@FunctionalInterface
public interface IChange {
    // move - moves the object given the aileron and elevator parameters
    void move(double aileron, double elevator);
}
