package org.ateam.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by OPSKMC on 7/26/15.
 */
@Controller
@SessionAttributes("authorizationRequest")
public class AuthorizationController {

    private ClientDetailsService clientDetailsService;
    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(ModelMap model) throws Exception{
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.remove("authorizationRequest");
        ClientDetails client = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());
        model.put("auth_request", authorizationRequest);
        model.put("client", client);
        return new ModelAndView("access_confirmation", model);
    }

    @RequestMapping(value = "/oauth/oauth_error")
    public String handleError(ModelMap model) throws Exception {
        // We can add more stuff to the model here for JSP rendering.  If the client was a machine then
        // the JSON will already have been rendered.
        model.put("message", "There was a problem with the OAuth2 protocol");
        return "oauth_error";
    }
    @Autowired
    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }
}
