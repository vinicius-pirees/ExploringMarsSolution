package com.mars.model;

public class Position {
	
	private int x;
	private int y;

	
	public Position(int x, int y) {
	  this.x = x;
	  this.y = y;
	}
	
	public Position(){}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	protected void setX(int x) {
		this.x = x;
	}
	
	protected void setY(int y) {
		this.y = y;
	}

}

