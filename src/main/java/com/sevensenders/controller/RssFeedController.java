package com.sevensenders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sevensenders.pojo.RssFeedResponse;
import com.sevensenders.service.RssFeedService;

@RestController
@RequestMapping("/api")
public class RssFeedController {
	
	@Autowired
	private RssFeedService rssFeedService;

	@GetMapping(value="/generatefeed")
	public ResponseEntity<List<RssFeedResponse>> getComicFeeds(){
		List<RssFeedResponse> rssFeedResponse = rssFeedService.getComicFeeds();
		return new ResponseEntity<>(rssFeedResponse,HttpStatus.OK);
	}
}
