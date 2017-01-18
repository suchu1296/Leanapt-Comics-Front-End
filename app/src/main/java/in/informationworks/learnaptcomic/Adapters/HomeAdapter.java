package in.informationworks.learnaptcomic.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.R;
import in.informationworks.learnaptcomic.viewholders.CoverItemListViewHolder;
import in.informationworks.learnaptcomic.viewholders.SingleCoverViewHolder;
import in.informationworks.learnaptcomic.viewholders.ComicCardListViewHolder;
import in.informationworks.learnaptcomic.viewholders.SingleComicCardViewHolder;

/**
 * Created by Riya on 10-Jan-17.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    List<CommonRecyclerItem> recyclerItems;
    Context context;
    LayoutInflater inflater;

    public HomeAdapter(Context context, List<CommonRecyclerItem> recyclerItems) {
        this.context = context;
        this.recyclerItems = recyclerItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView;
        if(viewType==CommonRecyclerItem.TYPE_COVER_LIST){
            rootView = inflater.inflate(R.layout.vh_cover_item_list,parent,false);
            return new CoverItemListViewHolder(rootView);
        }else if(viewType == CommonRecyclerItem.TYPE_SINGLE_COVER){
            rootView = inflater.inflate(R.layout.vh_single_cover_item,parent,false);
            return new SingleCoverViewHolder(rootView);
        }else if(viewType == CommonRecyclerItem.TYPE_SINGLE_ITEM){
            rootView = inflater.inflate(R.layout.vh_single_comic_card,parent,false);
            return new SingleComicCardViewHolder(rootView);
        }else if(viewType == CommonRecyclerItem.TYPE_SECTION_DATA){
            rootView = inflater.inflate(R.layout.vh_comic_card_list,parent,false);
            return new ComicCardListViewHolder(rootView);



        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (recyclerItems.get(position).getItemType()){
            case CommonRecyclerItem.TYPE_COVER_LIST:
                ((CoverItemListViewHolder)holder).bindCRItem(context,recyclerItems.get(position));
                return;
            case CommonRecyclerItem.TYPE_SINGLE_COVER:
                ((SingleCoverViewHolder)holder).bindCRItem(context,recyclerItems.get(position));
                return;
            case CommonRecyclerItem.TYPE_SINGLE_ITEM:
                ((SingleComicCardViewHolder)holder).bindCRI(context,recyclerItems.get(position));
                return;
            case CommonRecyclerItem.TYPE_SECTION_DATA:
                ((ComicCardListViewHolder)holder).bindCRItem(context,recyclerItems.get(position));
                return;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return recyclerItems.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return recyclerItems.size();
    }
}
