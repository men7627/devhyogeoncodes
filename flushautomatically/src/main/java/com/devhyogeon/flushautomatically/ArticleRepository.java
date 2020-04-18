package com.devhyogeon.flushautomatically;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Modifying()
    @Query("DELETE FROM Article a WHERE a.isPublished = TRUE")
    int deletePublic();
}
