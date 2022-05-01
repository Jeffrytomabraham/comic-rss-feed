package com.sevensenders.service.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sevensenders.pojo.RssFeedResponse;

@Component
public class FeedBurnerHandler {

	@Value("${feedburner.url}")
	private String feedburnerUrl;
	
	public List<RssFeedResponse> getFeedBurnerDetails() {
		List<RssFeedResponse> feedResponses = new ArrayList<>();
		Document doc = null;
		try {
			doc = Jsoup.connect(feedburnerUrl).get();
			Elements itemElements = doc.select("item");
			if(itemElements!=null)
				parseHtmlDetails(feedResponses, itemElements);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return feedResponses;
	}
	
	private void parseHtmlDetails(List<RssFeedResponse> feedResponses,  Elements itemElements) {
		itemElements.stream().limit(10).forEach(element -> {
			RssFeedResponse rssFeedResponse = new RssFeedResponse();
			String title = element.selectFirst("title")!=null ? element.selectFirst("title").html() : null;
			String description = element.selectFirst("description")!=null ? element.selectFirst("description").html() : null;
			String webUrl = element.selectFirst("link")!=null ? element.selectFirst("link").html() : null;
			String pubDateString = element.selectFirst("pubDate")!=null ? element.selectFirst("pubDate").html() : null;
			if(pubDateString!=null) {
				DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss Z");
				rssFeedResponse.setPublishingDate(LocalDateTime.parse(pubDateString, inputFormat));
			}
			rssFeedResponse.setTitle(title);
			rssFeedResponse.setDescription(description);
			rssFeedResponse.setWebUrl(webUrl);
			rssFeedResponse.setImageUrl(getImageUrl(element));
			feedResponses.add(rssFeedResponse);
		});
	}

	private String getImageUrl(Element content) {
		Document contentDocument = Jsoup.parseBodyFragment(content.data());
		Element imageElement = contentDocument.selectFirst("figure");
		Element imgUrl = imageElement.selectFirst("img");
		if(imgUrl.attr("src")!=null) {
			return imgUrl.attr("src");
		}
		return null;
	}
}
