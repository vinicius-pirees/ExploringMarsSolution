package com.mars.controller;


import org.springframework.http.HttpStatus;
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
	public ResponseEntity<Position> setup(@RequestBody Position position) throws Exception {
		if(Application.upperRightBoundary != null){
			throw new Exception("The surface's upper-right border was already set!");
		}else{
			Application.upperRightBoundary = position;
			return new ResponseEntity<Position>(position, HttpStatus.OK);
		}
		
	}

	
	
	@RequestMapping("/mars/getsetup")
    public Position getSetup() {
        return Application.upperRightBoundary;
    }
	
	
}
