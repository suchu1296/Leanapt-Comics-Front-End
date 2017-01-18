package in.informationworks.learnaptcomic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.informationworks.learnaptcomic.Adapters.HomeAdapter;
import in.informationworks.learnaptcomic.Models.CommonRecyclerItem;
import in.informationworks.learnaptcomic.Models.SingleItemModel;

public class FeaturedComicsActivity extends AppCompatActivity {
    HomeAdapter homeAdapter;
    ArrayList<CommonRecyclerItem> recyclerItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_comics);
        recyclerItems = new ArrayList<>();
        createAllFeaturedComicList();
        RecyclerView recyclerview_featured_comics = (RecyclerView) findViewById(R.id.recyclerview_featured_comics);
       // recyclerview_featured_comics.setHasFixedSize(true);
        homeAdapter = new HomeAdapter(this,recyclerItems);
        recyclerview_featured_comics.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerview_featured_comics.setAdapter(homeAdapter);



    }

    private void createAllFeaturedComicList() {

        List singleItems=new ArrayList();
        for (int i = 0; i < 21; i++) {

                singleItems.add(new SingleItemModel());
                CommonRecyclerItem i1 = new CommonRecyclerItem(CommonRecyclerItem.TYPE_SINGLE_ITEM, singleItems);
                recyclerItems.add(i1);

        }
    }
    public class HttpHandler()
    {

    }

}
