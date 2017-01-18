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
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;

/**
 * Created by Riya on 10-Jan-17.
 */

public class ComicCardListViewHolder extends RecyclerView.ViewHolder {

    RecyclerView recyclerview_comic_card_list;
    HomeAdapter homeAdapter;
    List<SingleItemModel> singleItemModels;
    List<CommonRecyclerItem> recyclerItems;
    public ComicCardListViewHolder(View itemView) {
        super(itemView);
        recyclerview_comic_card_list=(RecyclerView)itemView.findViewById(R.id.recyclerview_comic_card_list);
    }


    public void bindCRItem(Context context, CommonRecyclerItem commonRecyclerItem){
        singleItemModels = (List<SingleItemModel>)commonRecyclerItem.getItem();
        recyclerview_comic_card_list.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        prepareRecyclerItems();
        homeAdapter = new HomeAdapter(context,recyclerItems);
        recyclerview_comic_card_list.setAdapter(homeAdapter);
    }

    private void prepareRecyclerItems() {
        recyclerItems=new ArrayList<>();
        for (SingleItemModel singleItemModel : singleItemModels) {
            recyclerItems.add(new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_ITEM,singleItemModel));
        }
    }
}
