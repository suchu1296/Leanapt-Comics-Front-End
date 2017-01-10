package in.informationworks.learnaptcomic.Models;

import java.util.ArrayList;

/**
 * Created by Riya on 04-Jan-17.
 */

public class SingleCoverMain {

    private ArrayList<SingleItemModel> allItemsInSection;


    public SingleCoverMain() {

    }
    public SingleCoverMain( ArrayList<SingleItemModel> allItemsInSection) {

        this.allItemsInSection = allItemsInSection;
    }





    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
