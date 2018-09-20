package com.example.dell.appcuxa.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class AppUtils {
    public static final int REQUEST_CAMERA = 11;
    public static final int ERROR_DIALOG_REQUEST = 9001;
    public static final String TAG = "CUXA";
    public static final int PICK_IMAGE_1 = 1;
    public static final int PICK_IMAGE_2 = 2;
    public static final int PICK_IMAGE_3 = 3;
    public static final int PICK_IMAGE_4 = 4;
    public static final int PICK_IMAGE_5 = 5;
    public static final int PICK_IMAGE_6 = 6;

    public static final int PICK_IMAGE_7 = 7;
    public static final int PICK_IMAGE_8 = 8;
    public static final int PICK_IMAGE_9 = 9;
    public static final int PICK_IMAGE_10 = 10;

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * Hàm có yêu cầu quyền
     *
     * @param context
     * @return
     */
    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    /**
     * Kiểm tra dịch vụ google có trong trạng thái thỏa mãn không?
     * @param context
     * @return
     */
    public static boolean isServiceOk(Activity context){
        Log.d(TAG,"isServiceOk: checking google service version");
        int variable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        //everything is find and the user can make map requests
        if(variable == ConnectionResult.SUCCESS){
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(variable)){
            //an error occured but we can resolve it
            Log.d(TAG,"an error occured but we can resolve it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(context,variable,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(context, "We cannot make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
