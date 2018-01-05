package com.example.a77354.android_final_project.Trajectory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.a77354.android_final_project.R;
import com.xw.repo.supl.ISlidingUpPanel;
import com.xw.repo.supl.SlidingUpPanelLayout;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by shujunhuai on 2018/1/4.
 */

public class TrajectoryActivity  extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper swipeBackActivityHelper;
    private ISlidingUpPanel mSlidingUpPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trajectory_layout);

        //顶部返回键的配置
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("跑步轨迹");

        ButterKnife.bind(this);
        final SlidingUpPanelLayout mSlidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_up_panel_layout);
        final TextView mPickHintText = (TextView) findViewById(R.id.pick_hint_text);
        final View mBgLayout = (View) findViewById(R.id.bg_layout);

        mSlidingUpPanelLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListenerAdapter() {
            @Override
            public void onPanelExpanded(ISlidingUpPanel panel) {
                mSlidingUpPanel = panel;

                int childCount = mSlidingUpPanelLayout.getChildCount();
                CardPanelView card;
                for (int i = 1; i < childCount; i++) {
                    card = (CardPanelView) mSlidingUpPanelLayout.getChildAt(i);
                    if (card != panel && card.getSlideState() == SlidingUpPanelLayout.EXPANDED) {
                        mSlidingUpPanelLayout.collapsePanel(card);
                        break;
                    }
                }

            }

            @Override
            public void onPanelSliding(ISlidingUpPanel panel, float slideProgress) {
                if (mSlidingUpPanel == null || mSlidingUpPanel == panel) {
                    mPickHintText.setAlpha(1 - slideProgress);
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
