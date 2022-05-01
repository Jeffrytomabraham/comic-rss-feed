package com.sevensenders.service.handler;

import com.sevensenders.pojo.RssFeedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FeedBurnerHandlerTest {

    private FeedBurnerHandler feedBurnerHandlerUnderTest;

    @BeforeEach
    void setUp() {
        feedBurnerHandlerUnderTest = new FeedBurnerHandler();
    }

    @Test
    void testGetFeedBurnerDetails() {
        ReflectionTestUtils.setField(feedBurnerHandlerUnderTest,"feedburnerUrl","http://feeds.feedburner.com/PoorlyDrawnLines");
        final List<RssFeedResponse> result = feedBurnerHandlerUnderTest.getFeedBurnerDetails();
        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isEqualTo(10);
        assertThat(result.get(0).getPublishingDate()).isNotNull();
        assertThat(result.get(0).getImageUrl()).isNotNull();
        assertThat(result.get(0).getTitle()).isNotNull();
        assertThat(result.get(0).getWebUrl()).isNotNull();
        assertThat(result.get(0).getDescription()).isNotNull();
    }
}
