package in.informationworks.learnaptcomic.Activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import in.informationworks.learnaptcomic.R;
import jp.wasabeef.blurry.Blurry;

public class ProfileActivity extends AppCompatActivity {
Context context;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       /* imageView = (ImageView) findViewById(R.id.blurprofilebackground);


        imageView.post(new Runnable() {
            @Override
            public void run() {
                Blurry.with(ProfileActivity.this)
                        .radius(25)
                        .sampling(6)
                        .async()
                        .animate(500)
                        .onto((ViewGroup) findViewById(R.id.blurprofilebackground));
            }
        });*/
    }
}
