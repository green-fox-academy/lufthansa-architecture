package com.greenfoxacademy.terribleapp.repositories;

import com.greenfoxacademy.terribleapp.models.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;

@Repository
public class PostRepository {

  private EntityManager entityManager;

  public PostRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Collection<Post> findAllPosts() {
    Query query = entityManager.createQuery("SELECT post from Post post");
    return query.getResultList();
  }

}
