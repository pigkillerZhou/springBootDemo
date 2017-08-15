package com.xtubetv.app.interfaces.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.xtubetv.app.domain.video.QVideo;
import com.xtubetv.app.domain.video.Video;
import com.xtubetv.app.domain.video.VideoRepository;
import com.xtubetv.app.interfaces.controller.documentationsupport.WebLayerDocumentationTestBase;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by hyg on 2017/7/29.
 */

@WebMvcTest(VideoController.class)
public class VideoControllerUnitTest extends WebLayerDocumentationTestBase {
    @MockBean
    VideoRepository videoRepository;

    @Before
    @Override
    public void setUp() throws Exception{
        super.setUp();
        final Video video1 = new Video(1L, "url", "des", 0, 0L, 0.0);
        given(this.videoRepository.save(any(Video.class))).willReturn(video1);
        final Video video2 = new Video(107L, "url", "des", 0, 0L, 0.0);
        given(this.videoRepository.findOne(107L)).willReturn(video2);
        given(this.videoRepository.findOne(QVideo.video.id.eq(107L))).willReturn(video2);
        given(this.videoRepository.findAll(any(BooleanExpression.class), any(Pageable.class))).willReturn(new PageImpl<>(asList(video1, video2)));
    }

    @Test
    public void index() throws Exception {
        this.mockMvc
                .perform(
                        get("/video")
                                .param("page", "1")
                                .param("size", "5")
                )
                .andExpect(status().isOk());
    }

    @Test
    public void createWithoutId() throws Exception {
        this.mockMvc
                .perform(
                        post("/video")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"url\":\"http://a.com\",\"description\":\"haaaahaa\"}")
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void read() throws Exception {
        this.mockMvc
                .perform(
                        get("/video/107")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createWithId() throws Exception {
        this.mockMvc
                .perform(
                        put("/video/105?create=true")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"url\":\"http://a.com\",\"description\":\"haaaahaa\"}")
                )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void update() throws Exception {
        this.mockMvc
                .perform(
                        put("/video/105?create=true")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"url\":\"http://a.com\",\"description\":\"abcallkjsf\"}")
                )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void updateNotExists() throws Exception {
        this.mockMvc
                .perform(
                        put("/video/1012341243?create=false")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"url\":\"http://a.com\",\"description\":\"abc\"}")
                )
                .andExpect(status().isNotFound());
    }


    @Test
    public void deleteWithId() throws Exception {
        this.mockMvc
                .perform(
                        delete("/video/105")
                )
                .andExpect(status().isNoContent())
                .andDo(print());

        this.mockMvc
                .perform(
                        get("/video/105")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void increase() throws Exception {
        this.mockMvc
                .perform(
                        post("/video/105/stat")
                )
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}