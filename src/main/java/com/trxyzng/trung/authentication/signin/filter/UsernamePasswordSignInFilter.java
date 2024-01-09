package com.trxyzng.trung.authentication.signin.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trxyzng.trung.utility.JsonUtils;
import com.trxyzng.trung.utility.servlet.CachedBodyHttpServletRequest;
import com.trxyzng.trung.utility.BeanUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.*;

//@Configurable
//@ComponentScan
//@EnableSpringConfigured
//@EnableLoadTimeWeaving(aspectjWeaving = EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
public class UsernamePasswordSignInFilter extends OncePerRequestFilter {
//    @Autowired
    AuthenticationManager userPasswordAuthenticationManager = BeanUtils.getBean(AuthenticationManager.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NullPointerException {
        try {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                    new CachedBodyHttpServletRequest(request);
            String body = readRequestBody(cachedBodyHttpServletRequest);
                System.out.println("Body of request: " + body);
                JsonNode jsonNode = JsonUtils.getJsonObject(body);
                    String user = JsonUtils.readJsonProperty(jsonNode, "username");
                    String password = JsonUtils.readJsonProperty(jsonNode, "password");
                    System.out.println(user);
                    System.out.println(password);
                    System.out.println("Authentication manager "+this.userPasswordAuthenticationManager);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(user, password);
                    Authentication authentication = this.userPasswordAuthenticationManager
                            .authenticate(authenticationToken);
                    if (authentication != null){
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("Successfully authenticate user");
                    }
                    else {
                        System.out.println("Can not authenticate user");
                    }
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
        } catch (AuthenticationException e) {
            System.out.println("Error authenticate user using username password username password filter");
//            System.out.println(e);
            response.sendError(401, "Error authenticate user using username password");
        }
    }

    public String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            return requestBody.toString();
        }
        catch (IOException e) {
            System.out.println("Error read buffer");
        }
        return "";
    }
}