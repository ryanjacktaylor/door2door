package com.duckmethod.door2door.Sell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.CustomControls.SegmentedControl;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Inventory.Inventory;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Models.Product;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.R;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Utilities.DrawUtility;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ryan on 11/25/2017.
 */

public class SaleFragment extends Fragment {

    private final static String TAG = "SaleFragment";

    private final static int ADD_ITEM_REQUEST = 1;

    //UI Elements
    private LinearLayout mSaleItemsLayout;
    private CardView mAddItemCard;
    private TextView mSaleTotalTextView;
    private SegmentedControl mPaymentSegmentedControl;
    private Button mProcessButton;
    private List<SaleItemView> mSaleItemViews = new ArrayList<>();

    //Dialogs
    private DialogFragment mItemPickerDialog;

    //Cart List
    private List<Product> mCart = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sale, container, false);

        //Find UI Elements
        mSaleItemsLayout = v.findViewById(R.id.sale_item_linearlayout);
        mAddItemCard = v.findViewById(R.id.sale_add_item_cardview);
        mSaleTotalTextView = v.findViewById(R.id.sale_total_textview);
        mPaymentSegmentedControl = v.findViewById(R.id.sale_cash_check_charge_segmentedcontrol);
        mProcessButton = v.findViewById(R.id.sale_process_button);

        //Item Picker Dialog
        mItemPickerDialog = ItemPickerFragment.newInstance();
        mItemPickerDialog.setTargetFragment(this, ADD_ITEM_REQUEST);

        //Add Item card
        mAddItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog();
            }
        });

        return v;
    }

    public void showAddItemDialog(){

        // Show the dialog.
        mItemPickerDialog.show(getFragmentManager(), "");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bundle extras = data.getExtras();
        if (requestCode == ADD_ITEM_REQUEST) {
            if (resultCode==RESULT_OK) {

                //Get the item id
                if (extras != null){
                    int id = extras.getInt(ItemPickerFragment.BUNDLE_ID);
                    Product item = Inventory.getInstance().getItembyId(id);

                    //Add the product to the cart
                    mCart.add(item);

                    //Update the list
                    rebuildCart();


                }

            }
            mItemPickerDialog.dismiss();
        }
    }

    private void rebuildCart(){

        double total = 0;

        //Clear the layout
        mSaleItemsLayout.removeAllViews();

        //Add all views to the cart
        for (Product product:mCart){

            //Create the Sale Item View, add a margin, and add it to the list
            SaleItemView saleItemView = new SaleItemView(getContext());
            saleItemView.setItem(product, 1);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,DrawUtility.dpToPx(1),0, 0);
            saleItemView.setLayoutParams(params);
            mSaleItemsLayout.addView(saleItemView);

            //Add the price to the total
            total+= Utilities.parseDouble(product.getPrice());

        }

        //Update the total
        mSaleTotalTextView.setText(Utilities.formatCurrency(total));

    }
}
