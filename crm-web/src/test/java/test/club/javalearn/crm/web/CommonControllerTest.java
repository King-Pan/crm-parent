package test.club.javalearn.crm.web;

import club.javalearn.crm.CrmApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-11-18
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrmApplication.class)
public class CommonControllerTest{
    @Autowired
    protected WebApplicationContext wac;


    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
