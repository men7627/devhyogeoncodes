package com.devhyogeon.clearautomatically;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @Rollback(false)
    @DisplayName("clearAutomatically 테스트")
    void update() {
        Article article = new Article(); // Transient
        article.setTitle("before");
        articleRepository.save(article); // Persistent

        System.out.println("************************************");
        int result = articleRepository.updateTitle(1l, "after");
        System.out.println("************************************");

        System.out.println(articleRepository.findById(1l).get().getTitle());
    }
}