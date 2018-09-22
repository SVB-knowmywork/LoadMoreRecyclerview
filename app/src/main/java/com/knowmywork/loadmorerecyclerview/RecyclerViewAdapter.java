package com.knowmywork.loadmorerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    // Matching each View Type against a Integer
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    // Items List varaiable
    private List<Comment> mDataset;

    // Boolean to track loading status
    private boolean loading = false;

    // Listener or the Interface defined in STEP 4
    private OnLoadMoreListener mOnLoadMoreListener;

    // ViewHolder for Comment or the Items to be shown in RecyclerView
    // You must define it according to the data you want to show
    public static class DataViewHolder extends RecyclerView.ViewHolder {

        public TextView comment;
        public TextView byUser;

        public DataViewHolder(View view) {
            super(view);
            comment = (TextView) view.findViewById(R.id.comment);
            byUser = (TextView) view.findViewById(R.id.byUser);
        }

    }

    // ViewHolder for ProgressBar
    // Copy it as it is
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progress_bar_bottom);
        }

    }

    // Constructor for the Adapter
    public RecyclerViewAdapter(List<Comment> Dataset, OnLoadMoreListener onLoadMoreListener) {
        mDataset = Dataset;
        mOnLoadMoreListener = onLoadMoreListener;
    }

    // OnCreateViewHolder, where we are creating the ViewHolder as per the ViewType
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
            vh = new DataViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_bottom_layout, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    // Binding data to a ViewHolder as per its ViewType
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataViewHolder) {

            ((DataViewHolder) holder).comment.setText(mDataset.get(position).getComment());
            ((DataViewHolder) holder).byUser.setText(mDataset.get(position).getUser());

        } else {

            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            if (!loading) {
                // End has been reached
                // Do something
                if (mOnLoadMoreListener != null) {
                    mOnLoadMoreListener.onLoadMore(position);
                }
                loading = true;
            }

        }
    }

    // Method to set value of boolean variable "loading" to false
    // this method is called when data is loaded in the Activity class
    public void setLoaded() {
        loading = false;
    }

    // This method contains the main logic for showing ProgressBar at the end of RecyclerView
    // In the Activity class, we insert a null item at the end of the list
    // this null item produces a ProgressViewHolder by the logic given below
    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Method to add more items to the list
    public void update(List<Comment> newItems){
        mDataset.addAll(newItems);
        notifyDataSetChanged();
    }

    // This method is used to remove ProgressBar when data is loaded
    public void removeLastItem(){
        mDataset.remove(mDataset.size() - 1);
        notifyDataSetChanged();
    }

}
