package com.mars.model;

import java.util.Map;

public class Sonda {
	
	private Position position;
	private String heading;	
	private int id;
	
	public Sonda(Position position, String heading, int id) {
		  this.position = position;
		  this.heading = heading;
		  this.id = id;
		}
	
	
	public Sonda() {
		
	}
	
	
	public int getID(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public Position getPosition() {
		return this.position;
	}
	
	public String getHeading() {
		return this.heading;
	}
	
	
	
	public void doAction(String action, Position upperRightBoundary, Map<Position, Sonda> surfaceMap) {
		switch (action) {
            case "L":
                this.turnLeft();
                break;
            case "R":
                this.turnRight();
                break;
            case "M":
                this.move(upperRightBoundary, surfaceMap);
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
	
	public void move(Position upperRightBoundary, Map<Position, Sonda> surfaceMap){
		Position newPosition;
		int x, y, newX, newY;
		switch (this.heading) {
            case "N":
            	x = this.position.getX();
            	newY = this.position.getY() + 1;
            	newPosition = new Position(x,newY);
            	
            	if (isActionValid(upperRightBoundary, newPosition, surfaceMap)) {
            		surfaceMap.remove(this.position);
            		this.position.setY(newY);            		
            		surfaceMap.put(newPosition, this);
            	}               
                break;
            case "S":
            	x = this.position.getX();
            	newY = this.position.getY() - 1;
            	newPosition = new Position(x,newY);
            	
            	if (isActionValid(upperRightBoundary, newPosition, surfaceMap)) {
            		surfaceMap.remove(this.position);
            		this.position.setY(newY);         		
            		surfaceMap.put(newPosition, this);
            	}
                break;
            case "E":
            	newX = this.position.getX() + 1;
            	y = this.position.getY();
            	newPosition = new Position(newX,y);
            	
            	if (isActionValid(upperRightBoundary, newPosition, surfaceMap)) {
            		surfaceMap.remove(this.position);
            		this.position.setX(newX);          		
            		surfaceMap.put(newPosition, this);
            	}
                break;
            case "W":
            	newX = this.position.getX() - 1;
            	y = this.position.getY();
            	newPosition = new Position(newX,y);
            	
            	if (isActionValid(upperRightBoundary, newPosition, surfaceMap)) {
            		surfaceMap.remove(this.position);
            		this.position.setX(newX);
            		surfaceMap.put(newPosition, this);
            	}
                break;				
            default:
                 System.out.println("Invalid action!");
         }
	}
	
	public boolean isActionValid(Position upperRightBoundary, Position newPosition, Map<Position, Sonda> surfaceMap) {
					
		//first sonda
		if (surfaceMap == null){
			return true;
		}
		
		//another sonda in same Position
		if (surfaceMap.get(newPosition) != null) {
			return false;
		}
		
		//out of surface
		if (newPosition.getX() >= 0 && newPosition.getX() <= upperRightBoundary.getX()
				&& newPosition.getY() >= 0 && newPosition.getY() <= upperRightBoundary.getY()) {
			return true;
		}else{
			return false;
		}
			
		
	}
	
	@Override
	public String toString() {
		
		return "[" + this.position.getX() + "," + this.position.getY() + "," + this.heading + "]";
	}

}
