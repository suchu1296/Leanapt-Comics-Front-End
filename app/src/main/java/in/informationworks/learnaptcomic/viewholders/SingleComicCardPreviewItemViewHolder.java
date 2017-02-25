package in.informationworks.learnaptcomic.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import in.informationworks.learnaptcomic.Activities.ComicImagePlayerActivity;
import in.informationworks.learnaptcomic.Activities.ComicImagesGridActivity;
import in.informationworks.learnaptcomic.Models.ComicCardGridItem;
import in.informationworks.learnaptcomic.Models.ComicCardPreviewItem;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.R;

import static in.informationworks.learnaptcomic.R.id.preview_thumbimg;

/**
 * Created by Riya on 25-Feb-17.
 */

public class SingleComicCardPreviewItemViewHolder extends RecyclerView.ViewHolder {
    ImageView previewThumbImageView;
    public SingleComicCardPreviewItemViewHolder(View itemView) {
        super(itemView);
        previewThumbImageView=(ImageView)itemView.findViewById(preview_thumbimg);
    }

    public void bindCRItem(final Context context, CommonRecyclerItem commonRecyclerItem){
        final ComicCardPreviewItem comicCardPreviewItem = (ComicCardPreviewItem) commonRecyclerItem.getItem();
        Picasso.with(context).load(comicCardPreviewItem.getThumbImageUrl()).error(R.drawable.ic_menu_manage).into(previewThumbImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Toast.makeText(context,"Preview click",Toast.LENGTH_LONG).show();

                }

            });

    }

}
