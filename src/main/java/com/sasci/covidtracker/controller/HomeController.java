package com.sasci.covidtracker.controller;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sasci.covidtracker.services.CoronaVirusDataService;

@Controller
public class HomeController {
	@Autowired
	private CoronaVirusDataService coronaVirusDataService;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String getCovidStats(Model model) throws IOException {
		
		File file = ResourceUtils.getFile("classpath:DailyIndicators.csv");
		Reader csvFile=new FileReader(file);
		Iterable<CSVRecord> totalRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvFile);
		for (CSVRecord totalRecord : totalRecords) {
			
			model.addAttribute("totalUKCases",totalRecord.get("TotalUKCases"));
			model.addAttribute("newUKCases", totalRecord.get("NewUKCases"));
			model.addAttribute("totalUKDeaths",totalRecord.get("TotalUKDeaths"));
			
		}
		
		model.addAttribute("testHome","Corona Virus Case Tracker");
		
		model.addAttribute("allStats",coronaVirusDataService.getAllStats());
		
		return "home";
	}
}
