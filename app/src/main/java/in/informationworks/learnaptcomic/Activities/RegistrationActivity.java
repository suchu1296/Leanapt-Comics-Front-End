package in.informationworks.learnaptcomic.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Patterns;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.informationworks.learnaptcomic.Models.AppStorageAgent;
import in.informationworks.learnaptcomic.Models.ComicCardPreviewItem;
import in.informationworks.learnaptcomic.R;
import in.informationworks.learnaptcomic.helper.LCHelper;

public class RegistrationActivity extends AppCompatActivity
{

    RelativeLayout activityRegistration;
    EditText email,name,password,confirmPassword,mobileNo;
    Button button2;
    String userName,userEmail,userPassword,userConfirmPassword,userMobileNo;
    JsonObject user;
    String responseEmail;
    int responseId;
    Boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        bindViews();
    }

    private void bindViews()
    {
        activityRegistration = (RelativeLayout) findViewById(R.id.activity_registration);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        mobileNo = (EditText) findViewById(R.id.mobile_no);
        button2 = (Button) findViewById(R.id.button2);
    }

    public boolean validation()
    {
        userName = name.getText().toString();
        userEmail=email.getText().toString();
        userPassword=password.getText().toString();
        userConfirmPassword=confirmPassword.getText().toString();
        userMobileNo=mobileNo.getText().toString();

        boolean isAllOK = true;
        boolean isTemp = true;
        //First name can not be blank
        if (userName.length() == 0)
        {
            isAllOK = false;
            name.setError(getString(R.string.blankerror));
        }


        //email
        if (userEmail.length() > 0)
        {
            String emailPattern = "[a-zA-z0-9.-_]+@[a-z]+\\.+[a-z]+";
            if(userEmail.matches(emailPattern)) {
                isTemp=true;
            } else
            {
                email.setError(getString(R.string.emailerror));
                isAllOK = false;
            }
        }
        else
        {
            isAllOK = false;
            email.setError(getString(R.string.blankerror));
        }

        //password
        if(userPassword.length()>0)
        {
            if (userPassword.length() >= 6 && userPassword.length() <= 20) {
                isTemp = true;
            } else {
                isAllOK = false;
                password.setError("Password length must be between 6 to 20");
            }
        }
         else
        {
            password.setError(getString(R.string.blankerror));
        }

        //confirm password
        if(userPassword.equals(userConfirmPassword) )
        {
            isTemp=true;
        }
        else
        {
            isAllOK=false;
            confirmPassword.setError(getString(R.string.confirmPassworderror));
        }

        if (userMobileNo.length() > 0)
        {
            String mobilePattern = "[1-9]{1}[0-9]{9}";
            if(userMobileNo.matches(mobilePattern))
            {
                isTemp=true;
            }
            else
            {
                mobileNo.setError(getString(R.string.mobileNoerror));
                isAllOK = false;
            }
        }
        else
        {
            isAllOK = false;
            mobileNo.setError(getString(R.string.blankerror));
        }
        return isAllOK;
    }



    public void onRegisterClick(View view)
    {
        if(validation())
        {
            Toast.makeText(this, "Validation Successfull", Toast.LENGTH_LONG).show();
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://"+ LCHelper.getNetworkIp()+":3000/api/mobile/v1/users/registration";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Email already exists")) {
                                email.setError("Email already registered");
                            }
                            else {
                                user = (new JsonParser()).parse(response).getAsJsonObject();
                                responseEmail = user.get("email").getAsString();
                                responseId = user.get("id").getAsInt();
                                isLoggedIn = true;
                                //Toast.makeText(RegistrationActivity.this, email, Toast.LENGTH_LONG).show();
                                AppStorageAgent.setSharedStoreString("responseEmail", responseEmail, getApplicationContext());
                                AppStorageAgent.setSharedStoreInt("responseId", responseId, getApplicationContext());
                                AppStorageAgent.setSharedStoreBoolean("isLoggedIn", isLoggedIn, getApplicationContext());
                                RegistrationActivity.this.finish();
                                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user[name]", userName);
                    params.put("user[email]", userEmail);
                    params.put("user[password]", userPassword);
                    params.put("user[mobile_no]", userMobileNo);
                    return params;
                }
            };

            queue.add(stringRequest);
        }

    }




}



