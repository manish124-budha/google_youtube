package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private String reason;
  private boolean flagged =false;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  public String toString(){
    String tags = this.tags.toString();
    tags = tags.replace(",", "");
    if(!flagged) {
      return (getTitle() + " (" + getVideoId() + ") " + tags);
    }
    else{
      return (getTitle() + " (" + getVideoId() + ") " + tags+" - FLAGGED"+getReason());
    }
  }

  public String getReason() {
    return reason;
  }

  public void flag(String reason) {
    flagged=true;
    if (reason == ""){
      this.reason = " (reason: Not supplied)";
    }
    else {
      this.reason = " (reason: " + reason+")";
    }
  }

  public boolean isFlagged() {
    return flagged;
  }

  public void unflag() {
    flagged = false;
  }
}
