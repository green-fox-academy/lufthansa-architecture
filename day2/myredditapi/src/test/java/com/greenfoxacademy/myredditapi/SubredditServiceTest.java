package com.greenfoxacademy.myredditapi;

import com.greenfoxacademy.myredditapi.dto.SubredditReferenceDTO;
import com.greenfoxacademy.myredditapi.models.Subreddit;
import com.greenfoxacademy.myredditapi.repositories.PostRepository;
import com.greenfoxacademy.myredditapi.repositories.SubredditRepository;
import com.greenfoxacademy.myredditapi.services.SubredditService;
import com.greenfoxacademy.myredditapi.services.UrlProvider;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class SubredditServiceTest {
  @Test
  public void listSubredditsShouldReturnValidResult() {
    // Arrange
    SubredditRepository mockSubredditRepository = Mockito.mock(SubredditRepository.class);
    Mockito.when(mockSubredditRepository.findAll()).thenReturn(Arrays.asList(
            new Subreddit("test 1"),
            new Subreddit("test 2"),
            new Subreddit("test 3")
    ));

    PostRepository mockPostRepository = Mockito.mock(PostRepository.class);
    Mockito.when(mockPostRepository.countBySubreddit(any())).thenReturn(2L);

    UrlProvider mockUrlProvider = Mockito.mock(UrlProvider.class);
    SubredditService subredditService = new SubredditService(mockSubredditRepository, mockPostRepository, mockUrlProvider);

    // Act
    List<SubredditReferenceDTO> result = subredditService.listSubreddits();

    // Assert
    assertEquals(3, result.size());
    assertEquals(2, result.get(0).postCount);
  }
}

class MockSubredditRepository implements SubredditRepository {

  @Override
  public List<Subreddit> findAll() {
    return Arrays.asList(
            new Subreddit("test 1"),
            new Subreddit("test 2"),
            new Subreddit("test 3")
    );
  }

  @Override
  public Optional<Subreddit> getOneByName(String name) {
    return Optional.empty();
  }



  @Override
  public List<Subreddit> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Subreddit> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Subreddit> findAllById(Iterable<Long> longs) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Long aLong) {

  }

  @Override
  public void delete(Subreddit entity) {

  }

  @Override
  public void deleteAll(Iterable<? extends Subreddit> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public <S extends Subreddit> S save(S entity) {
    return null;
  }

  @Override
  public <S extends Subreddit> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<Subreddit> findById(Long aLong) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Subreddit> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Subreddit> entities) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Subreddit getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Subreddit> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Subreddit> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Subreddit> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Subreddit> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Subreddit> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Subreddit> boolean exists(Example<S> example) {
    return false;
  }
}
