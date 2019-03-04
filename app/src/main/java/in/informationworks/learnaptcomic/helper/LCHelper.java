package in.informationworks.learnaptcomic.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import in.informationworks.learnaptcomic.Activities.LoginActivity;
import in.informationworks.learnaptcomic.Models.AppStorageAgent;

import static in.informationworks.learnaptcomic.Models.AppStorageAgent.getSharedStoredString;

/**
 * Created by Riya on 23-Mar-17.
 */


public class LCHelper
{

    private static String networkIp =  "192.168.2.13";

    public static String getNetworkIp() {
        return networkIp;
    }


    public static void userLogoutFromApp(final Context context)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://"+networkIp+":3000/api/mobile/v1/users/logout";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Logout Successful"))
                        {
                            AppStorageAgent.removeEntries(context);
                            String retrivedEmail11 = AppStorageAgent.getSharedStoredString("responseEmail",context);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",String.valueOf(AppStorageAgent.getSharedStoredInt("responseId",context)));
                return params;
            }
        };

        queue.add(stringRequest);
    }
    public static void showAlertDialogBox(final Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("You need to login..")
                .setPositiveButton("login", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent login = new Intent(context,LoginActivity.class);
                        context.startActivity(login);
                    }
                })
                .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }


}
