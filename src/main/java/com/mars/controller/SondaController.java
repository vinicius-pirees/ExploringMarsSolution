package com.mars.controller;

import java.util.List;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mars.Application;
import com.mars.model.*;

@RestController
public class SondaController {
	
	@RequestMapping(value = "/mars/sonda", method = RequestMethod.POST)
	public void insertSonda(@RequestBody Sonda sonda){
		
		Application.listOfSondas.add(sonda);
	}
	
	@RequestMapping("/mars/allsondas")
    public List<Sonda> getAllSondas() {
        return Application.listOfSondas;
    }

}
