package com.knowmywork.loadmorerecyclerview;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Comment> mDataSet;

    // variable to define how many items you want to load in RecyclerView at a time
    private final static int limit = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        // Here you should instantiate your Toolbar, if using
        // and other views that you are using
        // ...

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataSet = new ArrayList<>();

        mProgressBar.setVisibility(View.VISIBLE);

        // Method to load initial list of data
        loadData();
    }

    public void loadData() {

        // Here you should make a http call to your REST API to load initaila data
        // As I don't have any public REST API, I willl be loading dummy data
        // ...

        // you should also check if the number of items you get from API
        // are less than limit or equal to limit
        // if there are equal to limit then add a null item to list
        // otherwise not

        List<Comment> mInitialDataSet = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            mInitialDataSet.add(new Comment("Tutorial on Load More Data in RecyclerView with ProgressBar by Know My Work", "Know My Work"));
        }
        mInitialDataSet.add(null);

        mAdapter = new RecyclerViewAdapter(mInitialDataSet, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(int position) {
                loadMoreData(position);
            }
        });
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void loadMoreData(int skip) {

        // Here you should make a http call to load more data
        // by utilizing the "skip" parameter
        // ...

        // you should also check if the number of items you get from API
        // are less than limit or equal to limit
        // if there are equal to limit then add a "null" item to list
        // otherwise not

        // As I don't have a REST API, i will load dummy data

        mDataSet.clear();
        for (int i = 0; i < limit; i++) {
            mDataSet.add(new Comment("Tutorial on Load More Data in RecyclerView with ProgressBar by Know My Work", "Know My Work"));
        }
        mDataSet.add(null);

        // since we are using the dummy data, it can happen that we try to
        // update the RecyclerView while it is computing its layout
        // it means that RecyclerView is in a lockdown state and any attempt to update adapter contents will result in an exception
        // so we will make any change to RecyclerView  when it is not in a lockdown state
        if (mRecyclerView.isComputingLayout() == false) {
            mAdapter.removeLastItem();
        } else {
            mRecyclerView.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.removeLastItem();

                    mAdapter.setLoaded();

                    if (mRecyclerView.isComputingLayout() == false) {
                        mAdapter.update(mDataSet);
                    } else {
                        mRecyclerView.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.update(mDataSet);
                            }
                        });
                    }
                }
            });
        }

        // since we are using dummy data
        // it can happen that you will never see a ProgressBAr
        // so to  vizualise a ProgressBar with dummy data you can use code commented below instead of above code


        /*if (mRecyclerView.isComputingLayout() == false) {
            mAdapter.removeLastItem();
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.removeLastItem();

                    mAdapter.setLoaded();

                    if (mRecyclerView.isComputingLayout() == false) {
                        mAdapter.update(mDataSet);
                    } else {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.update(mDataSet);
                            }
                        }, 1000);
                    }
                }
            }, 1000);
        }*/


    }

}
