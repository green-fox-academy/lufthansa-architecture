package com.greenfoxacademy.myredditapi.models;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum UserVoteDirection {
  UP(1),
  DOWN(-1);

  private final int value;

  UserVoteDirection(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  private static final Map<Integer, UserVoteDirection> map;

  static {
    map = Arrays.stream(values())
      .collect(Collectors.toMap(e -> e.value, e -> e));
  }

  public static UserVoteDirection fromInt(int value) {
    return map.get(value);
  }
}
