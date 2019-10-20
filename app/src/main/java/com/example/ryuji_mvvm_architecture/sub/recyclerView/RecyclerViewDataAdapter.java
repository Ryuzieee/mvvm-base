package com.example.ryuji_mvvm_architecture.sub.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ryuji_mvvm_architecture.R;

import java.util.ArrayList;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;

    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder rowHolder = new ItemRowHolder(view);
        return rowHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {
        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();
        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);
        itemRowHolder.recyclerViewList.setHasFixedSize(true);
        itemRowHolder.recyclerViewList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recyclerViewList.setAdapter(itemListDataAdapter);
       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected RecyclerView recyclerViewList;

        public ItemRowHolder(View view) {
            super(view);
            this.recyclerViewList = (RecyclerView) view.findViewById(R.id.recycler_view_list);
        }

    }

}