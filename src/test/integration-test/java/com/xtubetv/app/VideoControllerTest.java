package com.xtubetv.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by hyg on 2017/7/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
@Sql({"/sql/video/clean.sql", "/sql/video/schema.sql", "/sql/video/data.sql"})
public class VideoControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void index() throws Exception {

        this.mockMvc
                .perform(
                        get("/video")
                                .param("page", "1")
                                .param("size", "5")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createWithoutId() throws Exception {
        this.mockMvc
                .perform(
                        post("/video")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"url\":\"http://a.com\",\"description\":\"new created\"}")
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