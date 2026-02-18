package es.um.example.demo.steps;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@Component
public class StepHelper {

    private MvcResult mvcResult;
    private RequestPostProcessor jwt;
    private String currentUser;

    private RequestPostProcessor getJWT(String user, String scope) {
        if (user == null || user.isEmpty()) return anonPostProcessor();
        return jwt()
                .jwt(u -> u.subject(user))
                .authorities(new SimpleGrantedAuthority(scope));
    }

    private RequestPostProcessor anonPostProcessor() {
        return new RequestPostProcessor(){
                @Override
                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                    return request;
                }  
        };  
    }

    protected void enableUserToken(String user, String scope) {
        this.currentUser = user;
        this.jwt = getJWT(user,scope);
    }

    protected RequestPostProcessor getUserToken() {
        return this.jwt;
    }

    protected String getCurrentUser() {
        return this.currentUser;
    }

    protected ResultHandler readResponse = result -> this.mvcResult = result;

    protected MvcResult getResponse() {
        return this.mvcResult;
    }

    protected String getResponseBody() throws Exception {
        return this.mvcResult.getResponse().getContentAsString();
    }

    protected int getStatusCode() {
        return this.mvcResult.getResponse().getStatus();
    }
    
}
