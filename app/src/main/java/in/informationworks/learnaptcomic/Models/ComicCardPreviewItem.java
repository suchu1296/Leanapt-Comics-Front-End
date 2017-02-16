package in.informationworks.learnaptcomic.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riya on 15-Feb-17.
 */

public class ComicCardPreviewItem {
    @SerializedName("thumb_image_url")
    @Expose
    private String thumbImageUrl;

    public String getThumbImageUrl() {
        // return "https://s3.amazonaws.com/learnapt/uploads/item_image/image/392/thumb/From+Trade+to+Territory2.jpg";
        return thumbImageUrl;
    }

    public void setThumbImageUrl(String thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }
}
