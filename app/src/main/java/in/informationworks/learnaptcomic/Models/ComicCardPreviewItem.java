package in.informationworks.learnaptcomic.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riya on 15-Feb-17.
 */

public class ComicCardPreviewItem {
    public static final String EXTRA_ORDER = "extraOrder";
    public static final String EXTRA_ID = "extraID";
    @SerializedName("thumb_image_url")
    @Expose
    private String thumbImageUrl;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbImageUrl() {
        // return "https://s3.amazonaws.com/learnapt/uploads/item_image/image/392/thumb/From+Trade+to+Territory2.jpg";
        return thumbImageUrl;
    }

    public void setThumbImageUrl(String thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
