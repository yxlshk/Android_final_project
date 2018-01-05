package com.example.a77354.android_final_project.RunMusic;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77354.android_final_project.R;
import com.example.a77354.android_final_project.ToolClass.CommonAdapter;
import com.example.a77354.android_final_project.ToolClass.myViewHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by shujunhuai on 2017/12/26.
 */

public class RunMusicActivity  extends AppCompatActivity implements SwipeBackActivityBase {

    private SwipeBackActivityHelper swipeBackActivityHelper;
    private List<MusicInfo> dataList = new ArrayList<>();
    private CommonAdapter<MusicInfo> songAdapter;
    private RecyclerView.Adapter adapter;

    private MusicService ms = null;
    private ServiceConnection sc;
    private IBinder mBinder;
    private ImageButton playAndPause;
    private ImageButton buttomPlayAndPause;
    private ImageButton topNextButton;
    private ImageButton BottomNextButton;
    private ImageButton preButton;
    private ImageView Album;
    private ImageView songImage;
    private android.support.v7.widget.AppCompatSeekBar seekbar;
    private TextView curTime;
    private TextView totalTime;
    private int currentSong;
    private Handler mHandler;
    private static boolean hasPermission;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_music_layout);
        initVariable();     //初始化各种私有变量。
        initRecycleView();
        setClickEvent();
        //设置service相关
        initService();
        //定义一个Handler
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch(msg.what) {
                    case 123:
                        seekbar.setMax( msg.arg2);
                        seekbar.setProgress(msg.arg1);
                        curTime.setText(secondsFormatter(msg.arg1));
                        totalTime.setText(secondsFormatter(msg.arg2));
                        break;
                    case 100:       //扫描完所有mp3文件后更新歌单
                        songAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };

        //定义一个新线程
        Thread mThread = new Thread() {
            @Override
            public void run() {
                while(true) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (true) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = 123;
                        msg.arg1 = ms.mp.getCurrentPosition();
                        msg.arg2 = ms.mp.getDuration();
                        mHandler.sendMessage(msg);
                    }//判断条件需修改
                }
            }
        };
        mThread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<MusicInfo> tempList = new ArrayList<>();
                tempList = scanAllAudioFiles();
                dataList.clear();
                for(int i = 0; i < tempList.size();i++) {
                    dataList.add(tempList.get(i));
                }
                mHandler.sendEmptyMessage(100);
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        swipeBackActivityHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && swipeBackActivityHelper != null)
            return swipeBackActivityHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackActivityHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permission[], int[] grantResult) {
        if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
            ms = new MusicService();
        } else {
            System.exit(0);
        }
    }


    //辅助函数
    private String secondsFormatter(int milSeconds) {
        int seconds = milSeconds / 1000;
        int minute =  seconds / 60;
        int second = seconds % 60;
        String hourStr, secondStr;
        if(minute < 10) {
            hourStr = "0"+ minute;
        } else {
            hourStr = "" + minute;
        }
        if(second < 10) {
            secondStr = "0" + second;
        } else {
            secondStr = "" + second;
        }
        return hourStr + ":" + secondStr;
    }


    private void prepareDataList() {
        for (int i = 0; i < 1; i++) {
            dataList.add(new MusicInfo(0, "我的宣言", "周柏豪", "", 0));
            dataList.add(new MusicInfo(0,"斩立决", "周柏豪","", 0));
            dataList.add(new MusicInfo(0,"杰出青年", "周柏豪","",0));
            dataList.add(new MusicInfo(0,"天光", "周柏豪","",0));
            dataList.add(new MusicInfo(0,"传闻", "周柏豪","",0));
            dataList.add(new MusicInfo(0,"够钟", "周柏豪","",0));
            dataList.add(new MusicInfo(0,"近在千里", "周柏豪","",0));
            dataList.add(new MusicInfo(0,"有生一天", "周柏豪","",0));
        }
    }
    public static void verifyStoragePermissions(Activity activity) {
        try {
            int permission = ActivityCompat.checkSelfPermission(activity, "Manifest.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initVariable() {
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("跑步歌单");
        playAndPause = (ImageButton) findViewById(R.id.playAndPause);
        buttomPlayAndPause = (ImageButton) findViewById(R.id.playBtn);
        seekbar = (android.support.v7.widget.AppCompatSeekBar)findViewById(R.id.seekBar);
        curTime = (TextView)findViewById(R.id.leftTime);
        totalTime = (TextView)findViewById(R.id.rightTime);
        topNextButton = (ImageButton) findViewById(R.id.nextOne);
        BottomNextButton = (ImageButton) findViewById(R.id.skip_next);
        preButton = (ImageButton) findViewById(R.id.skip_pre);
        Album = (ImageView) findViewById(R.id.Album);
        songImage = (ImageView)findViewById(R.id.songImage);
        currentSong = -1;
    }
    private void initRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.music_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        songAdapter = new CommonAdapter<MusicInfo>(this, R.layout.run_music_recycler_item, dataList) {
            @Override
            public void convert(myViewHolder holder, MusicInfo s) {
                TextView musicName = (TextView) holder.getView(R.id.musicName);
                TextView singer = (TextView) holder.getView(R.id.singer);
                musicName.setText(s.getMusicName());
                singer.setText(s.getMusicArtist());
            }
        };
        recyclerView.setAdapter(songAdapter);
        prepareDataList();
        songAdapter.notifyDataSetChanged();
    }
    private void initService() {
        verifyStoragePermissions(this);
        //创建服务
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        //初始化sc
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = service;
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                sc = null;
            }
        };
        //绑定
        bindService(intent, sc, Context.BIND_AUTO_CREATE);
    }
    private void setClickEvent() {
        FloatingActionButton refresh_song_btn = (FloatingActionButton) findViewById(R.id.refresh_song_btn);
        refresh_song_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<MusicInfo> tempList = new ArrayList<>();
                        tempList = scanAllAudioFiles();
                        dataList.clear();
                        for(int i = 0; i < tempList.size();i++) {
                            dataList.add(tempList.get(i));
                        }
                        mHandler.sendEmptyMessage(100);
                    }
                }).start();
            }
        });
        playAndPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = playAndPause.getTag().toString();
                if(tag.equals("0")) {
                    buttomPlayAndPause.setTag("1");
                    buttomPlayAndPause.setImageResource(R.drawable.play2);
                    playAndPause.setTag("1");
                    playAndPause.setImageResource(R.drawable.play);
                } else if(tag.equals("1")) {
                    buttomPlayAndPause.setTag("0");
                    buttomPlayAndPause.setImageResource(R.drawable.pause2);
                    playAndPause.setTag("0");
                    playAndPause.setImageResource(R.drawable.pause);
                }
                try{
                    int code = 101;
                    Parcel data =Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(code, data, reply, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        buttomPlayAndPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = buttomPlayAndPause.getTag().toString();
                if(tag.equals("0")) {
                    buttomPlayAndPause.setTag("1");
                    buttomPlayAndPause.setImageResource(R.drawable.play2);
                    playAndPause.setTag("1");
                    playAndPause.setImageResource(R.drawable.play);
                } else if(tag.equals("1")) {
                    buttomPlayAndPause.setTag("0");
                    buttomPlayAndPause.setImageResource(R.drawable.pause2);
                    playAndPause.setTag("0");
                    playAndPause.setImageResource(R.drawable.pause);
                }
                try{
                    int code = 101;
                    Parcel data =Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(code, data, reply, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        topNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSong = (currentSong + 1) % dataList.size();
                playMusic(currentSong);
            }
        });

        BottomNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSong = (currentSong + 1) % dataList.size();
                playMusic(currentSong);
            }
        });

        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSong = (currentSong - 1) % dataList.size();
                playMusic(currentSong);
            }
        });

        songAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentSong = position;
                playMusic(position);
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                curTime.setText(secondsFormatter(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBarA) {
                try{
                    int code = 104;
                    Parcel data =Parcel.obtain();
                    data.writeInt(seekbar.getProgress());
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(code, data, reply, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /** 获取媒体库中所有歌曲 */
    private ArrayList<MusicInfo> scanAllAudioFiles() {
        // 生成动态数组，并且转载数据
        ArrayList<MusicInfo> mylist = new ArrayList<MusicInfo>();

        // 查询媒体数据库
        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        // 遍历媒体数据库
        if (cursor.moveToFirst()) {//判断游标是否为空

            MusicInfo info;
            int musicId;//歌曲id
            String musicName;//歌曲名
            String musicArtist;//音乐家
            int musicTime;//歌曲时长
            String musicPath;//歌曲路径
            while (!cursor.isAfterLast()) {//游标是否指向第最后一行的位置

                // 歌曲编号 :MediaStore.Audio.Media._ID
                musicId = cursor.getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                // 歌曲名: MediaStore.Audio.Media.TITLE
                musicName = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

                // 音乐家： MediaStore.Audio.Media.ARTIST
                musicArtist = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                // 歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                musicPath = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                // 歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                musicTime = cursor
                        .getInt(cursor
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                // 歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                Long size = cursor.getLong(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                //封面ID
                int albumId = cursor.getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

                if (size > 1024 * 800) {// 大于800K的音乐文件
                    info = new MusicInfo();
                    info.setMusicId(musicId);
                    info.setMusicName(musicName);
                    info.setMusicArtist(musicArtist);
                    info.setMusicPath(musicPath);
                    info.setMusicLong(musicTime);
                    info.setAlbumId(albumId);
                    mylist.add(info);
                }
                cursor.moveToNext();//游标移动到下一行
            }
        }
        cursor.close();
        return mylist;
    }

    private String getAlbumArt(int album_id)
    {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[] { "album_art" };
        Cursor cur = this.getContentResolver().query(  Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),  projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0)
        {  cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        cur = null;
        return album_art;
    }

    private void playMusic(int position) {
        Toast.makeText(getApplicationContext(),"click", Toast.LENGTH_SHORT).show();
        TextView bottomSongName = (TextView) findViewById(R.id.bottomSongName);
        TextView bottomSingerName = (TextView) findViewById(R.id.bottomSinger);
        TextView SongName = (TextView) findViewById(R.id.songName);
        TextView singerName = (TextView) findViewById(R.id.singerName);
        bottomSongName.setText(dataList.get(position).getMusicName());
        SongName.setText(dataList.get(position).getMusicName());
        bottomSingerName.setText(dataList.get(position).getMusicArtist());
        singerName.setText(dataList.get(position).getMusicArtist());
        playAndPause.setImageResource(R.drawable.play);
        buttomPlayAndPause.setImageResource(R.drawable.play2);
        playAndPause.setTag("1");
        buttomPlayAndPause.setTag("1");
        try{
            int code = 102;                             //更换歌曲
            Parcel data =Parcel.obtain();
            data.writeString(dataList.get(position).getMusicPath());
            Parcel reply = Parcel.obtain();
            mBinder.transact(code, data, reply, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        String albumArt = getAlbumArt(dataList.get(position).getAlbumId());
        Bitmap bm = null;
     //   Log.d("test",albumArt );
        if (albumArt == null)
        {
            Album.setImageResource(R.drawable.me);
            songImage.setImageResource(R.drawable.me);
        }
        else
        {
            Log.e("test",albumArt );
            bm = BitmapFactory.decodeFile(albumArt);
            BitmapDrawable bmpDraw = new BitmapDrawable(bm);
            Album.setImageDrawable(bmpDraw);
            songImage.setImageDrawable(bmpDraw);
        }
    }
}
