package com.mars;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mars.model.Position;
import com.mars.model.Sonda;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    public static Position upperRightBoundary;
    public static List<Sonda> listOfSondas;
}