package com.example.a77354.android_final_project.RunMusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a77354.android_final_project.R;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by shujunhuai on 2017/12/26.
 */

public class RunMusicActivity extends AppCompatActivity implements SwipeBackActivityBase {
    class temp {
        private String musicName;
        private String singer;
        public temp(String name, String singer) {
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

    private SwipeBackActivityHelper swipeBackActivityHelper;
    private List<temp> dataList = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_music_layout);

        //顶部返回键的配置
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("跑步歌单");

        //recyclerView伪数据填充
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.music_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        prepareDataList();
        adapter.notifyDataSetChanged();
    }

    private void prepareDataList() {
        for (int i = 0; i < 5; i++) {
            dataList.add(new temp("我的宣言", "周柏豪"));
            dataList.add(new temp("斩立决", "周柏豪"));
            dataList.add(new temp("杰出青年", "周柏豪"));
            dataList.add(new temp("天光", "周柏豪"));
            dataList.add(new temp("传闻", "周柏豪"));
            dataList.add(new temp("够钟", "周柏豪"));
            dataList.add(new temp("近在千里", "周柏豪"));
            dataList.add(new temp("有生一天", "周柏豪"));
        }
    }

    private RecyclerView.Adapter getAdapter() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.run_music_recycler_item, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyViewHolder myHolder = (MyViewHolder) holder;
                myHolder.bindData(dataList.get(position));
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        };
        return adapter;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView musicName;
        TextView singer;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.musicName = (TextView) itemView.findViewById(R.id.musicName);
            this.singer = (TextView) itemView.findViewById(R.id.singer);
        }

        public void bindData(temp temp) {
            musicName.setText(temp.getMusicName());
            singer.setText(temp.getSinger());
        }
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
}
