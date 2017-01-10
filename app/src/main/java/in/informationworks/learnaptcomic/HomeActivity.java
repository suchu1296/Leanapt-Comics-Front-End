package in.informationworks.learnaptcomic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.CoverRecyclerviewAdapter;
import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.CoverItem;
import in.informationworks.learnaptcomic.Models.SectionDataModel;
import in.informationworks.learnaptcomic.Models.SingleCoverMain;
import in.informationworks.learnaptcomic.Models.SingleItemModel;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView itemImage1;
    ArrayList<SectionDataModel> allSampleData;
    ArrayList<SingleCoverMain> allData;
    ArrayList<CommonRecyclerItem> recyclerItems;
    HomeAdapter homeAdapter;
    public int[] images = {
            // step1,
            //R.drawable.step2,
            //R.drawable.step3,
            //R.drawable.step4,
            //R.drawable.step5
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
       /* allSampleData = new ArrayList<SectionDataModel>();
        allData= new ArrayList<SingleCoverMain>();
*/
        /*if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            toolbar.setTitle("Comics");

        }*/
        recyclerItems = new ArrayList<>();
        //getSupportActionBar().setTitle("Comics");
        createCoverData();
        //createSectionData();
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.main_recycler_view);
        homeAdapter = new HomeAdapter(this,recyclerItems);
        /*CoverRecyclerviewAdapter coverRecyclerviewAdapter = new CoverRecyclerviewAdapter(this, allData);*/
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView1.setAdapter(homeAdapter);

        /*createTemporaryData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerViewDataAdapter recyclerViewDataAdapter = new RecyclerViewDataAdapter(this,allSampleData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(recyclerViewDataAdapter);*/


    }

    private void createCoverData() {
        List coverItems = new ArrayList();
        for (int i = 0; i < 5; i++) {
            coverItems.add(new CoverItem());
        }
        CommonRecyclerItem cri=new CommonRecyclerItem(CommonRecyclerItem.TYPE_COVER_LIST,coverItems);
        recyclerItems.add(cri);
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
                  // itemImage1=(ImageView) findViewById(R.id.itemImage);
                  // itemImage1.setBackground(getResources().getDrawable(R.drawable.step1));
               }

               sectionDataModel.setAllItemsInSection(singleItem);
               allSampleData.add(sectionDataModel);
           }
       }*/
    public void createSectionData() {
        for (int i = 1; i <= 3; i++) {
            SingleCoverMain singleCoverMain = new SingleCoverMain();
            // SingleCoverMain.setHeaderTitle("List:"+i);
            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j < 7; j++) {
                singleItem.add(new SingleItemModel("hello " + j, "Url " + j));
                // int id = getResources().getIdentifier("LearnaptComic:drawable/" + step1, null, null);
                // itemImage1=(ImageView) findViewById(R.id.itemImage);
                // itemImage1.setBackground(getResources().getDrawable(R.drawable.step1));
            }

            singleCoverMain.setAllItemsInSection(singleItem);
            allData.add(singleCoverMain);
        }
    }


}
