package com.sevensenders.controller;

import com.sevensenders.pojo.RssFeedResponse;
import com.sevensenders.service.RssFeedService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RssFeedController.class)
class RssFeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RssFeedService mockRssFeedService;

    @Test
    void testGetComicFeeds() throws Exception {
        final RssFeedResponse rssFeedResponse = new RssFeedResponse();
        rssFeedResponse.setTitle("title");
        rssFeedResponse.setDescription("description");
        rssFeedResponse.setImageUrl("imageUrl");
        rssFeedResponse.setWebUrl("webUrl");
        rssFeedResponse.setPublishingDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<RssFeedResponse> rssFeedResponses = List.of(rssFeedResponse);
        when(mockRssFeedService.getComicFeeds()).thenReturn(rssFeedResponses);

        final MockHttpServletResponse response = mockMvc.perform(get("/api/generatefeed")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"title\":\"title\",\"description\":\"description\",\"imageUrl\":\"imageUrl\",\"webUrl\":\"webUrl\",\"publishingDate\":\"2020-01-01T00:00:00\"}]");
    }

    @Test
    void testGetComicFeeds_RssFeedServiceReturnsNoItems() throws Exception {
        when(mockRssFeedService.getComicFeeds()).thenReturn(Collections.emptyList());

        final MockHttpServletResponse response = mockMvc.perform(get("/api/generatefeed")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
