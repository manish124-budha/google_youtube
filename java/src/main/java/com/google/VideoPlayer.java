package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video playingVideo=null;
  private boolean paused=false;
  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public ArrayList<Video> videosNoFlag(){
    ArrayList<Video> videosNoFlag = new ArrayList<>();
    for (Video v:videoLibrary.getVideos()) {
      if(!v.isFlagged()){
        videosNoFlag.add(v);
      }
    }
    return videosNoFlag;
  }
  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    ArrayList<Video> allVideos= new ArrayList<>(videoLibrary.getVideos());
    allVideos.sort(Comparator.comparing(Video::getTitle));
    for (Video x : allVideos){
      System.out.println(x.toString());
    }

  }

  public void playVideo(String videoId) {
    Video thisVideo = videoLibrary.getVideo(videoId);
    if(thisVideo==null){
      System.out.println("Cannot play video: Video does not exist");
    }
    else {
      if(thisVideo.isFlagged()){
        System.out.println("Cannot play video: "+showFlaggedReason(thisVideo));
        return;
      }
      if (playingVideo == null) {
        System.out.println("Playing video: " + thisVideo.getTitle());
      } else {
        System.out.println("Stopping video: "+ playingVideo.getTitle());
        System.out.println("Playing video: "+ thisVideo.getTitle());
      }
      playingVideo = thisVideo;
      paused=false;
    }
  }

  public void stopVideo() {
    if (playingVideo==null){
      System.out.println("Cannot stop video: No video is currently playing");
    }
    else{
      System.out.println("Stopping video: "+playingVideo.getTitle());
      playingVideo=null;
      paused=false;
    }
  }

  public void playRandomVideo() {
    ArrayList<Video> notFlagedVideos = new ArrayList<>(videosNoFlag());
    if(notFlagedVideos.size()==0){
      System.out.println("No videos available");
      return;
    }
    if (playingVideo!=null){
      System.out.println("Stopping video: "+playingVideo.getTitle());
    }
    int rand =(int) Math.random()* notFlagedVideos.size()+0;
    playingVideo = notFlagedVideos.get(rand);
    System.out.println("Playing video: "+playingVideo.getTitle());
  }

  public void pauseVideo() {
    if(playingVideo==null){
      System.out.println("Cannot pause video: No video is currently playing");
    }
    else{
      if(paused){
        System.out.println("Video already paused: "+ playingVideo.getTitle());
      }
      else{
        System.out.println("Pausing video: "+playingVideo.getTitle());
        paused=true;
      }
    }
  }

  public void continueVideo() {
    if(playingVideo==null){
      System.out.println("Cannot continue video: No video is currently playing");
    }
    else{
      if(paused){
        System.out.println("Continuing video: "+ playingVideo.getTitle());
        paused=false;
      }
      else{
        System.out.println("Cannot continue video: Video is not paused");
      }
    }
  }

  public void showPlaying() {
    if(playingVideo==null){
      System.out.println("No video is currently playing");
    }
    else{
      if(paused){
        System.out.println("Currently playing: "+ playingVideo.toString() +" - PAUSED");
      }
      else{
        System.out.println("Currently playing: "+ playingVideo.toString());
      }
    }
  }

  private HashMap<String,VideoPlaylist> playlists = new HashMap<>();

  public void createPlaylist(String playlistName) {
    if(playlistName.contains(" ")){
      System.out.println("No space should be there");
    }
    if(playlists.containsKey(playlistName.toUpperCase())){
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }
    else{
      System.out.println("Successfully created new playlist: " +playlistName);
      playlists.put(playlistName.toUpperCase(),new VideoPlaylist(playlistName));
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    if(!playlists.containsKey(playlistName.toUpperCase()) ){
      System.out.println("Cannot add video to "+ playlistName+": Playlist does not exist");
    }
    else{
      if(videoLibrary.getVideo(videoId)==null ){
        System.out.println("Cannot add video to "+playlistName+": Video does not exist");
      }
      else if(videoLibrary.getVideo(videoId).isFlagged()){
        System.out.println("Cannot add video to "+playlistName+": "+showFlaggedReason(videoLibrary.getVideo(videoId)));
      }
      else if(playlists.get(playlistName.toUpperCase()).getVideos()!=null && playlists.get(playlistName.toUpperCase()).getVideos().contains(videoLibrary.getVideo(videoId))){
        System.out.println("Cannot add video to "+playlistName+": Video already added");
      }
      else{
        System.out.println("Added video to "+playlistName+": "+videoLibrary.getVideo(videoId).getTitle());
        playlists.get(playlistName.toUpperCase()).addVideo((videoLibrary.getVideo(videoId)));
      }
    }
  }

  public void showAllPlaylists() {
    if (playlists.size()==0){
      System.out.println("No playlists exist yet");
    }
    else{
      System.out.println("Showing all playlists:");
      playlists.entrySet().stream()
              .sorted((a,b)->((String)a.getKey()).compareTo(b.getKey()))
              .forEach(a-> System.out.println("\t"+a.getValue().getPlaylistName()));
    }
  }

  public void showPlaylist(String playlistName) {
    if(!playlists.containsKey(playlistName.toUpperCase()) ){
      System.out.println("Cannot show playlist "+ playlistName+": Playlist does not exist");
    }
    else {
      System.out.println("Showing playlist: " + playlistName);
      if (playlists.get(playlistName.toUpperCase()).getVideos() == null) {
        System.out.println("\tNo videos here yet");
      } else {
        for (Video v : playlists.get(playlistName.toUpperCase()).getVideos()) {
          System.out.println("\t" + v.toString());
        }
      }
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    if(!playlists.containsKey(playlistName.toUpperCase()) ){
      System.out.println("Cannot remove video from "+ playlistName+": Playlist does not exist");
    }
    else{
      if(videoLibrary.getVideo(videoId)==null ){
        System.out.println("Cannot remove video from "+playlistName+": Video does not exist");
      }
      else if(playlists.get(playlistName.toUpperCase()).getVideos()==null||playlists.get(playlistName.toUpperCase()).getVideos()!=null && !(playlists.get(playlistName.toUpperCase()).getVideos().contains(videoLibrary.getVideo(videoId)))){
        System.out.println("Cannot remove video from "+playlistName+": Video is not in playlist");
      }
      else{
        System.out.println("Removed video from "+playlistName+": "+videoLibrary.getVideo(videoId).getTitle());
        playlists.get(playlistName.toUpperCase()).removeVideo((videoLibrary.getVideo(videoId)));
      }
    }
  }

  public void clearPlaylist(String playlistName) {
    if(!playlists.containsKey(playlistName.toUpperCase())){
      System.out.println("Cannot clear playlist "+ playlistName+": Playlist does not exist");
    }
    else{
      System.out.println("Successfully removed all videos from "+playlistName);
      playlists.get(playlistName.toUpperCase()).clearPlaylist();
    }
  }

  public void deletePlaylist(String playlistName) {
    if(!playlists.containsKey(playlistName.toUpperCase())){
      System.out.println("Cannot delete playlist "+ playlistName+": Playlist does not exist");
    }
    else{
      System.out.println("Deleted playlist: "+playlistName);
      playlists.remove(playlistName);
    }
  }

  public void searchVideos(String searchTerm) {
    ArrayList<Video> allVideos= new ArrayList<>();
    ArrayList<Video> notFLaggedVideos= new ArrayList<>(videosNoFlag());
    for (Video v: notFLaggedVideos) {
      if(v.getTitle().toLowerCase().contains(searchTerm.toLowerCase())){
        allVideos.add(v);
      }
    }
    allVideos.sort(Comparator.comparing(Video::getTitle));
    if(allVideos.size()==0){
      System.out.println("No search results for "+searchTerm);
    }
    else{
      System.out.println("Here are the results for "+searchTerm+":");
      printSearched(allVideos);
    }
  }

  public void printSearched(ArrayList<Video> allVideos){
    int count = 0;
    for (Video v: allVideos){
      count++;
      System.out.println(count+") "+ v.toString());
    }
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
    System.out.println("If your answer is not a valid number, we will assume it's a no.");
    var sc = new Scanner(System.in);
    String readNumber = sc.nextLine();
    try{
      Integer number = Integer.valueOf(readNumber);
      if(number<=count){
        playVideo(allVideos.get(number-1).getVideoId());
      }
    }
    catch (NumberFormatException ex){
    }
  }
  public void searchVideosWithTag(String videoTag) {
    ArrayList<Video> allVideos= new ArrayList<>();
    ArrayList<Video> notFLaggedVideos= new ArrayList<>(videosNoFlag());
    for (Video v: notFLaggedVideos) {
      for(String tag:v.getTags()) {
        if (tag.toLowerCase().equals(videoTag.toLowerCase())) {
          allVideos.add(v);
        }
      }
    }
    allVideos.sort(Comparator.comparing(Video::getTitle));
    if(allVideos.size()==0){
      System.out.println("No search results for "+videoTag);
    }
    else{
      System.out.println("Here are the results for "+videoTag+":");
      printSearched(allVideos);
    }
  }

  public void flagVideo(String videoId) {
    if(checkFlagged(videoId)) return;
    Video video =videoLibrary.getVideo(videoId);
    video.flag("");
    System.out.println("Successfully flagged video: "+video.getTitle()+video.getReason());
  }
  public boolean checkFlagged(String videoId){
    System.out.print("Cannot flag video: ");
    if(videoLibrary.getVideo(videoId)==null){
      System.out.println("Video does not exist");
      return true;
    }
    else if(videoLibrary.getVideo(videoId).isFlagged()){
      System.out.println("Video is already flagged");
      return true;
    }
    return false;
  }

  public void flagVideo(String videoId, String reason) {
    if(checkFlagged(videoId)) return;
    Video video =videoLibrary.getVideo(videoId);
    video.flag(reason);
    if(playingVideo==video){
      stopVideo();
    }
    System.out.println("Successfully flagged video: "+video.getTitle()+video.getReason());

  }
  public String showFlaggedReason(Video v){
    return ("Video is currently flagged"+v.getReason());
  }

  public void allowVideo(String videoId) {
    Video video = videoLibrary.getVideo(videoId);
    if(video == null){
      System.out.println("Cannot remove flag from video: Video does not exist");
    }
    else {
      if (!video.isFlagged()) {
        System.out.println("Cannot remove flag from video: Video is not flagged");
      }
      else {
        System.out.println("Successfully removed flag from video: "+video.getTitle());
      }
    }
  }
}