package com.flightgearcontroller;

/**
 * IChange interface - declares the method that will be called when change detected.
 */
@FunctionalInterface
public interface IChange {
    /**
     * move - moves the object using the given aileron and elevator parameters.
     * @param aileron The aileron value to move the object by.
     * @param elevator The elevator value to move the object by.
     */
    void move(double aileron, double elevator);
}
