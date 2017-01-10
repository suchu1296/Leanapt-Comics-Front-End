package in.informationworks.learnaptcomic.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.CoverItem;
import in.informationworks.learnaptcomic.R;

/**
 * Created by Riya on 10-Jan-17.
 */

public class CoverItemListViewHolder extends RecyclerView.ViewHolder {

    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    List<CoverItem> coverItems;
    List<CommonRecyclerItem> recyclerItems;
    public CoverItemListViewHolder(View itemView) {
        super(itemView);
        recyclerView=(RecyclerView)itemView.findViewById(R.id.recyclerView);
    }


    public void bindCRItem(Context context, CommonRecyclerItem commonRecyclerItem){
        coverItems = (List<CoverItem>)commonRecyclerItem.getItem();
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        prepareRecyclerItems();
        homeAdapter = new HomeAdapter(context,recyclerItems);
        recyclerView.setAdapter(homeAdapter);
    }

    private void prepareRecyclerItems() {
        recyclerItems=new ArrayList<>();
        for (CoverItem coverItem : coverItems) {
            recyclerItems.add(new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_COVER,coverItem));
        }
    }
}
