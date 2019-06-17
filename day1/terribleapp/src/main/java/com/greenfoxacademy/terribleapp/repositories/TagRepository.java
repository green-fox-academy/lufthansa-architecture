package com.greenfoxacademy.terribleapp.repositories;

import com.greenfoxacademy.terribleapp.models.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class TagRepository {

  private EntityManager entityManager;

  public TagRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<Tag> findAllTags() {
    Query tagQuery = entityManager.createQuery("SELECT tag from Tag tag");

    return ((Stream<Tag>) tagQuery.getResultStream().map(Tag.class::cast)).collect(Collectors.toList());
  }

}
