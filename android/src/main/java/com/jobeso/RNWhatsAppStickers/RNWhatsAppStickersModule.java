package com.jobeso.RNWhatsAppStickers;

import java.lang.NullPointerException;
import android.os.Environment;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.net.Uri;

import androidx.core.content.FileProvider;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException; // added for #373
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import android.os.AsyncTask;
// import org.jlibvips.*; // NOTE: libvip A

// NOTE and these are from another libvips wrapper
// import com.criteo.vips.Vips;
// import com.criteo.vips.VipsContext;
// import com.criteo.vips.VipsException;
// import com.criteo.vips.VipsImage;
// import com.jobeso.RNWhatsAppStickers.VipsTestUtils;
// import com.criteo.vips.enums.VipsImageFormat;
// import java.awt.*;
import java.io.IOException;

import com.arthenica.ffmpegkit;
// import com.arthenica.ffmpegkit.FFmpegKit;
// import com.arthenica.ffmpegkit.ReturnCode;

public class RNWhatsAppStickersModule extends ReactContextBaseJavaModule {
    public static final String EXTRA_STICKER_PACK_ID = "sticker_pack_id";
    public static final String EXTRA_STICKER_PACK_AUTHORITY = "sticker_pack_authority";
    public static final String EXTRA_STICKER_PACK_NAME = "sticker_pack_name";
    public static String path;
    public static String path2;
    public static final int ADD_PACK = 200;
    public static final String ERROR_ADDING_STICKER_PACK = "Could not add this sticker pack. Please install the latest version of WhatsApp before adding sticker pack";

    private final ReactApplicationContext reactContext;
    public static ReactApplicationContext reactContext2;

