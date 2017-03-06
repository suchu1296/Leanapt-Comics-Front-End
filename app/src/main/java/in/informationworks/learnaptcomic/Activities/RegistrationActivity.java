package in.informationworks.learnaptcomic.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.informationworks.learnaptcomic.Models.ComicCardPreviewItem;
import in.informationworks.learnaptcomic.R;

public class RegistrationActivity extends AppCompatActivity {

    RelativeLayout activityRegistration;
    EditText email;
    EditText password;
    Button button2;
    String userEmail,userPassword;
    JsonObject user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        bindViews();
    }

    private void bindViews()
    {
        activityRegistration = (RelativeLayout) findViewById(R.id.activity_registration);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        button2 = (Button) findViewById(R.id.button2);
    }



    public void onRegisterClick(View view)
    {
        userEmail=email.getText().toString();
        userPassword=password.getText().toString();
        String emailPattern = "[a-zA-z0-9.-_]+@[a-z]+\\.+[a-z]+";
        if(userEmail.matches(emailPattern)) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.2.30:3000/api/mobile/v1/users/registration";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            // Toast.makeText(RegistrationActivity.this,response, Toast.LENGTH_SHORT).show();
                            user = (new JsonParser()).parse(response).getAsJsonObject();
                            String email = user.get("email").getAsString();
                            Toast.makeText(RegistrationActivity.this, email, Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    //  mTextView.setText("That didn't work!");
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user[email]", userEmail);
                    params.put("user[password]", userPassword);
                    return params;
                }
            };

            queue.add(stringRequest);
        }
        else
        {
            email.setError("Invalid Email");
        }


    }


}
