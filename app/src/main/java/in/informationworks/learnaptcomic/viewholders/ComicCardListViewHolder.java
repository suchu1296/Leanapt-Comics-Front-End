package in.informationworks.learnaptcomic.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Activities.FeaturedComicsActivity;
import in.informationworks.learnaptcomic.Activities.HomeActivity;
import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.CoverItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;

import static in.informationworks.learnaptcomic.Models.SingleItemModel.COMIC_TYPE_FEATURED;
import static in.informationworks.learnaptcomic.Models.SingleItemModel.COMIC_TYPE_POPULAR;

/**
 * Created by Riya on 10-Jan-17.
 */

public class ComicCardListViewHolder extends RecyclerView.ViewHolder {

    RecyclerView recyclerview_comic_card_list;
    HomeAdapter homeAdapter;
    List<SingleItemModel> singleItemModels;
    List<CommonRecyclerItem> recyclerItems;
    /** Android Views **/
    TextView catagoryLabel;
    Button seeAllButton;
    /** Android Views **/

    /**
     * Binds XML views
     * Call this function after setContentView() in onCreate().
     **/
    private void bindViews(){

    }
    public ComicCardListViewHolder(View itemView) {
        super(itemView);
        recyclerview_comic_card_list=(RecyclerView)itemView.findViewById(R.id.recyclerview_comic_card_list);
        catagoryLabel = (TextView) itemView.findViewById(R.id.catagory_label);
        seeAllButton = (Button) itemView.findViewById(R.id.see_all_button);
    }


    public void bindCRItem(Context context, CommonRecyclerItem commonRecyclerItem){
        singleItemModels = (List<SingleItemModel>) commonRecyclerItem.getItem();
        recyclerview_comic_card_list.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        prepareRecyclerItems();
        homeAdapter = new HomeAdapter(context,recyclerItems);
        recyclerview_comic_card_list.setAdapter(homeAdapter);
        setupHeader(context,commonRecyclerItem);
    }

    /**
     *
     * Sets up comic title and click for see all
     * @param commonRecyclerItem
     */
    private void setupHeader(final Context context, CommonRecyclerItem commonRecyclerItem) {
        if(commonRecyclerItem.getOptions()!=null && commonRecyclerItem.getOptions().size()>0) {
            //case : featured list
            switch (((String) commonRecyclerItem.getOptions().get(0))) {
                case COMIC_TYPE_FEATURED:
                    catagoryLabel.setText("Featured Comics");
                    seeAllButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(context,FeaturedComicsActivity.class);
                            i.putExtra("EXTRA_MODE",1);
                            context.startActivity(i);
                        }
                    });
                    break;
                case COMIC_TYPE_POPULAR:
                    catagoryLabel.setText("Popular Comics");
                    seeAllButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(context,FeaturedComicsActivity.class);
                            i.putExtra("EXTRA_MODE",2);
                            context.startActivity(i);
                        }
                    });
                    break;

            }
        }
    }

    private void prepareRecyclerItems() {
        recyclerItems=new ArrayList<>();
        for (SingleItemModel singleItemModel : singleItemModels) {
            recyclerItems.add(new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_ITEM,singleItemModel));
        }
    }
}
