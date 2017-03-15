package in.informationworks.learnaptcomic.Models;

import java.util.List;

/**
 * Created by Riya on 10-Jan-17.
 */

public class CommonRecyclerItem {
    final public static int TYPE_COVER_LIST=1,TYPE_SECTION_DATA = 2, TYPE_SINGLE_COVER=3, TYPE_SINGLE_ITEM=4,TYPE_SINGLE_GRID_IMAGE=5,TYPE_GRID_IMAGE_LIST=6,TYPE_SINGLE_PREVIEW_IMAGE=7,TYPT_HORIZONTAL_COMIC_CARD=8;
    int itemType;
    Object item;
    List<Object> options;

    public CommonRecyclerItem(int itemType, Object item) {
        this.itemType = itemType;
        this.item = item;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public List<Object> getOptions() {
        return options;
    }

    public void setOptions(List<Object> options) {
        this.options = options;
    }
}
