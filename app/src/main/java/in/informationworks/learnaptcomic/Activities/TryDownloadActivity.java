package in.informationworks.learnaptcomic.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import in.informationworks.learnaptcomic.R;
import in.informationworks.learnaptcomic.helper.DownloadComicHelper;

public class TryDownloadActivity extends AppCompatActivity {

    Context context;
    ImageView imageView;
    String IMAGE_URL;
    String name =  "hello.jpg";
    String root = Environment.getExternalStorageDirectory().toString();
    Bitmap image;
    ImageLoader imageLoader;
    URL url;
    static ImageView img;
    Uri uri;
    static HttpURLConnection connection;
    Bitmap bitmap;
    String internalRoot;// = getApplicationContext().getFilesDir().toString();
    DownloadComicHelper d1 = new DownloadComicHelper(context);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_download);
        internalRoot = getApplicationContext().getFilesDir().toString();
        IMAGE_URL = "https://www.wired.com/wp-content/uploads/2015/09/google-logo-1200x630.jpg";
        imageLoader = ImageLoader.getInstance();
         img=(ImageView)findViewById(R.id.tryimageView);

    }

    public void onDownloadClick(View view)
    {
        //imageView = (ImageView) findViewById(R.id.imageView);




        /*try {
             url = new URL(IMAGE_URL);
             uri=Uri.parse(IMAGE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
       // uri=Uri.parse(IMAGE_URL);
        //Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_LONG).show();
        //Bitmap bitmap = imageLoader.loadImageSync(uri.toString());

       // new MyAsyncTask(MainActivity.this).execute(IMAGE_URL);
        new DownloadComicHelper(TryDownloadActivity.this).execute(IMAGE_URL);

        /*try {
            url = new URL( "https://www.wired.com/wp-content/uploads/2015/09/google-logo-1200x630.jpg");
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        // Toast.makeText(getApplicationContext(),internalRoot,Toast.LENGTH_LONG).show();
        //Bitmap Icon = BitmapFactory.decodeResource(getResources(), R.drawable.a2);
       // Bitmap bitmap = StringToBitMap("https://www.wired.com/wp-content/uploads/2015/09/google-logo-1200x630.jpg");
       // bitmap = getBitmapFromURL("https://www.wired.com/wp-content/uploads/2015/09/google-logo-1200x630.jpg");
        //Toast.makeText(getApplicationContext(),bitmap.toString(),Toast.LENGTH_LONG).show();
      //  saveImage(context,bitmap);

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

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
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
        //EXTERNAL
        //Toast.makeText(context,name,Toast.LENGTH_LONG).show();
       /* try {

            File myDir = new File(root + "/data");

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
        }*/


    public void onRetriveClick(View view)
    {
        //loadImageFromStorage(internalRoot + "/c1");
    }

    public static void loadImageFromStorage(String path)
    {
        //EXTERNAL
        /*try {

            //Toast.makeText(context,path,Toast.LENGTH_LONG).show();
            File f=new File(path, name);

            Bitmap b = BitmapFactory.decodeFile(path+"/"+name);
           // Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=(ImageView)findViewById(R.id.tryimageView);
            img.setImageBitmap(b);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

         try {

            //Toast.makeText(context,path,Toast.LENGTH_LONG).show();
            File f=new File(path, "hello.jpg");

            Bitmap b = BitmapFactory.decodeFile(path+"/hello.jpg");
           // Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            img.setImageBitmap(b);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
