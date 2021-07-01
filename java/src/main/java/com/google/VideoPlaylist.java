package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {
    String playlistName;
    ArrayList<Video> videos = new ArrayList<>();

    public VideoPlaylist(String playlistName) {
        this.playlistName = playlistName;
    }

    public void addVideo(Video video){
        videos.add(video);
    }

    public List<Video> getVideos(){
        if(videos.size()==0) return null;
        return new ArrayList<Video>(this.videos);
    }

    public void removeVideo(Video video){
        videos.remove(video);
    }

    public String getPlaylistName() {
        return playlistName;
    }
    public void clearPlaylist(){
        videos.clear();
    }
}
