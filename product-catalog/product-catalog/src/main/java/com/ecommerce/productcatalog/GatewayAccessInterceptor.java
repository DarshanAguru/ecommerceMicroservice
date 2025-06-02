package com.ecommerce.productcatalog;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GatewayAccessInterceptor implements HandlerInterceptor {

    private static final String GATEWAY_KEY = "my-secret-key";

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws  Exception
    {
        try {
            PasswordEncoder encoder = new BCryptPasswordEncoder();


            String header = req.getHeader("X-Gateway-Key");
            try {
                String otherHeader = req.getHeader("X-InterService-Id");
                if (otherHeader.equals("payment-service")) {
                    return true;
                }
            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }


            if (!encoder.matches(GATEWAY_KEY, header)) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("Unauthorized access");
                return false;
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
