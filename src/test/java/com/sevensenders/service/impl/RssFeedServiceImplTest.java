package com.sevensenders.service.impl;

import com.sevensenders.pojo.RssFeedResponse;
import com.sevensenders.service.handler.FeedBurnerHandler;
import com.sevensenders.service.handler.XkcdHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RssFeedServiceImplTest {

    @Mock
    private FeedBurnerHandler mockFeedBurnerHandler;
    @Mock
    private XkcdHandler mockXkcdHandler;

    @InjectMocks
    private RssFeedServiceImpl rssFeedServiceImplUnderTest;

    @Test
    void testGetComicFeeds() {
        final RssFeedResponse rssFeedResponse = new RssFeedResponse();
        rssFeedResponse.setTitle("title");
        rssFeedResponse.setDescription("description");
        rssFeedResponse.setImageUrl("imageUrl");
        rssFeedResponse.setWebUrl("webUrl");
        rssFeedResponse.setPublishingDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<RssFeedResponse> rssFeedResponses = List.of(rssFeedResponse);
        when(mockFeedBurnerHandler.getFeedBurnerDetails()).thenReturn(rssFeedResponses);

        final RssFeedResponse rssFeedResponse1 = new RssFeedResponse();
        rssFeedResponse1.setTitle("title");
        rssFeedResponse1.setDescription("description");
        rssFeedResponse1.setImageUrl("imageUrl");
        rssFeedResponse1.setWebUrl("webUrl");
        rssFeedResponse1.setPublishingDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<RssFeedResponse> rssFeedResponses1 = List.of(rssFeedResponse1);
        when(mockXkcdHandler.getXkcdDetails()).thenReturn(rssFeedResponses1);

        final List<RssFeedResponse> result = rssFeedServiceImplUnderTest.getComicFeeds();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void testGetComicFeeds_FeedBurnerHandlerReturnsNoItems() {

        when(mockFeedBurnerHandler.getFeedBurnerDetails()).thenReturn(Collections.emptyList());

        final RssFeedResponse rssFeedResponse = new RssFeedResponse();
        rssFeedResponse.setTitle("title");
        rssFeedResponse.setDescription("description");
        rssFeedResponse.setImageUrl("imageUrl");
        rssFeedResponse.setWebUrl("webUrl");
        rssFeedResponse.setPublishingDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<RssFeedResponse> rssFeedResponses = List.of(rssFeedResponse);
        when(mockXkcdHandler.getXkcdDetails()).thenReturn(rssFeedResponses);

        final List<RssFeedResponse> result = rssFeedServiceImplUnderTest.getComicFeeds();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testGetComicFeeds_XkcdHandlerReturnsNoItems() {
        final RssFeedResponse rssFeedResponse = new RssFeedResponse();
        rssFeedResponse.setTitle("title");
        rssFeedResponse.setDescription("description");
        rssFeedResponse.setImageUrl("imageUrl");
        rssFeedResponse.setWebUrl("webUrl");
        rssFeedResponse.setPublishingDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<RssFeedResponse> rssFeedResponses = List.of(rssFeedResponse);
        when(mockFeedBurnerHandler.getFeedBurnerDetails()).thenReturn(rssFeedResponses);

        when(mockXkcdHandler.getXkcdDetails()).thenReturn(Collections.emptyList());

        final List<RssFeedResponse> result = rssFeedServiceImplUnderTest.getComicFeeds();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }
}
