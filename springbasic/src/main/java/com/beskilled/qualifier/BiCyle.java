package com.beskilled.qualifier;

import org.springframework.stereotype.Component;

@Component(value = "bicyleBean")
public class BiCyle implements Vehicle {

    @Override
    public void start() {
        System.out.println("bicylle start");

    }

    @Override
    public void stop() {
        System.out.println("bicyle stop");

    }
}
