package in.informationworks.learnaptcomic.Models;

import android.content.Context;
import android.widget.ImageView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import in.informationworks.learnaptcomic.R;

/**
 * Created by Riya on 04-Jan-17.
 */

public class SingleItemModel {

    public static final String COMIC_TYPE_FEATURED="featured-comics";
    public static final String COMIC_TYPE_POPULAR="popular-comics";

    public static final String EXTRA_ID = "extraID";
    public static final String EXTRA_IMAGE_URL = "extraImageURL";
    public static final String EXTRA_NAME = "extraName";
   // public static final String PAGES_COUNT = "extraID";
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose

    private String name;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("pages_count")
    @Expose
    private int pagesCount;
    @SerializedName("paid")
    @Expose
    private boolean paid;
    @SerializedName("price")
    @Expose
    private int price;



    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
       // return "https://s3.amazonaws.com/learnapt/uploads/item_image/image/392/thumb/From+Trade+to+Territory2.jpg";
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}

