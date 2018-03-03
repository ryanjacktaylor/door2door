package com.duckmethod.door2door.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;

/**
 * Created by Ryan on 10/28/2017.
 */

public class Utilities {

    private final static String TAG = "Utilities";

    //PARSING UTILITIES
    public static int parseInt(String stringValue){
        try{
            int intValue = Integer.parseInt(stringValue);
            return intValue;
        } catch (Exception e){
            Log.w(TAG, "parseInt: Could not parse " + stringValue + "into an integer");
        }
        return 0;
    }

    public static int parseInt(TextView stringValue){
        return parseInt(stringValue.getText().toString());
    }

    public static double parseDouble(String string){
        try{
            double value = Double.parseDouble(string);
            return value;
        } catch (Exception e){
            Log.w(TAG, "parseDouble: Could not parse " + string + "into an double");
        }
        return 0;
    }

    //Formatting
    public static String formatCurrency(String input){
        return formatCurrency(parseDouble(input));
    }

    public static String formatCurrency(double input){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(2);
        return format.format(input);  //NOTE:  This adds a $ sign
    }

    public static String formatDecimal(String input, int decimalPlaces){
        NumberFormat format = NumberFormat.getInstance();
        double value = parseDouble(input);
        format.setMinimumFractionDigits(decimalPlaces);
        return format.format(value);
    }


    //Convert ImageView to Blob
    public static byte[] imageViewToByteArray(ImageView iv){
        Drawable d = iv.getBackground();
        BitmapDrawable bitDw = ((BitmapDrawable) d);
        Bitmap bitmap = bitDw.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        return imageInByte;
    }

    //Convert Byte array to bitmap
    public static Bitmap convertByteArrayToBitmap(byte[] byteArrayToBeCOnvertedIntoBitMap) {
        Bitmap bitMapImage = BitmapFactory.decodeByteArray(
                byteArrayToBeCOnvertedIntoBitMap, 0,
                byteArrayToBeCOnvertedIntoBitMap.length);
        return bitMapImage;
    }
}
