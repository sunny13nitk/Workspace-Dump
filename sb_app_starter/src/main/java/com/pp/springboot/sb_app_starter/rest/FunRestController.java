package com.pp.springboot.sb_app_starter.rest;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController
{
	@Value(
	    "${team.name}"
	)
	private String teamName;
	
	@Value(
	    "${coach.name}"
	)
	private String coachName;
	
	@GetMapping(
	    "/"
	)
	public String helloWorld(
	)
	{
		return "Hello World!  - " + LocalDate.now();
	}
	
	@GetMapping(
	    "/workout"
	)
	public String getWorkout(
	)
	{
		return "Run a hard 5k!";
	}
	
	@GetMapping(
	    "/teaminfo"
	)
	public String getTeamInfo(
	)
	{
		return teamName + " - " + coachName;
	}
}
