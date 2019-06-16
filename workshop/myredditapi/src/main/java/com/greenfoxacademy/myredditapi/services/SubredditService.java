package com.greenfoxacademy.myredditapi.services;

import com.greenfoxacademy.myredditapi.dto.PostReferenceDTO;
import com.greenfoxacademy.myredditapi.dto.SubredditDTO;
import com.greenfoxacademy.myredditapi.dto.SubredditReferenceDTO;
import com.greenfoxacademy.myredditapi.infrastructure.APIConfiguration;
import com.greenfoxacademy.myredditapi.infrastructure.URLs;
import com.greenfoxacademy.myredditapi.models.Subreddit;
import com.greenfoxacademy.myredditapi.repositories.PostRepository;
import com.greenfoxacademy.myredditapi.repositories.SubredditRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubredditService {
  private SubredditRepository subredditRepository;
  private PostRepository postRepository;
  private UrlProvider urlProvider;

  public SubredditService(SubredditRepository subredditRepository,
                          PostRepository postRepository,
                          UrlProvider urlProvider) {
    this.subredditRepository = subredditRepository;
    this.postRepository = postRepository;
    this.urlProvider = urlProvider;
  }

  public List<SubredditReferenceDTO> listSubreddits() {
    return subredditRepository.findAll()
      .stream()
      .map(s -> new SubredditReferenceDTO(s.getName(), urlProvider.subredditFetchUrl(s), postCount(s)))
      .collect(Collectors.toList());
  }

  public void addSubreddit(SubredditDTO subredditDTO) {
    if (subredditDTO == null || StringUtils.isEmpty(subredditDTO.name)) {
      throw new IllegalArgumentException("subreddit is required");
    }

    Optional<Subreddit> maybeSubreddit = subredditRepository.getOneByName(subredditDTO.name);

    if (maybeSubreddit.isPresent()) {
      throw new IllegalArgumentException("Subreddit already exist: `" + subredditDTO.name + "`");
    }

    Subreddit subreddit = new Subreddit();

    subreddit.setName(subredditDTO.name);

    subredditRepository.save(subreddit);
  }

  public void updateSubreddit(long subredditId, SubredditDTO subredditDTO) {
    if (subredditDTO == null || StringUtils.isEmpty(subredditDTO.name)) {
      throw new IllegalArgumentException("subreddit is required");
    }

    if (subredditId <= 0) {
      throw new IllegalArgumentException("subreddit id is required and must be a positive number, got `" + subredditId + "`");
    }

    Subreddit subreddit = subredditRepository.getOne(subredditId);

    BeanUtils.copyProperties(subredditDTO, subreddit);

    subredditRepository.save(subreddit);
  }

  public void deleteSubreddit(long subredditId) {
    Optional<Subreddit> optionalSubreddit = subredditRepository.findById(subredditId);

    if (!optionalSubreddit.isPresent()) {
      return;
    }

    subredditRepository.deleteById(subredditId);
  }

  public Optional<SubredditDTO> getSubreddit(long subredditId) {
    return subredditRepository.findById(subredditId).map(this::mapToSubredditDTO);
  }

  private SubredditDTO mapToSubredditDTO(Subreddit subreddit) {
    List<PostReferenceDTO> posts = postRepository.findAllIdAndTitleBySubredditId(subreddit)
      .stream()
      .map(ps -> new PostReferenceDTO(ps.getTitle(), urlProvider.postFetchUrl(ps.getId())))
      .collect(Collectors.toList());

    return new SubredditDTO(subreddit.getName(), posts);
  }

  private long postCount(Subreddit subreddit) {
    return postRepository.countBySubreddit(subreddit);
  }
}
