package com.hins.reader.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hins.reader.App;
import com.hins.reader.R;
import com.hins.reader.base.BaseActivity;
import com.hins.reader.ui.about.AboutActivity;
import com.hins.reader.ui.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";


    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.container)
    FrameLayout mContainer;

    private MainFragment mMainFragment;
    private long mExistTime;

    private boolean mNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        
        mNightMode = App.isNightMode();

        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolBar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mNavView.setCheckedItem(R.id.home);
        mNavView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.setting:
                        SettingActivity.start(MainActivity.this);
                        break;
                    case R.id.about:
                        AboutActivity.start(MainActivity.this);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        mMainFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mMainFragment)
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            doExistApp();
        }
    }

    private void doExistApp() {
        if (System.currentTimeMillis() - mExistTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExistTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNightMode != App.isNightMode()) {
            recreate();
            Log.d(TAG, "recreate: ");
        }

        Log.d(TAG, "onResume: ");
       
    }
}
