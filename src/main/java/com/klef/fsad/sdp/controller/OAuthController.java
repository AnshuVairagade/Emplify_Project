package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.model.OAuthUser;
import com.klef.fsad.sdp.repository.OAuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuthUserRepository repository;


    @GetMapping("/verify")
    public OAuthUser verify(OAuth2AuthenticationToken token) {

        OAuth2User user = token.getPrincipal();

        String email = user.getAttribute("email");

        String name = user.getAttribute("name");

        String oauthId = user.getAttribute("sub");

        OAuthUser oauthUser =
                repository.findByEmail(email)
                        .orElseGet(() -> {
                            OAuthUser u = new OAuthUser();

                            u.setEmail(email);
                            u.setName(name);
                            u.setOauthProvider("GOOGLE");
                            u.setOauthId(oauthId);

                            return repository.save(u);
                        });

        return oauthUser;
    }

}


/*  Endpoints:

http://localhost:8080/oauth/login

http://localhost:8080/oauth/verify

 */
