package com.example.a77354.android_final_project.RunMusic;

/**
 * Created by kunzhai on 2018/1/2.
 */
class song {
    private String musicName;
    private String singer;
    public song(String name, String singer) {
        this.musicName = name;
        this.singer = singer;
    }

    public String getMusicName() {
        return musicName;
    }

    public String getSinger() {
        return singer;
    }
}