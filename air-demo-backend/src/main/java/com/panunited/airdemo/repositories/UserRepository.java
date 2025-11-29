package com.panunited.airdemo.repositories;

import com.panunited.airdemo.enums.Role;
import com.panunited.airdemo.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Modifying
    @Query(value = """
			UPDATE USERS SET LAST_LOGIN_TIME = CURRENT_TIMESTAMP WHERE ID = :userId
			""", nativeQuery = true)
    void updateUserLastLoginTime(@Param("userId") long userId);

    @Query(value = """
			SELECT UR.ROLE FROM USERS U INNER JOIN USER_ROLE UR ON U.ID = UR.USER_ID
			WHERE U.ID = :userId AND U.IS_ACTIVE = 1
			""", nativeQuery = true)
    Set<Role> getRoles(@Param("userId") long userId);
}
