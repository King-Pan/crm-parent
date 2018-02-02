package test.club.javalearn.crm.web.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import test.club.javalearn.crm.web.CommonControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-16
 **/
public class RoleControllerTest extends CommonControllerTest {

    @Test
    public void testGetUserList() throws Exception{
        String result = mockMvc.perform(get("/role").param("roleName","a")).
                andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testCreateRole() throws Exception{
        String content = "{\"roleId\":\"1\", \"roleName\":\"超级管理员\",\"roleDesc\":\"上帝\",\"status\":\"1\"}";

        String result = mockMvc.perform(post("/role").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testUpdateRole() throws Exception{
        String content = "{\"roleId\":\"1\", \"roleName\":\"超级管理员\",\"roleDesc\":\"我的天啊\",\"status\":\"1\"}";
        String result = mockMvc.perform(put("/role/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testDeleteUser() throws Exception{
        mockMvc.perform(delete("/role/2").contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

}
