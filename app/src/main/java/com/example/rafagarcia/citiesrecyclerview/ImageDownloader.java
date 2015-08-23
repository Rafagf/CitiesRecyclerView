package com.example.rafagarcia.citiesrecyclerview;

/**
 * Created by Rafael Garcia on 23/08/15.
 */
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

//Just an old regular ImageDownloader class (background process)
public class ImageDownloader {

    static ImageDownloader instance;
    private HashMap<String, Bitmap> images;

    public static ImageDownloader getInstance(){

        if(instance == null)
            instance = new ImageDownloader();

        return instance;
    }

    public ImageDownloader(){
        images = new HashMap<>();
    }

    /**
     * It returns the images hashmap
     * @return images hashmap
     */
    public HashMap<String, Bitmap> getImages(){
        return images;
    }

    /**
     * Method to download an image given an url
     * @param url the image url to download
     * @param imageView the view where introduce the selected image
     */
    public void download(String url, ImageView imageView, Context context) {

        if(!images.containsKey(url)){
            Bitmap customThumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.abc_spinner_textfield_background_material);
            BitmapDownloaderTask task = new BitmapDownloaderTask(imageView, url, images, context);
            imageView.setImageBitmap(customThumb);
            Log.d("Thumbs", "Downloading it");
            task.execute(url);
        }
        else{
            imageView.setImageBitmap(images.get(url));
            //Log.d("Thumbs", "Already downloaded");
        }
    }
}

class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    HashMap<String, Bitmap> images;
    String url;
    Context context;

    /**
     * Constructor. Create a new BitMapDownloader object.
     * @param imageView view where introduce the selected image
     */
    public BitmapDownloaderTask(ImageView imageView, String url, HashMap<String, Bitmap> images, Context context) {

        this.images = images;
        imageViewReference = new WeakReference<>(imageView);
        this.url = url;
        this.context = context;
    }

    @Override
    /**
     * Download method. Run in the task thread
     * @param params comes from the execute() call. params[0] is the url
     * @return a Bitmap with the image
     */
    protected Bitmap doInBackground(String... params) {
        return downloadBitmap(params[0]);
    }

    @Override
    /**
     * Method to associates the image to the imageView once is downloaded
     */
    protected void onPostExecute(Bitmap bitmap) {

        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null && ((String)imageView.getTag()).equals(url)) {
                if(bitmap == null){
                    Bitmap customThumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.abc_spinner_textfield_background_material);
                    imageView.setImageBitmap(customThumb);
                }

                else{
                    imageView.setImageBitmap(bitmap);
                    images.put(url, bitmap);
                }
            }
        }
    }

    /**
     * Method to download the bitmap of the desired image
     * @param url the image url
     * @return a Bitmap of the desired image
     */
    static Bitmap downloadBitmap(String url) {
        final AndroidHttpClient client = AndroidHttpClient
                .newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            // Could provide a more explicit error message for IOException or
            // IllegalStateException
            getRequest.abort();
            Log.w("ImageDownloader", "Error while retrieving bitmap from "
                    + url);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }
}