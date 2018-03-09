package com.mars.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

public class SetupControllerTest extends ExploringMarsTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		Application.upperRightBoundary = null;
	}
	
	/**
	 * Tests the setup of the upper right boundary
	 * The result shoudl be the upper right coordinate returned as Json
	 */	
	@Test
	public void testSetupBoundary() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.x").value("5"))
			.andExpect(jsonPath("$.y").value("5"))
			.andExpect(content().contentType("application/json;charset=UTF-8"));
		
		assertEquals(Application.upperRightBoundary, new Position(5,5));

	}
	
	/**
	 * Tests the retrieval of the current upper right boundary
	 * The result shoudl be the upper right coordinate returned as Json
	 */	
	@Test
	public void testGetBoundary() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}"));
		
		mockMvc.perform(get("/mars/getsetup"))
					.andExpect(status().isOk())
					.andExpect(content().contentType("application/json;charset=UTF-8"))
					.andExpect(jsonPath("$.x").value("5"))
					.andExpect(jsonPath("$.y").value("5"));

	}

	/**
	 * Tests the boundary can't be reset once it was set
	 * The result should be an Exception thrown
	 */	
	@Test
	public void testNoOtherBoundary() throws Exception {
		mockMvc.perform(post("/mars/setup")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"x\": 5,\"y\": 5}"));
		
		try {
			
			mockMvc.perform(post("/mars/setup")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"x\": 6,\"y\": 6}"));
			
			fail("This should have thrown an exception");
			
		} catch (Exception e){
			assertEquals("Request processing failed; nested exception is java.lang.Exception: The surface's upper-right border was already set!", e.getMessage());
		}
			
		
		assertEquals(Application.upperRightBoundary, new Position(5,5));

	}

}
