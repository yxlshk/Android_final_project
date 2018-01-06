package com.example.a77354.android_final_project.RunPlan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a77354.android_final_project.HttpServiceInterface.CreatePlanServiceInterface;
import com.example.a77354.android_final_project.HttpServiceInterface.GetPlanServiceInterface;
import com.example.a77354.android_final_project.RunPlan.PlanEntity;
import com.example.a77354.android_final_project.R;
import com.example.a77354.android_final_project.ToolClass.HttpTool;
import com.example.a77354.android_final_project.ToolClass.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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


        Button addNewPlanBtn = (Button) findViewById(R.id.add_new_plan_button);
        addNewPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RunPlanActivity.this, AddNewPlanActivity.class));
            }
        });
    }

    private void prepareDataList() {
        Retrofit retrofit = HttpTool.createRetrofit("http://112.124.47.197:4000/api/runner/", getApplicationContext(), "en");
        GetPlanServiceInterface service = retrofit.create(GetPlanServiceInterface.class);
        service.getPlan()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PlanGetFromService> >(){
                    @Override
                    public final void onCompleted() {
                        Log.e("test", "完成传输");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RunPlanActivity.this, e.hashCode() + "确认信息是否符合标准", Toast.LENGTH_SHORT).show();
                        Log.e("test", e.getMessage());
                    }
                    @Override
                    public void onNext(List<PlanGetFromService> responseBody) {
                        Log.e("test", responseBody.toString());
                        for (int i = 0; i < responseBody.size(); i++) {
                            dataList.add(new PlanEntity("", responseBody.get(i).getPlanTime(), responseBody.get(i).getPlanPlace(), responseBody.get(i).getPlanPartner()));
                        }
                    }
                });
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
