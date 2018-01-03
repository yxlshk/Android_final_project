package com.example.a77354.android_final_project.RunMusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;

public class MusicService extends Service {
    public static MediaPlayer mp = new MediaPlayer();
    public static int state = -1;  //0 Playing,  1 Paused, 2 Stopped
    public MyBinder mBinder = new MyBinder();
    public MusicService() {
        ;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public class MyBinder extends Binder {
        @Override
        protected  boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            switch(code) {
                case 101:
                    //播放、暂停
                    if (mp.isPlaying()) {
                        mp.pause();
                        state = 1;
                    } else {
                        mp.start();
                        state = 0;
                    }
                    break;
                //切歌
                case 102:
                    if (mp != null) {
                        mp.reset();
                        try {
                            String path = data.readString();
                            mp.setDataSource(path);
                            mp.prepare();
                            mp.setLooping(true);
                            mp.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//                case 102:
//                    if (mp != null) {
//                        mp.stop();
//                        try {
//                            mp.prepare();
//                            mp.seekTo(0);
//                            state = 2;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    //stop
//                    break;
//                case 103:
//                    //quit
//                    if (mp != null) {
//                        mp.stop();
//                        mp.release();   //释放资源
//                    }
//                    break;
                case 104:
                    //drag seekbar
                    int seconds = data.readInt();
                    if (mp != null) {
                        try {
                            mp.seekTo(seconds);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
            boolean temp = false;
            try {
                temp = super.onTransact(code, data, reply, flags);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return temp;
        }
    }
}
