package com.devhyogeon.jpaequality;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(false)
    @DisplayName("JPA 동일성 보장으로 인한 문제")
    void identity1() {
        User user = new User(); //Transient
        user.setName("hyo");

        userRepository.save(user); //Persistent

        System.out.println("***************************************************");
        int update = userRepository.updateName(1l, "geon"); //bulk operation
        assertThat(update).isEqualTo(1);
        System.out.println("***************************************************");

        List<User> users = userRepository.findAll();//SELECT * Query Execution
        System.out.println("Name = " + users.get(0).getName());
    }
}