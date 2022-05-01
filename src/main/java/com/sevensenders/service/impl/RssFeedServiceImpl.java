package com.sevensenders.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensenders.pojo.RssFeedResponse;
import com.sevensenders.service.RssFeedService;
import com.sevensenders.service.handler.FeedBurnerHandler;
import com.sevensenders.service.handler.XkcdHandler;

@Service
public class RssFeedServiceImpl implements RssFeedService {

	@Autowired
	private FeedBurnerHandler feedBurnerHandler;
	
	@Autowired
	private XkcdHandler XkcdHandler;

	public List<RssFeedResponse> getComicFeeds() {
		List<RssFeedResponse> feedResponses = new ArrayList<>();
		generateFeedBurnerDetails(feedResponses);
		generateXkcdDetails(feedResponses);
		List<RssFeedResponse> sortedResponse = feedResponses.stream()
				.sorted(Comparator.comparing(RssFeedResponse::getPublishingDate).reversed())
			.collect(Collectors.toList());
		return sortedResponse;
	}
	
	private void generateFeedBurnerDetails(List<RssFeedResponse> feedResponses) {
		feedResponses.addAll(feedBurnerHandler.getFeedBurnerDetails());
	}
	
	private void generateXkcdDetails(List<RssFeedResponse> feedResponses) {
		feedResponses.addAll(XkcdHandler.getXkcdDetails());
	}
	
}
