package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.PortalUser;
import com.panunited.airdemo.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PortalUserRepository extends JpaRepository<PortalUser, Long> {
    @Query(value = "SELECT U.* FROM USERS U INNER JOIN PORTAL_USER PU ON U.ID = PU.USER_ID WHERE U.USERNAME = :username", nativeQuery = true)
    Optional<Users> findUsersByUsername(@Param("username") String userName);

    @Query(value = "SELECT PU.* FROM USERS U INNER JOIN PORTAL_USER PU ON U.ID = PU.USER_ID WHERE PU.USER_ID = :userId", nativeQuery = true)
    Optional<PortalUser> findPortalUser(@Param("userId") long userId);

}
