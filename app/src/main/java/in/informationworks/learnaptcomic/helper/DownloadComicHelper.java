package in.informationworks.learnaptcomic.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import in.informationworks.learnaptcomic.Activities.TryDownloadActivity;

/**
 * Created by Riya on 29-Mar-17.
 */

public class DownloadComicHelper extends AsyncTask<String,Integer,String> {


    private Context context;
    //private ProgressDialog progressDialog;
    static HttpURLConnection connection;
    Bitmap bitmap;
    String internalRoot;
    String name =  "hello.jpg";

    private String TAG = "asyncTask";

    public DownloadComicHelper(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
      /* for progress dialog */
        // progressDialog = ProgressDialog.show(context,
        // "Wait", "downloading");
    }
    @Override
    protected String doInBackground(String... params) {
        internalRoot = context.getFilesDir().toString();

        try {

            final URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
           // return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {

            File myDir = new File(internalRoot+"/c1");

            if (!myDir.exists()) {
                myDir.mkdirs();
            }

            //String name = new Date().toString() + ".jpg";
            myDir = new File(myDir, name);
            FileOutputStream out = new FileOutputStream(myDir);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);


            out.flush();
            out.close();
        } catch(Exception e){
            // some action
        }

       // Toast.makeText(context,bitmap.toString(),Toast.LENGTH_LONG).show();



        return internalRoot;

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {

            final URL url = new URL(src);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
       // progressbar.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String imagePath) {
        super.onPostExecute(imagePath);
       // saveImage(context,imagePath);
        TryDownloadActivity.loadImageFromStorage(imagePath+"/c1");

    }

    public void saveImage(Context context, Bitmap bitmap){
        try {

            File myDir = new File(internalRoot+"/c1");

            if (!myDir.exists()) {
                myDir.mkdirs();
            }

            //String name = new Date().toString() + ".jpg";
            myDir = new File(myDir, name);
            FileOutputStream out = new FileOutputStream(myDir);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);


            out.flush();
            out.close();
        } catch(Exception e){
            // some action
        }
    }




}
