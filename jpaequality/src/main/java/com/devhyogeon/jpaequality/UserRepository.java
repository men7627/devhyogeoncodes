package com.devhyogeon.jpaequality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.name = ?2 WHERE u.id =?1")
    int updateName(Long id, String name);
}
