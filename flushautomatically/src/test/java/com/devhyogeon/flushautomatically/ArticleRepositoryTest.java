package com.devhyogeon.flushautomatically;

import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Spring Data JPA를 통한 테스트")
    @Rollback(false)
    void delete1() {
        Article article = new Article(); // Transient
        articleRepository.save(article); // Persistent

        Optional<Article> byId = articleRepository.findById(1l);// get By Persistence Context (Not DB)
        byId.get().publish(); // isPublished -> True

        System.out.println("***********************************");
        int delete = articleRepository.deletePublic(); // Execute DELETE Query
        System.out.println("***********************************");

        assertThat(delete).isEqualTo(0);
    }

    @Test
    @DisplayName("JPA(Hibernate)를 통한 테스트 - FlushModeType.AUTO")
    @Rollback(false)
    void delete2() {
        Session session = entityManager.unwrap(Session.class);
        System.out.println("session.getFlushMode() = " + session.getFlushMode());

        Article article = new Article(); // Transient
        session.save(article); // Persist

        Article byId = session.find(Article.class, 1l); // get By Persistence Context (Not DB)
        byId.publish();

        System.out.println("***************************************");
        int delete = session.createQuery("DELETE FROM Article a WHERE a.isPublished = TRUE").executeUpdate();
        System.out.println("***************************************");

        assertThat(delete).isEqualTo(0);
    }

    @Test
    @DisplayName("JPA(Hibernate)를 통한 테스트2 - FlushModeType.COMMIT")
    @Rollback(false)
    void delete3() {
        Session session = entityManager.unwrap(Session.class);
        System.out.println("session.getFlushMode() = " + session.getFlushMode());

        session.setFlushMode(FlushModeType.COMMIT);

        Article article = new Article(); // Transient
        session.save(article); // Persist

        Article byId = session.find(Article.class, 1l); // get By Persistence Context (Not DB)
        byId.publish();

        System.out.println("***************************************");
        int delete = session.createQuery("DELETE FROM Article a WHERE a.isPublished = TRUE").executeUpdate();
        System.out.println("***************************************");

        assertThat(delete).isEqualTo(0);
    }
}