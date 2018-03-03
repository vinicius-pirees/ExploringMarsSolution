package com.mars.controller;


import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.mars.Application;
import com.mars.model.*;

@RestController
public class SondaController {
	
	@RequestMapping(value = "/mars/sonda", method = RequestMethod.POST)
	public ResponseEntity<Sonda> insertSonda(@RequestBody Sonda sonda) throws Exception{
				
		if(Application.upperRightBoundary == null){
			throw new Exception("The surface's upper-right border wasn't set!");
		}
		
		if (sonda.isActionValid(Application.upperRightBoundary, 
				sonda.getPosition(), Application.surfaceMap)) {
			Application.sondasMap.put(sonda.getID(), sonda);
			Application.surfaceMap.put(sonda.getPosition(), sonda);
			return new ResponseEntity<Sonda>(sonda, HttpStatus.OK);
		} else {
			throw new Exception("The sonda cannot be created!");
		}	
		
	}
	
	@RequestMapping("/mars/allsondas")
    public ResponseEntity<Map<Integer, Sonda>> getAllSondas() {
        return new ResponseEntity<Map<Integer, Sonda>>(Application.sondasMap, HttpStatus.OK);
    }
	
	@RequestMapping("/mars/getsonda")
    public ResponseEntity<Sonda> getSonda(@RequestParam(value="id") Integer id) {
        return new ResponseEntity<Sonda>(Application.sondasMap.get(id), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/mars/movesonda/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Sonda> moveSonda(@PathVariable Integer id, @RequestBody String movements) throws JSONException  {
		JSONObject obj = new JSONObject(movements);
		String moves = obj.getString("movements");
        Sonda sonda = Application.sondasMap.get(id);
        
        
        for (char movement: moves.toCharArray()) {		
			sonda.doAction(String.valueOf(movement), Application.upperRightBoundary, Application.surfaceMap);		
		}
        Application.sondasMap.put(id, sonda);
        return new ResponseEntity<Sonda>(sonda, HttpStatus.OK);
    }

}
