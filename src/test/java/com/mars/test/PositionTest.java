package com.mars.test;

import java.util.HashMap;
import java.util.Map;

import com.mars.model.Position;
import com.mars.model.Sonda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import org.junit.Before;
import org.junit.Test;

public class PositionTest {
	
	
	public static Map<Position, Sonda> surfaceMap;
	
	@Before
	public void init(){
		surfaceMap = new HashMap<Position, Sonda>();
	}
	
	/**
	 * Tests the equality between two Positions
	 * the result should validate that the two Positions are equal
	 */
	@Test
	public void compareTwoEqual(){
		Position pos1 = new Position(3,3);
		Position pos2 = new Position(3,3);
		assertEquals(pos1, pos2);
	}
	
	/**
	 * Tests the inequality between two Positions
	 * the result should validate that the two Positions are different
	 */
	@Test
	public void compareTwoDifferent(){
		
		Position pos1 = new Position(3,3);
		Position pos2 = new Position(3,4);
		assertFalse(pos1.equals(pos2));
		
		
	}

	/**
	 * Tests the result when Sondas are inserted at the same position of the sondasMap
	 * the result should be the sondasMap size equals to 1
	 */	
	@Test
	public void testSurfaceMapSize(){
		
		Position pos1 = new Position(3,3);
		Sonda sonda1 = new Sonda(pos1, "W", 1);
		surfaceMap.put(pos1, sonda1);
			
		Position pos2 = new Position(3,3);
		Sonda sonda2 = new Sonda(pos2, "N", 2);
		surfaceMap.put(pos2, sonda2);
		
		Position pos3 = new Position(3,3);
		Sonda sonda3 = new Sonda(pos2, "E", 3);
		surfaceMap.put(pos3, sonda3);
		
		assertEquals(surfaceMap.size(), 1);
		
		
	}
	
	/**
	 * Tests what value is retained when multiple Sondas are added 
	 * to the position of the sondasMap
	 * the result should be the last heading value being equal to the one 
	 * of the last Sonda added
	 */		
	@Test
	public void testSurfaceMapLastItem(){
		
		Position pos1 = new Position(3,3);
		Sonda sonda1 = new Sonda(pos1, "W", 1);
		surfaceMap.put(pos1, sonda1);
			
		Position pos2 = new Position(3,3);
		Sonda sonda2 = new Sonda(pos2, "N", 2);
		surfaceMap.put(pos2, sonda2);
		
		Position pos3 = new Position(3,3);
		Sonda sonda3 = new Sonda(pos2, "E", 3);
		surfaceMap.put(pos3, sonda3);
		
		assertEquals(surfaceMap.get(new Position(3,3)).getHeading(), "E");
		
		
	}
	
	

}
