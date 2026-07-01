package com.klef.fsad.sdp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="oauth_user")
public class OAuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthProvider;

    private String oauthId;

    private String email;

    private String name;

    private String pictureUrl;

    // getters setters


}