package com.sevensenders.service.handler;

import com.sevensenders.pojo.RssFeedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class XkcdHandlerTest {

    private XkcdHandler xkcdHandlerUnderTest;

    @BeforeEach
    void setUp() {
        xkcdHandlerUnderTest = new XkcdHandler();
    }

    @Test
    void testGetXkcdDetails() {
        ReflectionTestUtils.setField(xkcdHandlerUnderTest,"xkcdUrlTemplate","https://xkcd.com/%sinfo.0.json");
        final List<RssFeedResponse> result = xkcdHandlerUnderTest.getXkcdDetails();
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(10);
        assertThat(result.get(0).getPublishingDate()).isNotNull();
        assertThat(result.get(0).getImageUrl()).isNotNull();
        assertThat(result.get(0).getTitle()).isNotNull();
        assertThat(result.get(0).getWebUrl()).isNotNull();
        assertThat(result.get(0).getDescription()).isNotNull();
    }
}
