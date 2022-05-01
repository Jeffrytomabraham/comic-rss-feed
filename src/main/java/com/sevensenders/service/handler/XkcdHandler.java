package com.sevensenders.service.handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sevensenders.pojo.RssFeedResponse;

@Component
public class XkcdHandler {

	@Value("${xkcd.comics.urltemplate}")
	private String xkcdUrlTemplate;
	
	public List<RssFeedResponse> getXkcdDetails(){
		List<RssFeedResponse> feedResponses = new ArrayList<>();
		try {
			IntStream.rangeClosed(0, 9).forEach(in -> {
				String urlString;
				if(in==0) 
					urlString = String.format(xkcdUrlTemplate, ""); 
				else
					urlString = String.format(xkcdUrlTemplate, in+"/"); 
				JsonObject jsonData = getJsonObjectFromXkcd(urlString);
				populateComicDetails(jsonData,feedResponses);
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
		return feedResponses;
	}
	
	private void populateComicDetails(JsonObject jsonData,List<RssFeedResponse> feedResponses) {
		RssFeedResponse rssFeedResponse = new RssFeedResponse();
		rssFeedResponse.setTitle(jsonData.get("title")!=null ? jsonData.get("title").getAsString() : null);
		rssFeedResponse.setDescription(jsonData.get("alt")!=null ? jsonData.get("alt").getAsString() : null);
		rssFeedResponse.setWebUrl(jsonData.get("link")!=null ? jsonData.get("link").getAsString() : null);
		rssFeedResponse.setImageUrl(jsonData.get("img")!=null ? jsonData.get("img").getAsString() : null);
		rssFeedResponse.setPublishingDate(generateDateFromJson(jsonData));
		feedResponses.add(rssFeedResponse);
	}
	
	private LocalDateTime generateDateFromJson(JsonObject jsonData) {
		int day = jsonData.get("day")!=null ? jsonData.get("day").getAsInt() : 0;
		int month = jsonData.get("month")!=null ? jsonData.get("month").getAsInt() : 0;
		int year = jsonData.get("year")!=null ? jsonData.get("year").getAsInt() : 0;
		LocalDateTime pubDate = null;
		if(day>0 && month>0 && year>0) {
			pubDate = LocalDateTime.of(year, month, day, 0, 0, 0);
		}
		return pubDate;
	}
	
	private JsonObject getJsonObjectFromXkcd(String urlString) {
		InputStreamReader reader = null;
		JsonObject jsonData = null;
	    try {
	      URL url = new URL(urlString);
	      reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
	      jsonData = new Gson().fromJson(reader, JsonObject.class);
	    } catch(Exception ex){
	    	
	    }finally {
	      try {
	    	if(reader!=null)
	    		reader.close();
		  } catch (IOException e) {
			e.printStackTrace();
		  }
	    }
	    return jsonData;
	}
}