    public RNWhatsAppStickersModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext2 = reactContext;
        Hawk.init(reactContext).build();
        path = reactContext.getFilesDir() + "/" + "stickers_asset";
        // path2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // System.load("libs/JVips");
    }

    @Override
    public String getName() {
        return "RNWhatsAppStickers";
    }


    @ReactMethod
    public void checker(String src, String dest, Promise promise){
        // ClassLoader classLoader = VipsImage.class.getClassLoader();
        // String libName = System.mapLibraryName("VipsContext");
        // Log.d("ReactNative", "checker "+libName);
        // String path = classLoader.getResource(libName);
        // Log.d("ReactNative", "checker "+path);
        // System.load(path);
        // String src  = "a.webp";
        // String dest = "a.png";

        // WebpIO.create().toNormalImage(src, dest);

        // WebpIO.create().toWEBP("hello.png", "hello.webp");

        // Log.d("ReactNative", "checker:path "+path);
        Log.d("ReactNative", "checker:path "+path);
        // Log.d("ReactNative", "checker:path2 "+path2);
        // Set vips memory leak report at exit
        // loadLibraryFromJar("libs/JVips.jar");
        // VipsContext.setLeak(true);
        // FFmpegSession session = FFmpegKit.execute("-i file1.mp4 -c:v mpeg4 file2.mp4");
        // if (ReturnCode.isSuccess(session.getReturnCode())) {
        
        //     // SUCCESS
        
        // } else if (ReturnCode.isCancel(session.getReturnCode())) {
        
        //     // CANCEL
        
        // } else {
        
            // FAILURE
            // Log.d("ReactNative", String.format("Command failed with state %s and rc %s.%s", session.getState(), session.getReturnCode(), session.getFailStackTrace()));
        
        // }

        try {
            File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/new103.png");
            
            // byte[] contents = VipsTestUtils.getByteArray("file:///storage/emulated/0/Download/new103.png");
            byte[] contents = getByteArray(f);
 
            // Printing the byte array
            // Log.d("ReactNative",Arrays.toString(contents));
            // VipsImage image = new VipsImage(contents, contents.length);
            // int width = image.getWidth();
            // int height = image.getHeight();

            // image.thumbnailImage(new Dimension(width / 2, height / 2), true);
            // Log.d("ReactNative",String.format("Image has been correctly resized: (%d,%d) -> (%d,%d)", width, height, image.getWidth(), image.getHeight()));
            // contents = image.writeToArray(VipsImageFormat.JPG, false);

            // Release object reference and resources
            // image.release();

        // } catch (IOException |  VipsException e) {
            // e.printStackTrace();
            // promise.resolve("Promise A "+e.toString());
        }catch (Exception e2){
            promise.resolve("promise B"+e2.toString());
            e2.printStackTrace();
        }

        promise.resolve("pppp C");
    }

    public static byte[] getByteArray(File file) throws IOException{

        // Creating an object of FileInputStream to
        // read from a file
        FileInputStream fl = new FileInputStream(file);

        // Now creating byte array of same length as file
        byte[] arr = new byte[(int)file.length()];

        // Reading file content to byte array
        // using standard read() method
        fl.read(arr);

        // lastly closing an instance of file input stream
        // to avoid memory leakage
        fl.close();

        // Returning above byte array
        return arr;
    }
    @ReactMethod
    public void prepare(String stickerPackParam, Promise promise) {
        List<StickerPack> stickerPacks = new ArrayList<StickerPack>();
        List<Sticker> mStickers = new ArrayList<>();
        List<String> mEmojis = new ArrayList<>();
        mEmojis.add("😀");
        mEmojis.add("😁");

        try {
            JSONObject stickerPack = new JSONObject(stickerPackParam);
            stickerPacks.addAll(Hawk.get("sticker_pack", new ArrayList<StickerPack>()));
            stickerPacks.add(new StickerPack(stickerPack.getString("identifier"), stickerPack.getString("name"),
                    stickerPack.getString("publisher"),
                    getLastBitFromUrl(stickerPack.getString("tray_image_file")).replace(" ", "_").replace(".png",
                            ".webp"),
                    stickerPack.getString("publisher_email"), stickerPack.getString("publisher_website"),
                    stickerPack.getString("privacy_policy_website"), stickerPack.getString("license_agreement_website"),
                    stickerPack.getString("image_data_version"),stickerPack.getBoolean("avoid_cache"),
                    stickerPack.getBoolean("animated_sticker_pack"))); // #373
            
            JSONArray stickers = stickerPack.getJSONArray("stickers");

            if(stickerPack.getBoolean("animated_sticker_pack")){
              new DownloadImageAnimated().execute(
                stickerPack.getString("tray_image_file"), stickerPack.getString("identifier"),
                stickerPack.getString("name"));
              
              for (int j = 0; j < stickers.length(); j++) {
                  JSONObject jsonStickersChildNode = stickers.getJSONObject(j);
                  new DownloadImageAnimated().execute(jsonStickersChildNode.getString("image_file"), stickerPack.getString("identifier"),
                          stickerPack.getString("name"));
                  mStickers.add(new Sticker(
                          getLastBitFromUrl(jsonStickersChildNode.getString("image_file")).replace(".png", ".webp"), mEmojis));
              }
            }else{
              new DownloadImage().execute(
                stickerPack.getString("tray_image_file"), stickerPack.getString("identifier"),
                stickerPack.getString("name"));
              for (int j = 0; j < stickers.length(); j++) {
                  JSONObject jsonStickersChildNode = stickers.getJSONObject(j);
                  new DownloadImage().execute(jsonStickersChildNode.getString("image_file"), stickerPack.getString("identifier"),
                          stickerPack.getString("name"));
                  mStickers.add(new Sticker(
                          getLastBitFromUrl(jsonStickersChildNode.getString("image_file")).replace(".png", ".webp"), mEmojis));
              }
            }
            

            Hawk.put(stickerPack.getString("identifier"), mStickers);

            stickerPacks.get(stickerPacks.size() - 1).setAndroidPlayStoreLink("https://play.google.com/store/apps/details?id=com.dawwati");
            stickerPacks.get(stickerPacks.size() - 1).setStickers(Hawk.get(stickerPack.getString("identifier"), new ArrayList<Sticker>()));
            Hawk.put("sticker_packs", stickerPacks);
            Hawk.put("dawwati_ping", "PONG");
            promise.resolve(stickerPacks.size() + stickerPacks.get(0).getContent().toString());
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject(ERROR_ADDING_STICKER_PACK, e);
        }
    }

    public static String getContentProviderAuthority(Context context) {
        return context.getPackageName() + ".stickercontentprovider";
    }
    public static String getFileProviderAuthority(Context context) {
        return context.getPackageName() + ".fileprovider";
    }


    @ReactMethod
    public void isAdded(String identifier, Promise promise){
        boolean f = WhitelistCheck.isWhitelisted(reactContext,identifier);
        promise.resolve(f);
    }

    @ReactMethod
    public void send(String identifier, String stickerPackName, Promise promise) {
        Intent intent = new Intent();
        intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
        intent.putExtra(EXTRA_STICKER_PACK_ID, identifier);
        intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, getContentProviderAuthority(reactContext));
        intent.putExtra(EXTRA_STICKER_PACK_NAME, stickerPackName);

        try {
            Activity activity = getCurrentActivity();
            ResolveInfo should = activity.getPackageManager().resolveActivity(intent, 0);
            if (should != null) {
                activity.startActivityForResult(intent, ADD_PACK);
                promise.resolve("OK");
            } else {
                promise.resolve("OK, but not opened");
            }
        } catch (ActivityNotFoundException e) {
            promise.reject(ERROR_ADDING_STICKER_PACK, e);
        } catch (Exception e) {
            promise.reject(ERROR_ADDING_STICKER_PACK, e);
        }
    }

    @ReactMethod
    public void getDownloadedStickers(Promise promise) {
        try {
            JSONArray identifiers = new JSONArray();
            File[] files = new File(path).listFiles();

            for (File aFile : files) {
                if (aFile.isDirectory()) {
                    identifiers.put(aFile.getName());
                }
            }
            promise.resolve(identifiers.toString());
        } catch (Exception e) {
            promise.reject(ERROR_ADDING_STICKER_PACK, e);
        }
    }

    private static String getLastBitFromUrl(final String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }

    public static void SaveTryImage(Bitmap finalBitmap, String name, String identifier) {

        String root = path + "/" + identifier;
        File myDir = new File(root + "/" + "try");
        myDir.mkdirs();
        String fname = name.replace(".png", "").replace(" ", "_") + ".png";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 40, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SaveImage(Bitmap finalBitmap, String name, String identifier) {

        String root = path + "/" + identifier;
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = name;
        File file = new File(myDir, fname);
        
        if (file.exists()){
            // Log.d("ReactNative","root "+root);
            file.delete();
        }

        
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.WEBP, 90, out);
            out.flush();
            out.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String TAG = "DownloadImage";
        public String imageFileName;
        public String identifier;
        public String name;

        private Bitmap downloadImageBitmap(String sUrl, String sIdentifier, String sName) {
            imageFileName = getLastBitFromUrl(sUrl).replace(".png", ".webp");
            identifier = sIdentifier;
            name = sName;
            Bitmap bitmap = null;
            
            try {
                InputStream inputStream = new URL(sUrl).openStream(); // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream); // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0], params[1], params[2]);
        }

        protected void onPostExecute(Bitmap result) {
            SaveImage(result, imageFileName, identifier);
        }
    }
    // ANIMATED 
    private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {

      // append = false
      try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
          int read;
          byte[] bytes = new byte[8192];
          while ((read = inputStream.read(bytes)) != -1) {
              outputStream.write(bytes, 0, read);
          }
      }

    }

    public static void SaveImageAnimated(String sUrl, String name, String identifier) {

      String root = path + "/" + identifier;
      File myDir = new File(root);
      myDir.mkdirs();
      String fname = name;
      File file = new File(myDir, fname);

      if (file.exists())
          file.delete();
      
      
      Log.d("ReactNative",sUrl);
      try{
        InputStream inputStream = new URL(sUrl).openStream();
        copyInputStreamToFile(inputStream,file);
      }catch(Exception e){
        Log.d("ReactNative",e.toString());
      }

     
    }

    private class DownloadImageAnimated extends AsyncTask<String, Void, String>{
      private String TAG = "DownloadImage";
      public String imageFileName;
      public String identifier;
      public String name;
      public boolean animated;

      private String downloadImageBitmap(String sUrl, String sIdentifier, String sName) {
          imageFileName = getLastBitFromUrl(sUrl).replace(".png", ".webp");
          identifier = sIdentifier;
          name = sName;
          // Bitmap bitmap = null;
          // try {
              // InputStream inputStream = new URL(sUrl).openStream(); // Download Image from URL
              // bitmap = BitmapFactory.decodeStream(inputStream); // Decode Bitmap
              // inputStream.close();
          // } catch (Exception e) {
          //     Log.d(TAG, "Exception 1, Something went wrong!");
          //     e.printStackTrace();
          // }
          return sUrl;
      }

      @Override
      protected String doInBackground(String... params) {
          return downloadImageBitmap(params[0], params[1], params[2]);
      }

      protected void onPostExecute(String result) {
          SaveImageAnimated(result, imageFileName, identifier);
      }
    }
    //! TESTING... 

    @ReactMethod
    public void addSticker(String url, String identifier, Promise promise){
        String tg = "ReactNative";
        String name = identifier;
        // try{
        //     Log.d("ReactNative", "cntx dir "+reactContext.getFilesDir().toString());
        // }catch(Exception e){
        //     Log.d("ReactNative", "exception: cntx dir:  "+e.toString());
        // }

        // File imagePath;
        // File[] files;
        // try{
        //     imagePath = new File(reactContext.getFilesDir(), "sticker_asset");
            
        //     files = imagePath.listFiles();

        //     for(int i=0;i<files.length;i++){
        //         if (files[i].isFile()) {
        //             Log.d("ReactNative", "File " + files[i].getName());
        //           } else if (files[i].isDirectory()) {
        //             Log.d("ReactNative", "Directory " + files[i].getName());
        //           }
        //     }
            // Log.d("ReactNative", "imagepath getParent : "+imagePath.getParent());
            // Log.d("ReactNative", "imagepath getAbsolutePath : "+imagePath.getAbsolutePath());
            // Log.d("ReactNative", "imagepath getName : "+imagePath.getName());
        // }catch(Exception e){
        //     Log.d("ReactNative", "Exception @main "+e.toString());
        // }
        // File newFile = new File(imagePath, "default_image.jpg");
        // Uri contentUri = FileProvider.getUriForFile(getContext(),getContentProviderAuthority(reactContext) , newFile);
        
        new DownloadImageA().execute(url,identifier,name);
        Log.d(tg,"Called the addSticker "+name+" "+identifier+" "+url);

        String root = path + "/" + identifier;
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = name;
        File file = new File(myDir, url);

        try{
            File newFile = file;
            Uri contentUri = FileProvider.getUriForFile(reactContext2,
                getFileProviderAuthority(reactContext2), newFile);
            
            // reactContext2.getPackageName()
             
            reactContext2.grantUriPermission("com.whatsapp", contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            
            Intent intent = new Intent(Intent.ACTION_SEND);
            // intent.setType("image/webp");
            intent.setData(contentUri);
            // Intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Activity activity = getCurrentActivity();
            activity.setResult(Activity.RESULT_OK, intent);
            Log.d("ReactNative","-------====================-----");
            Log.d("ReactNative","getEncodedPath() " +contentUri.getEncodedPath());
            Log.d("ReactNative","toString() " +contentUri.toString());
            Log.d("ReactNative","isAbsolute() " +contentUri.isAbsolute());
            Log.d("ReactNative","isRelative() " +contentUri.isRelative());
            Log.d("ReactNative","-------==========X==========-----");
        }catch(Exception e){
            Log.d("ReactNative","Exception in FP "+e.getClass());
            Log.d("ReactNative","Exception in FP "+e.getMessage());
            e.printStackTrace();
        }

        // Intent intent = new Intent();
        // intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
        // intent.putExtra(EXTRA_STICKER_PACK_ID, identifier);
        // intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, getContentProviderAuthority(reactContext2));
        // intent.putExtra(EXTRA_STICKER_PACK_NAME, identifier);

        // try {
        //     Activity activity = getCurrentActivity();
        //     ResolveInfo should = activity.getPackageManager().resolveActivity(intent, 0);
        //     if (should != null) {
        //         activity.startActivityForResult(intent, ADD_PACK);
        //         Log.d("ReactNative","OK");
        //     } else {
        //         Log.d("ReactNative","OK, but not opened");
        //     }
        // } catch (ActivityNotFoundException e) {
        //     Log.d("ReactNative","ERROR_ADDING_STICKER_PACK");
        // } catch (Exception e) {
        //     Log.d("ReactNative","ERROR_ADDING_STICKER_PACK 2 ");
        // }
    }

    private class DownloadImageA extends AsyncTask<String, Void, Bitmap> {
        private String TAG = "DownloadImage";
        public String imageFileName;
        public String identifier;
        public String name;

        private Bitmap downloadImageBitmap(String sUrl, String sIdentifier, String sName) {
            imageFileName = getLastBitFromUrl(sUrl).replace(".png", ".webp");
            identifier = sIdentifier;
            name = sName;
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream(); // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream); // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0], params[1], params[2]);
        }

        protected void onPostExecute(Bitmap result) {
            SaveImage(result, imageFileName, identifier);
        }
    }
}