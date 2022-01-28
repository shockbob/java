package com.bob.spring.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcWebClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcExtensionsKt;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(Controller.class)
class DemoApplicationTests {

    @Autowired
    private MockMvc mvc;
    @Test
    void testValidateSucceeds() throws Exception {
        mvc.perform(get("/validate?hand=2C,3C,4S,5S,6S")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testValidateFails() throws Exception {
        mvc.perform(get("/validate?hand=XC,3C,4S,5S,6S")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCompareSucceeds() throws Exception {
        MvcResult result = mvc.perform(get("/compare?hand1=6C,6D,6H,9C,KD&hand2=2C,3C,4S,5S,6S")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(result.getResponse().getContentAsString(),"2C,3C,4S,5S,6S");

    }

}
