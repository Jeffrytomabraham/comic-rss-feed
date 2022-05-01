package com.sevensenders.pojo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RssFeedResponse {

	private String title;
	private String description;
	private String imageUrl;
	private String webUrl;
	private LocalDateTime publishingDate;
	
}
