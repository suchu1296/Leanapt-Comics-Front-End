package in.informationworks.learnaptcomic.Models;

/**
 * Created by Riya on 10-Jan-17.
 */

public class CommonRecyclerItem {
    final public static int TYPE_COVER_LIST=1,TYPE_SECTION_DATA = 2, TYPE_SINGLE_COVER=3, TYPE_SINGLE_ITEM=4;
    int itemType;
    Object item;

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
}
