package in.informationworks.learnaptcomic.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.R;

/**
 * Created by Riya on 10-Jan-17.
 */

public class SingleCoverViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    int images[]={R.drawable.why_do_we_need_a_parliament,R.drawable.the_great_stone_face,R.drawable.from_tread_to_territory,R.drawable.natural_vegetation_and_wildlife,R.drawable.the_tsunami};
    public SingleCoverViewHolder(View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.img_cover);
    }

    public void bindCRItem(Context context, CommonRecyclerItem commonRecyclerItem){

        //Picasso.with(context).load(singleItemModel.getImageUrl()).error(R.drawable.ic_menu_manage).into(imageView);

    }
}
