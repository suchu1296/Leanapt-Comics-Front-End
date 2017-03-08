package in.informationworks.learnaptcomic.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import in.informationworks.learnaptcomic.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText forgotPasswordEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        bindViews();
    }
    private void bindViews(){
        forgotPasswordEmail = (EditText) findViewById(R.id.forgot_password_email);
    }
    public  void onGetInstructionsClick(View view)
    {
        Toast.makeText(this,"On getinstructions click",Toast.LENGTH_SHORT).show();
    }

}
