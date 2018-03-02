package com.mars.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Sonda {
	
	private Position position;
	private String heading;	
	private static final AtomicInteger count = new AtomicInteger(1);
	private int id;
	
	public Sonda(Position position, String heading) {
	  this.position = position;
	  this.heading = heading;
	  this.id = count.incrementAndGet(); 
	}
	
	public Sonda() {}
	
	public Position getPosition() {
		return this.position;
	}
	
	public String getHeading() {
		return this.heading;
	}
	
	public void doAction(String action, Position upperRightBoundary, List<Sonda> sondasInMars) {
		switch (action) {
            case "L":
                this.turnLeft();
                break;
            case "R":
                this.turnRight();
                break;
            case "M":
                this.move(upperRightBoundary, sondasInMars);
                break;
            default:
                 System.out.println("Invalid!");
         }
	}
	
	public void turnLeft(){
		switch (this.heading) {
            case "N":
                this.heading = "W";
                break;
            case "S":
            	this.heading = "E";
                break;
            case "E":
            	this.heading = "N";
                break;
            case "W":
            	this.heading = "S";
                break;				
            default:
                 System.out.println("Invalid!");
         }
	}
	
	public void turnRight(){
		switch (this.heading) {
            case "N":
            	this.heading = "E";
                break;
            case "S":
            	this.heading = "W";
                break;
            case "E":
            	this.heading = "S";
                break;
            case "W":
            	this.heading = "N";
                break;				
            default:
                 System.out.println("Invalid!");
         }
	}
	
	public void move(Position upperRightBoundary, List<Sonda> sondasInMars){
		int newX, newY = 0;
		switch (this.heading) {
            case "N":
            	newY = this.position.getY() + 1;
            	if (isActionValid(upperRightBoundary, newY, "y", sondasInMars)) {
            		this.position.setY(newY);
            	}               
                break;
            case "S":
            	newY = this.position.getY() - 1;
            	if (isActionValid(upperRightBoundary, newY, "y", sondasInMars)) {
            		this.position.setY(newY);
            	}
                break;
            case "E":
            	newX = this.position.getX() + 1;
            	if (isActionValid(upperRightBoundary, newX, "x", sondasInMars)) {
            		this.position.setX(newX);
            	}
                break;
            case "W":
            	newX = this.position.getX() - 1;
            	if (isActionValid(upperRightBoundary, newX, "x", sondasInMars)) {
            		this.position.setX(newX);
            	}
                break;				
            default:
                 System.out.println("Invalid!");
         }
	}
	
	public boolean isActionValid(Position upperRightBoundary, int newCoordinate, 
			String axis, List<Sonda> sondasInMars) {
		
		
		//another sonda in same Position
		for (Sonda sonda : sondasInMars) {
			if (axis.equals("x")) {
				if (newCoordinate == sonda.position.getX() && 
						this.getPosition().getY() == sonda.position.getY()) { //if Position of this sonda is equal to another rover in the list
					return false;
				}
			} else { //axis is y
				if (newCoordinate == sonda.position.getY() && 
						this.getPosition().getX() == sonda.position.getX()) { //if Position of this sonda is equal to another rover in the list
					return false;
				}
			}
		}
		
		//out of surface
		if (axis.equals("x")) {
			if (newCoordinate >= 0 && newCoordinate <= upperRightBoundary.getX()) {
				return true;
			} else {
				return false;
			}
		} else { //axis is y
			if (newCoordinate >= 0 && newCoordinate <= upperRightBoundary.getY()) {
				return true;
			} else {
				return false;
			}
			
		}
		

	
		
		
	}
	
	@Override
	public String toString() {
		
		return "[" + this.position.getX() + "," + this.position.getY() + "," + this.heading + "]";
	}

}
