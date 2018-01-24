package com.zs.project.ui.fragment.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.zs.project.R;
import com.zs.project.base.LazyFragmentTest;

/**
 * Created by zs
 * Date：2018年 01月 24日
 * Time：10:04
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class NewListFragment extends LazyFragmentTest {

    private String mTitleName;
    private Fragment mFrgment;
    private TextView tv_test;

    public static NewListFragment getInstance(String ... args){

        NewListFragment fragment = new NewListFragment();
        if (args != null){
            Bundle bundle = new Bundle();
            if (args.length > 0){
                bundle.putString("index",args[0]);
            }
            if (args.length > 1){
                bundle.putString("title",args[1]);
            }
            fragment.setArguments(bundle);
        }
        return fragment;

    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.public_list_layout);
        mFrgment = this;
        initData();
    }

    @Override
    protected void initData() {
        super.initData();
        mTitleName = getArguments().getString("title");
    }
}
