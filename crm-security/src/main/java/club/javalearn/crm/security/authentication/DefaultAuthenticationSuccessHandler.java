package club.javalearn.crm.security.authentication;

import club.javalearn.crm.security.core.LoginType;
import club.javalearn.crm.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-04
 **/
@Component("defaultAuthenticationSuccessHandler")
@Slf4j
public class DefaultAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if(log.isInfoEnabled()){
            log.info("{},登录成功",authentication.getPrincipal());
        }
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else{
            super.onAuthenticationSuccess(request,response,authentication);
        }

    }
}
