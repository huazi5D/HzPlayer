package bf.hzplayer;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import bf.hzplayer.Fragment.LVideoListFragment;
import bf.hzplayer.Fragment.NVideoListFragment;

public class VideoListActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup mTitleTab;
    private FragmentTransaction transaction;
    private NVideoListFragment nVideoListFragment;
    private LVideoListFragment lVideoListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mTitleTab = (RadioGroup) findViewById(R.id.title_tab);
        mTitleTab.setOnCheckedChangeListener(this);

        nVideoListFragment = NVideoListFragment.newInstance();
        lVideoListFragment = LVideoListFragment.newInstance();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.video_list_fragment, nVideoListFragment).hide(nVideoListFragment);
        transaction.add(R.id.video_list_fragment, lVideoListFragment).hide(lVideoListFragment).commit();
        mTitleTab.check(R.id.net_button);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        transaction = getSupportFragmentManager().beginTransaction();

        switch (checkedId) {
            case R.id.net_button:
                transaction.show(nVideoListFragment).hide(lVideoListFragment).commit();
                break;
            case R.id.loc_button:
                transaction.show(lVideoListFragment).hide(nVideoListFragment).commit();
                break;
        }
    }
}
