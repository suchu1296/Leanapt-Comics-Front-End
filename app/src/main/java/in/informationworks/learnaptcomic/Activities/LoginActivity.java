package in.informationworks.learnaptcomic.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

import in.informationworks.learnaptcomic.Models.AppStorageAgent;
import in.informationworks.learnaptcomic.R;
import in.informationworks.learnaptcomic.helper.LCHelper;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout activityLogin;
    EditText loginEmail;
    EditText loginPassword;
    Button loginButton;
    String userLoginEmail,userLoginPassword;
    JsonObject userLogin;
    Context context;
    String responseEmail;
    int responseId;
    Boolean isLoggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
    }
    private void bindViews(){
        activityLogin = (RelativeLayout) findViewById(R.id.activity_login);
        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
    }

    public void onLoginClick(View view)
    {
        userLoginEmail=loginEmail.getText().toString();
        userLoginPassword=loginPassword.getText().toString();
        if(userLoginEmail.length() == 0 || userLoginPassword.length() == 0)
        {
            if(userLoginEmail.length() == 0 && userLoginPassword.length() == 0)
            {
                loginEmail.setError("Required..");
                loginPassword.setError("Required..");
            }
            else if(userLoginEmail.length() == 0)
            {
                loginEmail.setError("Required..");
            }
            else
            {
                loginPassword.setError("Required..");
            }
        }
        else {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://"+ LCHelper.getNetworkIp()+":3000/api/mobile/v1/users/login";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(LoginActivity.this,response, Toast.LENGTH_LONG).show();
                            if (response.equals("Password Invalid")) {
                                loginPassword.setError("Password Invalid");
                            } else if (response.equals("Email Invalid")) {
                                loginEmail.setError("Email Invalid");
                            } else {
                                userLogin = (new JsonParser()).parse(response).getAsJsonObject();
                                responseEmail = userLogin.get("email").getAsString();
                                responseId = userLogin.get("id").getAsInt();
                                isLoggedIn=true;
                                Toast.makeText(LoginActivity.this, userLogin.toString(), Toast.LENGTH_LONG).show();
                                AppStorageAgent.setSharedStoreString("responseEmail",responseEmail,getApplicationContext());
                                AppStorageAgent.setSharedStoreInt("responseId",responseId,getApplicationContext());
                                AppStorageAgent.setSharedStoreBoolean("isLoggedIn",isLoggedIn,getApplicationContext());
                                LoginActivity.this.finish();
                               // Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               // startActivity(intent);
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
                    params.put("email", userLoginEmail);
                    params.put("password", userLoginPassword);
                    return params;
                }
            };

            queue.add(stringRequest);
        }

    }
    public void onSignUp(View view)
    {
        Intent signUp = new Intent(this,RegistrationActivity.class);
        startActivity(signUp);
    }

    public void onForgotPassword(View view)
    {
        Intent intent = new Intent(this,ForgotPasswordActivity.class);
        startActivity(intent);
    }

}
