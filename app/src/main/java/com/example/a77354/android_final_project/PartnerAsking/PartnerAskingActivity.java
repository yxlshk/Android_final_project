package com.example.a77354.android_final_project.PartnerAsking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a77354.android_final_project.R;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by shujunhuai on 2018/1/4.
 */

public class PartnerAskingActivity extends AppCompatActivity implements SwipeBackActivityBase {
    class temp {
        private String publisher;
        private String content;
        private String publishTime;
        //还有两个变量，一个是发布者的头像、一个是有记得人点"约"，即点"约"列表
        public temp(String publisher, String content, String publishTime) {
            this.publisher = publisher;
            this.content = content;
            this.publishTime = publishTime;
        }

        public String getContent() {
            return content;
        }

        public String getPublisher() {
            return publisher;
        }

        public String getPublishTime() {
            return publishTime;
        }
    }

    private SwipeBackActivityHelper swipeBackActivityHelper;
    private List<PartnerAskingActivity.temp> dataList = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner_asking_layout);

        //顶部返回键的配置
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("同校约跑");

        //recyclerView伪数据填充
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.partner_asking_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        prepareDataList();
        adapter.notifyDataSetChanged();

        //点击编辑一条新的帖子
        FloatingActionButton addNewAskingBtn = (FloatingActionButton) findViewById(R.id.add_new_asking);
        addNewAskingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PartnerAskingActivity.this, NewAskingActivity.class));
            }
        });
    }

    private void prepareDataList() {
        for (int i = 0; i < 5; i++) {
            dataList.add(new PartnerAskingActivity.temp("shujh", "寻找本周五晚上的跑步小伙伴～","1月3日，18：00"));
        }
    }

    private RecyclerView.Adapter getAdapter() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.partner_asking_item_layout, parent, false);
                return new PartnerAskingActivity.MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                PartnerAskingActivity.MyViewHolder myHolder = (PartnerAskingActivity.MyViewHolder) holder;
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

        TextView publisher;
        TextView content;
        TextView publishTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.publisher = (TextView) itemView.findViewById(R.id.publisher_nickname);
            this.content = (TextView) itemView.findViewById(R.id.publish_content);
            this.publishTime = (TextView) itemView.findViewById(R.id.publish_time);
        }

        public void bindData(PartnerAskingActivity.temp temp) {
            publisher.setText(temp.getPublisher());
            content.setText(temp.getContent());
            publishTime.setText(temp.getPublishTime());
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
