package com.greenfoxacademy.myredditapi;

import com.greenfoxacademy.myredditapi.dto.PostDTO;
import com.greenfoxacademy.myredditapi.models.Post;
import com.greenfoxacademy.myredditapi.models.Subreddit;
import com.greenfoxacademy.myredditapi.repositories.PostRepository;
import com.greenfoxacademy.myredditapi.repositories.SubredditPostView;
import com.greenfoxacademy.myredditapi.repositories.SubredditRepository;
import com.greenfoxacademy.myredditapi.repositories.UserVoteRepository;
import com.greenfoxacademy.myredditapi.services.PostService;
import com.greenfoxacademy.myredditapi.services.UrlProvider;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

public class PostServiceTest {

  @Test
  public void addPost_shouldAddANewPostCorrectly() {
    // Arrange
    AnotherMockSubredditRepository mockRepository = new AnotherMockSubredditRepository();
    MockPostRepository mockPostRepository = new MockPostRepository();
    UrlProvider mockUrlProvider = Mockito.mock(UrlProvider.class);
    UserVoteRepository mockUserVoteRepository = Mockito.mock(UserVoteRepository.class);
    Clock mockClock = Mockito.mock(Clock.class);

    Instant instant = Instant.parse("2019-10-10T10:00:00.000Z");

    Mockito.when(mockClock.instant()).thenReturn(instant);

    PostService service = new PostService(mockRepository, mockPostRepository, mockUrlProvider, mockUserVoteRepository, mockClock);
    String expectedPostTitle = "Test Post";
    PostDTO postDTO = new PostDTO(1, "Test Post", "http://example.com", 1, "");

    // Act
    service.addPost(postDTO, 1);

    // Assert
    Assert.assertEquals(1, mockPostRepository.saveMethodCalledTimes);
    Assert.assertEquals(expectedPostTitle, mockPostRepository.saveMethodCalledWith.getTitle());
    Assert.assertEquals(instant, mockPostRepository.saveMethodCalledWith.getAddedAt());
  }
}

class MockPostRepository implements PostRepository {

  @Override
  public long countBySubreddit(Subreddit subreddit) {
    return 0;
  }

  @Override
  public List<SubredditPostView> findAllIdAndTitleBySubredditId(Subreddit subreddit) {
    return null;
  }

  @Override
  public List<Post> findAll() {
    return null;
  }

  @Override
  public List<Post> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Post> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Post> findAllById(Iterable<Long> longs) {
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
  public void delete(Post entity) {

  }

  @Override
  public void deleteAll(Iterable<? extends Post> entities) {

  }

  @Override
  public void deleteAll() {

  }

  public boolean saveMethodCalled = false;
  public int saveMethodCalledTimes = 0;
  public Post saveMethodCalledWith;

  @Override
  public <S extends Post> S save(S entity) {
    saveMethodCalled = true;
    saveMethodCalledTimes++;
    saveMethodCalledWith = entity;
    return null;
  }

  @Override
  public <S extends Post> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<Post> findById(Long aLong) {
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
  public <S extends Post> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Post> entities) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Post getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Post> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Post> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Post> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Post> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Post> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Post> boolean exists(Example<S> example) {
    return false;
  }
}

class AnotherMockSubredditRepository implements SubredditRepository {

  @Override
  public Optional<Subreddit> getOneByName(String name) {
    return Optional.empty();
  }

  @Override
  public List<Subreddit> findAll() {
    return null;
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

  public boolean saveMethodCalled = false;
  public int saveMethodCalledTimes = 0;
  public Subreddit saveMethodCalledWith;

  @Override
  public <S extends Subreddit> S save(S entity) {
    saveMethodCalled = true;
    saveMethodCalledTimes++;
    saveMethodCalledWith = entity;
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
    return new Subreddit("Test Subreddit");
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
