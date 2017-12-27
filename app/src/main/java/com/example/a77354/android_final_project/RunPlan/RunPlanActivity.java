package com.example.a77354.android_final_project.RunPlan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.a77354.android_final_project.RunPlan.PlanEntity;
import com.example.a77354.android_final_project.R;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * Created by shujunhuai on 2017/12/26.
 */

public class RunPlanActivity extends AppCompatActivity implements SwipeBackActivityBase {

    private List<PlanEntity> dataList = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private SwipeBackActivityHelper swipeBackActivityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_plan_layout);

        //顶部返回键的配置
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("跑步计划");

        //recyclerView伪数据填充
        adapter = getAdapter();
        prepareDataList();
        adapter.notifyDataSetChanged();

        //动画效果
        RecyclerCoverFlow mList = (RecyclerCoverFlow) findViewById(R.id.plan_list);
        mList.setAdapter(adapter);
        mList.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
//                ((TextView)findViewById(R.id.index)).setText((position+1)+"/"+mList.getLayoutManager().getItemCount());
            }
        });
    }

    private void prepareDataList() {
        for (int i = 0; i < 15; i++) {
            dataList.add(new PlanEntity("12.31 16:00", "假草操场", "五五开"));
            dataList.add(new PlanEntity("1.1 18:00", "内环", "坤哥"));
            dataList.add(new PlanEntity("12.31 16:00", "假草操场", "五五开"));
            dataList.add(new PlanEntity("12.31 16:00", "假草操场", "五五开"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private RecyclerView.Adapter getAdapter() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflater.inflate(R.layout.run_recycler_item, parent, false);
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
        TextView planTime;
        TextView planPlace;
        TextView partner;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.planTime = (TextView) itemView.findViewById(R.id.planTime);
            this.planPlace = (TextView) itemView.findViewById(R.id.planPlace);
            this.partner = (TextView) itemView.findViewById(R.id.planPartner);
        }

        public void bindData(PlanEntity planEntity) {
            planTime.setText(planEntity.getPlanTime());
            planPlace.setText(planEntity.getPlanPlace());
            partner.setText(planEntity.getPlanPartner());
        }
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
        me.imid.swipebacklayout.lib.Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
