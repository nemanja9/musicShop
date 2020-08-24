package fon.silab.web.an.ainmusicshop.accessControll;

import fon.silab.web.an.ainmusicshop.dto.UserDto;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
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
import org.springframework.web.servlet.FlashMap;

@WebFilter(filterName = "cartCheckout", urlPatterns = {"/PayPal/*",})
public class forEmptyAdressCheckoutCart implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (req.getSession().getAttribute("loginUser") != null) {
            if (((UserDto) req.getSession().getAttribute("loginUser")).getAdress() == null || ((UserDto) req.getSession().getAttribute("loginUser")).getAdress().length() == 0
                    || ((UserDto) req.getSession().getAttribute("loginUser")).getAdress() == null || ((UserDto) req.getSession().getAttribute("loginUser")).getAdress().length() == 0
                    || ((UserDto) req.getSession().getAttribute("loginUser")).getCity() == null || ((UserDto) req.getSession().getAttribute("loginUser")).getCity().length() == 0
                    || ((UserDto) req.getSession().getAttribute("loginUser")).getZip() == null || ((UserDto) req.getSession().getAttribute("loginUser")).getZip().length() == 0
                    || ((UserDto) req.getSession().getAttribute("loginUser")).getFirstname() == null || ((UserDto) req.getSession().getAttribute("loginUser")).getFirstname().length() == 0
                    || ((UserDto) req.getSession().getAttribute("loginUser")).getLastname() == null || ((UserDto) req.getSession().getAttribute("loginUser")).getLastname().length() == 0) {

                        req.getSession().setAttribute("nedostajuciPodaci", "Niste popunili licne podatke!");
                                        res.sendRedirect(req.getContextPath()+ "/user/profile");
                                        return;

        
        
}

            }
            
                    chain.doFilter(request, response);

            
        }
       

    

    @Override
    public void destroy() {
    }

}
