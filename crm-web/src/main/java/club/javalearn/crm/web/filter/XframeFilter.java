package club.javalearn.crm.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-10
 **/
@WebFilter(filterName = "xFrameFilter",urlPatterns = "/*")
@Slf4j
public class XframeFilter  implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //log.info("初始化XframeFilter配置");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //log.info("设置请求头x-frame-options='SAMEORIGIN'");
        //HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //httpServletResponse.addHeader("x-frame-options","SAMEORIGIN");

        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        log.info("销毁XframeFilter");
    }
}
