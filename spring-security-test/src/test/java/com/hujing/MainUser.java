package com.hujing;

import com.hujing.springsecuritytest.SpringSecurityTestApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author hj
 * @create 2019-05-01 8:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringSecurityTestApplication.class)
public class MainUser {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testQueryTestById() throws Exception {
        String result = mockMvc.perform(get("/test")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testPathVariable() throws Exception {
        String result = mockMvc.perform(get("/test/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("hujing"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testInsertUser() throws Exception {
        LocalDateTime of = LocalDateTime.of(1996, 2, 2, 12, 12, 12);
        String content = "{\"username\": \"h\",\"password\": \"dd\",\"birthday\":\"" + of.toString()+ "\"}";
        String result = mockMvc.perform(post("/test").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }


    @Test
    public void testUpload() throws Exception {
        String file = mockMvc.perform(multipart("/upload")
                .file(new MockMultipartFile("file", "a.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "hello,spring boot".getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(file);
    }

    @Test
    public void testDownload() throws Exception {
        mockMvc.perform(post("/download/1556686696558")
                .contentType("application/x-download").header("Content-Disposition","attachment;filename=test.txt"))
                .andExpect(status().isOk());
    }

}
