package com.microsoft.aad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.aad.Service.CustomUserDetailsService;
import com.microsoft.aad.Service.UserService;
import com.microsoft.aad.dto.User;
import com.microsoft.aad.dto.ActiveDirectoryUser;
import com.microsoft.aad.util.AuthHelper;
import com.microsoft.aad.util.HttpClientHelper;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.util.GetPropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author MehmetAlpGuler
 */

@Controller
@RequestMapping("/loginAad")
public class ActiveDirectoryController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private GetPropertyValues getPropertyValues;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public String getDirectoryObjects(ModelMap model, HttpServletRequest httpRequest) {

        HttpSession session = httpRequest.getSession();
        AuthenticationResult result = (AuthenticationResult) session.getAttribute(AuthHelper.PRINCIPAL_SESSION_NAME);
        if (result == null) {
            model.addAttribute("error", new Exception("AuthenticationResult not found in session."));
            return "/WEB-INF/error.jsp";
        } else {
            ActiveDirectoryUser activeDirectoryUser;
            try {
                String tenant =getPropertyValues.getProperties("azure.active.directory.tenant");
                activeDirectoryUser = getUsernamesFromGraph(result.getAccessToken(), tenant);
                model.addAttribute("tenant", tenant);
                model.addAttribute("users", activeDirectoryUser.toString());
                model.addAttribute("userInfo", result.getUserInfo());

                loadUserWithAad(httpRequest, session, activeDirectoryUser.getMail());

            } catch (Exception e) {
                model.addAttribute("error", e);
                return "/WEB-INF/error.jsp";
            }
        }
        return "redirect:/secure/mypage";
    }

    private ActiveDirectoryUser getUsernamesFromGraph(String accessToken, String tenant) throws Exception {
        URL url = new URL("https://graph.microsoft.com/v1.0/me");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Accept","application/json");
        int httpResponseCode = conn.getResponseCode();

        String goodRespStr = HttpClientHelper.getResponseStringFromConn(conn, true);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(goodRespStr.toString(), ActiveDirectoryUser.class);
    }

    private void loadUserWithAad(HttpServletRequest httpRequest, HttpSession session,  String email){
        User user = userService.findByMail(email);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        // Create a new session and add the security context.
        session = httpRequest.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }
}
