package com.sevensenders.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private XkcdHandler xkcdHandler;

	private static final Logger LOGGER = LoggerFactory.getLogger(RssFeedServiceImpl.class);

	public List<RssFeedResponse> getComicFeeds() {
		LOGGER.info("Inside RssFeedServiceImpl.getComicFeeds()..");
		List<RssFeedResponse> feedResponses = new ArrayList<>();
		generateFeedBurnerDetails(feedResponses);
		generateXkcdDetails(feedResponses);
		return feedResponses.stream()
				.sorted(Comparator.comparing(RssFeedResponse::getPublishingDate).reversed())
			.collect(Collectors.toList());
	}
	
	private void generateFeedBurnerDetails(List<RssFeedResponse> feedResponses) {
		LOGGER.info("Generate feed burner details.");
		feedResponses.addAll(feedBurnerHandler.getFeedBurnerDetails());
	}
	
	private void generateXkcdDetails(List<RssFeedResponse> feedResponses) {
		LOGGER.info("Generate XKCD details.");
		feedResponses.addAll(xkcdHandler.getXkcdDetails());
	}
	
}
