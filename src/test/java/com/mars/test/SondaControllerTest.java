package com.mars.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mars.Application;
import com.mars.model.Position;


public class SondaControllerTest extends ExploringMarsTest {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
	    Application.upperRightBoundary = null;
	    Application.sondasMap.clear();
	    Application.surfaceMap.clear();
	    
	}
	
	
	/**
	 * Tests the insertion of one sonda, the result should be
	 * the sonda returned as Json and the same sonda added to sondasMap and surfaceMap
	 */	
	@Test
	public void testInsertSonda() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		

		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":3}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.position.x").value("1"))
			.andExpect(jsonPath("$.position.y").value("2"))
			.andExpect(jsonPath("$.heading").value("N"))
			.andExpect(jsonPath("$.id").value("3"))
			.andExpect(content().contentType("application/json;charset=UTF-8"));
		
		assertEquals(Application.sondasMap.size(), 1);
		assertEquals(Application.surfaceMap.size(), 1);
		
	}
	
	
	/**
	 * Tests the retrieval of all sondas inserted so far, the result 
	 * includes a map returned as Json. In this test 2 sondas are 
	 * added, so the size of sondasMap should be 2
	 */	
	@Test
	public void testGetAllSondas() throws Exception {
		
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		

		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":1}"));
		
		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 3,\"y\": 3},\"heading\": \"E\",\"id\":2}"));
		
		
		mockMvc.perform(get("/mars/allsondas"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.1.position.x").value("1"))
			.andExpect(jsonPath("$.1.position.y").value("2"))
			.andExpect(jsonPath("$.1.heading").value("N"))
			.andExpect(jsonPath("$.2.position.x").value("3"))
			.andExpect(jsonPath("$.2.position.y").value("3"))
			.andExpect(jsonPath("$.2.heading").value("E"));
		
		assertEquals(Application.sondasMap.size(), 2);
		
		
	}
	
	
	/**
	 * Tests the retrieval of a sonda by an id 
	 * the result should be the sonda returned as Json
	 */		
	@Test
	public void testGetSondabyId() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		

		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":1}"));
		
		mockMvc.perform(get("/mars/getsonda/{id}", 1))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.position.x").value("1"))
			.andExpect(jsonPath("$.position.y").value("2"))
			.andExpect(jsonPath("$.heading").value("N"))
			.andExpect(jsonPath("$.id").value("1"));

	}
	
	/**
	 * Tests the movement of one sonda, after a sonda is inserted it can be moved
	 * by passing its id and the movement string
	 * The result should be a sonda return as Json at the new Position
	 */		
	@Test
	public void testMoveOneSonda() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		

		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":1}"));	
		
		mockMvc.perform(put("/mars/movesonda/{id}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"movements\": \"LMLMLMLMM\"}"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.position.x").value("1"))
				.andExpect(jsonPath("$.position.y").value("3"))
				.andExpect(jsonPath("$.heading").value("N"));
	}

	/**
	 * Tests the movement of two sondas
	 * The result should be both sondas returned as Json at their new Positions
	 */		
	@Test
	public void testMoveTwoSondas() throws Exception {
		
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		

		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":1}"));	
		
		mockMvc.perform(put("/mars/movesonda/{id}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"movements\": \"LMLMLMLMM\"}"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.position.x").value("1"))
				.andExpect(jsonPath("$.position.y").value("3"))
				.andExpect(jsonPath("$.heading").value("N"));
		
		
		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 3,\"y\": 3},\"heading\": \"E\",\"id\":2}"));	
		
		mockMvc.perform(put("/mars/movesonda/{id}", 2)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"movements\": \"MMRMMRMRRM\"}"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.position.x").value("5"))
				.andExpect(jsonPath("$.position.y").value("1"))
				.andExpect(jsonPath("$.heading").value("E"));
		
	}
	
	/**
	 * Tests when a sonda is inserted out of the surface
	 * The result should be an Exception thrown with a 
	 * message saying that the sonda couldn't be created
	 */	
	@Test
	public void testInsertOutOfSurface() throws Exception {
		
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		
		try {
			
			mockMvc.perform(post("/mars/sonda")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"position\": {\"x\": 6,\"y\": 2},\"heading\": \"N\",\"id\":1}"))
					.andDo(print());
			
			fail("This should have thrown an exception");
			
		} catch (Exception e){
			assertEquals("Request processing failed; nested exception is java.lang.Exception: The sonda cannot be created!", e.getMessage());
		}
	}
	
	/**
	 * Tests when a sonda is moved out of the boundary
	 * the result is that sonda is not moved when it reaches the boundary
	 */	
	@Test
	public void testMoveOutOfSurface() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		
		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":1}"));
		
		
	
			
		mockMvc.perform(put("/mars/movesonda/{id}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"movements\": \"MMMMMMMMMMMM\"}"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.position.x").value("1"))
				.andExpect(jsonPath("$.position.y").value("5"))
				.andExpect(jsonPath("$.heading").value("N"));
			

	}
	
	/**
	 * Tests when the movement string contains characters that are not 'L', 'R' or 'M'
	 * the result is that sonda is not moved when command is invalid
	 */	
	@Test
	public void testInvalidMoves() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		
		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":1}"));
	
			
		mockMvc.perform(put("/mars/movesonda/{id}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"movements\": \"Mjfh0M\"}"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.position.x").value("1"))
				.andExpect(jsonPath("$.position.y").value("4"))
				.andExpect(jsonPath("$.heading").value("N"));
			

	}

	/**
	 * Tests when a sonda collides with another one
	 * the result should be that when the sonda tries to go to a Position where there 
	 * is already another sonda, nothing happens
	 * the subsequent commands that don't make the sonda collide with another sonda 
	 * are executed normally
	 */	
	@Test
	public void testSondaCollison() throws Exception {
		
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		

		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":1}"));	
		
		
		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 2,\"y\": 2},\"heading\": \"W\",\"id\":2}"));	
		
		mockMvc.perform(put("/mars/movesonda/{id}", 2)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"movements\": \"MRM\"}"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.position.x").value("2"))
				.andExpect(jsonPath("$.position.y").value("3"))
				.andExpect(jsonPath("$.heading").value("N"));
		
	}
	
	/**
	 * Tests the deletion of a  sonda
	 * the result should be the sonda successfully removed and 
	 * a message confirming the exclusion
	 */	
	@Test
	public void testSondaDeletion() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		

		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":1}"));
		
		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 3,\"y\": 2},\"heading\": \"W\",\"id\":2}"));
		
		mockMvc.perform(delete("/mars/deletesonda/{id}", 1))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("Sonda with id = 1 was deleted"));
		
		assertEquals(Application.sondasMap.size(), 1);
		assertEquals(Application.sondasMap.get(1), null);
		assertEquals(Application.sondasMap.get(2).getHeading(), "W");
		assertEquals(Application.sondasMap.get(2).getPosition(), new Position(3,2));
	}
	
	
	/**
	 * Tests that the deletion of an invalid sonda throws an Exception
	 * the result should be an Exception thrown and no sonda deleted
	 */	
	@Test
	public void testSondaDeletionError() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		

		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":1}"));
		
		mockMvc.perform(post("/mars/sonda")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"position\": {\"x\": 3,\"y\": 2},\"heading\": \"W\",\"id\":2}"));
		
		try {
			mockMvc.perform(delete("/mars/deletesonda/{id}", 3))
					.andDo(print())
					.andExpect(status().isBadRequest());
			
			fail("This should have thrown an exception");
		
		} catch (Exception e) {
			
			assertEquals("Request processing failed; nested exception is java.lang.Exception: The sonda with id 3 doesn't exist!", e.getMessage());
			
			assertEquals(Application.sondasMap.size(), 2);
			
		}
		
	}
	
	/**
	 * Tests when an invalid Json is passed as the content
	 * the result should be a Exception thrown
	 */		
	@Test
	public void testInvalidJson() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}")); 
		try {
			mockMvc.perform(post("/mars/sonda")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"positionnn\": {\"x\": 1,\"y\": 2},\"heading\": \"N\",\"id\":1}"));
			
			fail("This should have thrown an exception");
			
		} catch (Exception e){
			assertEquals("Request processing failed; nested exception is java.lang.NullPointerException", e.getMessage());
		}
	}
	
	

}
