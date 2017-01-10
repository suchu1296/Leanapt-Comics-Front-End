package in.informationworks.learnaptcomic.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.informationworks.learnaptcomic.Models.SectionDataModel;
import in.informationworks.learnaptcomic.Models.SingleCoverMain;
import in.informationworks.learnaptcomic.R;

/**
 * Created by Riya on 04-Jan-17.
 */



public class CoverRecyclerviewAdapter extends RecyclerView.Adapter<CoverRecyclerviewAdapter.ItemRowHolder> {

    private ArrayList<SingleCoverMain> dataList;
    private Context mContext;

    public CoverRecyclerviewAdapter(Context context, ArrayList<SingleCoverMain> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cover_list, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        //final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

       // itemRowHolder.itemTitle.setText(sectionName);

        CoverListDataAdapter itemListDataAdapter = new CoverListDataAdapter(mContext, singleSectionItems);

        itemRowHolder.main_recycler_view1.setHasFixedSize(true);
        itemRowHolder.main_recycler_view1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.main_recycler_view1.setAdapter(itemListDataAdapter);


       /* itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(v.getContext(), "click event on more, "+sectionName , Toast.LENGTH_SHORT).show();



            }
        });*/


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

       // protected TextView itemTitle;

        protected RecyclerView main_recycler_view1;

        //protected Button btnMore;



        public ItemRowHolder(View view) {
            super(view);

          //  this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.main_recycler_view1 = (RecyclerView) view.findViewById(R.id.main_recycler_view1);
           // this.btnMore= (Button) view.findViewById(R.id.btnMore);


        }

    }

}


