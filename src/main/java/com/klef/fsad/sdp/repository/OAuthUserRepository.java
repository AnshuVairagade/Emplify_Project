package com.klef.fsad.sdp.repository;

import com.klef.fsad.sdp.model.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthUserRepository
        extends JpaRepository<OAuthUser,Long> {

    Optional<OAuthUser> findByEmail(String email);

}