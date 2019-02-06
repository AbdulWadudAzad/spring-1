package com.beskilled.qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class VehicyleService {
    @Autowired
    @Qualifier(value = "bicyleBean")
    private Vehicle vehicle;

    public void service(){
        vehicle.start();
        vehicle.stop();
    }

}
