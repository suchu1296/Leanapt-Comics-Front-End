package in.informationworks.learnaptcomic.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.informationworks.learnaptcomic.Activities.ComicDetailsActivity;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;

/**
 * Created by Riya on 15-Mar-17.
 */

public class HorizontalComicCardViewHolder extends RecyclerView.ViewHolder  {
    ImageView horizontalComicCardImage;
    TextView horizontalComicCardText;
    TextView horizontalComicPageCount;
    TextView horizontalComicPaid;
    String id;
    public HorizontalComicCardViewHolder(View itemView) {
        super(itemView);
        horizontalComicCardImage=(ImageView)itemView.findViewById(R.id.horizontal_comic_card_image);
        horizontalComicCardText=(TextView)itemView.findViewById(R.id.horizontal_comic_card_text);
        horizontalComicPageCount=(TextView)itemView.findViewById(R.id.page_count);
        horizontalComicPaid = (TextView)itemView.findViewById(R.id.free_paid);
    }

    public void bindCRItem(Context context, CommonRecyclerItem recyclerItem)
    {
        final SingleItemModel singleItemModel = (SingleItemModel)recyclerItem.getItem();
        horizontalComicCardText.setText(singleItemModel.getName());
        horizontalComicPageCount.setText(String.valueOf(singleItemModel.getPagesCount()));
        boolean ispaid = singleItemModel.getPaid();
        if(ispaid)
        {
          horizontalComicPaid.setText("FREE");
        }
        else
        {
            horizontalComicPaid.setText("PAID");
        }


        Picasso.with(context).load(singleItemModel.getImageUrl()).error(R.drawable.ic_menu_manage).into(horizontalComicCardImage);

        itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {

                Intent intent = new Intent(v.getContext(), ComicDetailsActivity.class);
                intent.putExtra(SingleItemModel.EXTRA_ID,singleItemModel.getId());
                intent.putExtra(SingleItemModel.EXTRA_IMAGE_URL,singleItemModel.getImageUrl());
                intent.putExtra(SingleItemModel.EXTRA_NAME,singleItemModel.getName());
                v.getContext().startActivity(intent);

            }

        });


    }
}
