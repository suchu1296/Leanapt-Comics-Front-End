package in.informationworks.learnaptcomic.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.R;

/**
 * Created by Riya on 10-Jan-17.
 */

public class SingleCoverViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    public SingleCoverViewHolder(View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.img_cover);
    }

    public void bindCRItem(Context context, CommonRecyclerItem commonRecyclerItem){

    }
}
