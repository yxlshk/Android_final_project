package com.example.a77354.android_final_project.RunMusic;

/**
 * Created by kunzhai on 2018/1/2.
 */

public class MusicInfo {

    /** 歌曲id */
    private int musicId;

    /** 歌曲名 */
    private String musicName;

    /** 歌曲演唱者 */
    private String musicArtist;

    /** 歌曲地址 */
    private String musicPath;

    /** 歌曲时间长度 */
    private int musicLong;




    public MusicInfo() {
        super();
        // TODO Auto-generated constructor stub
    }



    public MusicInfo(int musicId, String musicName, String musicArtist,
                     String musicPath, int musicLong) {
        super();
        this.musicId = musicId;
        this.musicName = musicName;
        this.musicArtist = musicArtist;
        this.musicPath = musicPath;
        this.musicLong = musicLong;
    }



    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicArtist() {
        return musicArtist;
    }

    public void setMusicArtist(String musicArtist) {
        this.musicArtist = musicArtist;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public int getMusicLong() {
        return musicLong;
    }

    public void setMusicLong(int misicLong) {
        this.musicLong = misicLong;
    }


}