package fon.silab.web.an.ainmusicshop.accessControll;

import fon.silab.web.an.ainmusicshop.dto.UserDto;
import fon.silab.web.an.ainmusicshop.entity.UserEntity;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;



@WebFilter(filterName = "admins", urlPatterns = { "/admin/*" })
public class forNonAdmins implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        UserDto pom = (UserDto) req.getSession().getAttribute("loginUser");
        if (pom == null || pom.getRoleUser() != UserEntity.UserRole.ROLE_ADMIN){
            res.sendRedirect(req.getContextPath()+ "/");
            return;
            
        }
       

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
}
