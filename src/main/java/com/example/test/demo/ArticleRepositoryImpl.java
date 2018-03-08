package com.example.test.demo;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleRepositoryImpl implements ArticleRepository {

    List<Article> Articles;

    @PostConstruct
    public synchronized void init() {

        Articles = new ArrayList<>();
        Articles.add(new Article("nice", "asdasd gfgdafnjh .l", "max power"));
        Articles.add(new Article("sick", "xvbgsnj", "max power"));
        Articles.add(new Article("rest", "afgagnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnm", "john doe"));
        Articles.add(new Article("api", "cccccccccccccccccccccccccccccccccccccccc", "max power"));
        Articles.add(new Article("we", "bfssfbdl", "max power"));
        Articles.add(new Article("got", "fbbd", "max power"));
        Articles.add(new Article("here", "afbfsgggggggs", "nn"));
    }

    @Override
    public Article saveEntity(Article entity) {

        Articles.add(entity);
        return entity;
    }

    @Override
    public void delete(Long aLong) throws IllegalArgumentException {

        Article a = findOne(aLong);

        if(a == null || !Articles.contains(a))
            throw new CannotFindArticleException(aLong);

        Articles.remove(a);
    }

    @Override
    public Iterable<Article> findAll() { return this.Articles; }

    @Override
    public Article findOne(Long aLong) throws IllegalArgumentException {

        Article article = this.Articles.stream().filter(a -> (a.getId() == aLong)).findFirst().orElse(null);

        if(article == null)
            throw new CannotFindArticleException(aLong);

        return article;
    }
}
