package test.club.javalearn.crm.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import test.club.javalearn.crm.web.CommonControllerTest;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-11-18
 **/
public class HomeControllerTest extends CommonControllerTest {

    @Test
    public void testHome() throws Exception{
        mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }


}
