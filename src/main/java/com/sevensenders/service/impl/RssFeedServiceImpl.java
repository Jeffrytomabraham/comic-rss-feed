package com.sevensenders.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensenders.pojo.RssFeedResponse;
import com.sevensenders.service.RssFeedService;
import com.sevensenders.service.handler.FeedBurnerHandler;

@Service
public class RssFeedServiceImpl implements RssFeedService {

	@Autowired
	private FeedBurnerHandler feedBurnerHandler;

	public List<RssFeedResponse> getComicFeeds() {
		List<RssFeedResponse> feedResponses = new ArrayList<>();
		generateFeedBurnerDetails(feedResponses);
		return feedResponses;
	}
	
	private void generateFeedBurnerDetails(List<RssFeedResponse> feedResponses) {
		feedResponses.addAll(feedBurnerHandler.getFeedBurnerDetails());
	}
	
}
