package in.informationworks.learnaptcomic.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import in.informationworks.learnaptcomic.R;

public class CartActivity extends AppCompatActivity {

    Toolbar cartActivityToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        bindViews();
        setCartActivityToolbar();
        setStatusBarColour();
    }

    private void setCartActivityToolbar() {

            setSupportActionBar(cartActivityToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            cartActivityToolbar.setTitle("Your Cart");
            cartActivityToolbar.setTitleTextColor(0xFFFFFFFF);
            cartActivityToolbar.setNavigationIcon(R.drawable.leftaero);
            cartActivityToolbar.setNavigationOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    CartActivity.this.finish();
                }
            });



    }

    private void bindViews() {
        cartActivityToolbar = (Toolbar) findViewById(R.id.cart_activity_toolbar);
    }

    public void setStatusBarColour() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

}
