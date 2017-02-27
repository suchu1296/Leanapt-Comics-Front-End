package in.informationworks.learnaptcomic.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.CoverItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;
import in.informationworks.learnaptcomic.R;

public class HomeActivity extends AppCompatActivity {


    ArrayList<CommonRecyclerItem> recyclerItems;
    HomeAdapter homeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerItems = new ArrayList<>();
        createCoverData();
        RecyclerView recyclerview_comic_card_list = (RecyclerView) findViewById(R.id.main_recycler_view);
        homeAdapter = new HomeAdapter(this,recyclerItems);
        recyclerview_comic_card_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerview_comic_card_list.setAdapter(homeAdapter);


    }

    private void createCoverData() {
        List coverItems = new ArrayList();
        for (int i = 0; i < 5; i++) {
            coverItems.add(new CoverItem());
        }
        List singleitems=new ArrayList();
        for (int i = 0; i < 7; i++) {
            singleitems.add(new SingleItemModel());
        }
        CommonRecyclerItem cri=new CommonRecyclerItem(CommonRecyclerItem.TYPE_COVER_LIST,coverItems);
        CommonRecyclerItem cri1=new CommonRecyclerItem(CommonRecyclerItem.TYPE_SECTION_DATA,singleitems);
        CommonRecyclerItem cri2=new CommonRecyclerItem(CommonRecyclerItem.TYPE_SECTION_DATA,singleitems);


        recyclerItems.add(cri);
        recyclerItems.add(cri1);
        recyclerItems.add(cri2);
    }

    /* public void createTemporaryData()
       {
           for(int i=1;i<=2;i++)
           {
               SectionDataModel sectionDataModel = new SectionDataModel();
               sectionDataModel.setHeaderTitle("List:"+i);
               ArrayList<SingleItemModel>  singleItem = new ArrayList<SingleItemModel>();
               for (int j = 0; j < 5; j++) {
                   singleItem.add(new SingleItemModel("Hii " + j , "Url " + j));
                  // int id = getResources().getIdentifier("LearnaptComic:drawable/" + step1, null, null);
                  // comic_card_image1=(ImageView) findViewById(R.id.comic_card_image);
                  // comic_card_image1.setBackground(getResources().getDrawable(R.drawable.step1));
               }

               sectionDataModel.setAllItemsInSection(singleItem);
               allSampleData.add(sectionDataModel);
           }
       }*/
    public void On_SeeAll_Button_Click(View view)
    {
        Intent featuredComicsIntent = new Intent(this,FeaturedComicsActivity.class);
        startActivity(featuredComicsIntent);
    }


}
