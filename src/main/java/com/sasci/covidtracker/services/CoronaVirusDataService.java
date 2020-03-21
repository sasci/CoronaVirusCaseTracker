package com.sasci.covidtracker.services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.sasci.covidtracker.models.LocationStats;

@Service
public class CoronaVirusDataService {
	private static String DATAURI="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/"
			+ "csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	
	private List<LocationStats> allStats=new ArrayList<>();
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}

	@PostConstruct
	@Scheduled(cron="* * 1 * * *")
	public void fetchConfirmedCases() throws IOException, InterruptedException {
		List<LocationStats> newStats=new ArrayList<>();
		
		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(DATAURI)).build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString()); 
		//System.out.println(httpResponse.body());
		
		StringReader csvReader= new StringReader(httpResponse.body());
				
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
		for (CSVRecord record : records) {
			LocationStats locationStat=new LocationStats();
			locationStat.setState(record.get("Province/State"));
			locationStat.setCountry(record.get("Country/Region"));
			locationStat.setNumberOfCases(Integer.parseInt(record.get(record.size()-1)));
			newStats.add(locationStat);
		}		
		this.allStats=newStats;
		
	}
}
