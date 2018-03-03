package com.duckmethod.door2door.Sell;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Models.Product;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.R;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Utilities.Utilities;

;

/**
 * Created by Ryan on 11/25/2017.
 */

public class SaleItemView extends CardView {

    private final static String TAG = "SaleItemView";

    //UI elements
    private TextView mName;
    private TextView mQty;
    private TextView mPrice;
    private ImageView mImageView;


    public SaleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SaleItemView(Context context) {
        super(context);
        initView();
    }

    private void initView(){
        View v = inflate(getContext(), R.layout.sale_item, null);
        mName = v.findViewById(R.id.sale_item_name_textview);
        mQty = v.findViewById(R.id.sale_item_qty_textview);
        mPrice = v.findViewById(R.id.sale_item_price_textview);
        mImageView = v.findViewById(R.id.sale_item_imageview);
        addView(v);
    }

    public void setItem(Product item, int qty){
        mName.setText(item.getName());
        mQty.setText(String.valueOf(qty));
        mPrice.setText(Utilities.formatCurrency(item.getPrice()));
        if (item.getImage() != null){
            try {
                Bitmap bmp = Utilities.convertByteArrayToBitmap(item.getImage().getBlob());
                mImageView.setImageBitmap(bmp);
            } catch (Exception e){
                Log.w(TAG, "setItem: Could not create image from blob: " + e.toString());
            }
        }

    }
}
