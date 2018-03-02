package com.mars.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mars.Application;
import com.mars.model.Position;

@RestController
public class SetupController {
	
			
	@RequestMapping(value = "/mars/setup", method = RequestMethod.POST)
	public void setup(@RequestBody Position position){
		
		System.out.println(position.getX());
		System.out.println(position.getY());
		
		Application.upperRightBoundary = position;
	}
	
	
	@RequestMapping("/mars/getsetup")
    public Position getSetup() {
        return Application.upperRightBoundary;
    }
}
