package test.club.javalearn.crm.web.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import test.club.javalearn.crm.web.CommonControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-15
 **/
public class UserControllerTest extends CommonControllerTest {
    @Test
    public void testGetUserList() throws Exception{
        String result = mockMvc.perform(get("/user").param("userName","a")).
                andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testUpdateUser() throws Exception{
        String content = "{\"userId\":\"1\", \"userName\":\"tom\",\"password\":null,\"nickName\":\"haha\"}";
        String result = mockMvc.perform(put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }
}
