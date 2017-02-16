package in.informationworks.learnaptcomic.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.ComicCardPreviewItem;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.CoverItem;
import in.informationworks.learnaptcomic.R;

/**
 * Created by Riya on 15-Feb-17.
 */

public class ComicCardPreviewListViewHolder extends RecyclerView.ViewHolder {

    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    List<ComicCardPreviewItem> comicCardPreviewItems;
    List<CommonRecyclerItem> recyclerItems;
    public ComicCardPreviewListViewHolder(View itemView) {
        super(itemView);
        recyclerView=(RecyclerView)itemView.findViewById(R.id.grid_thumbimage_recyclerView);
    }

    public void bindCRItem(Context context, CommonRecyclerItem commonRecyclerItem){
        //comicCardPreviewItems = (List<ComicCardPreviewItem>)commonRecyclerItem.getItem();
        //recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        //prepareRecyclerItems();
        //homeAdapter = new HomeAdapter(context,recyclerItems);
        //recyclerView.setAdapter(homeAdapter);
    }

    private void prepareRecyclerItems() {
        recyclerItems=new ArrayList<>();
        for (ComicCardPreviewItem comicCardPreviewItem : comicCardPreviewItems) {
            recyclerItems.add(new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_PREVIEW_IMAGE,comicCardPreviewItem));
        }
    }
}
