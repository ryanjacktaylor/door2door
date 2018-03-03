package com.duckmethod.door2door.Inventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Models.Product;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.R;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Utilities.DecimalDigitsInputFilter;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Utilities.Utilities;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Ryan on 10/28/2017.
 */

public class InventoryEditFragment extends DialogFragment {

    private static final String TAG = "InventoryEditFragment";

    //BUNDLE KEY
    public static final String BUNDLE_ID = "id";

    //UI
    private EditText mNameEditText;
    private EditText mPriceEditText;
    private Button mChoseImageButton;
    private ImageView mImageView;
    private Button[] mCountButtons;
    private int[] mCountButtonIds = {R.id.inventory_edit_minus_10_button,
                                     R.id.inventory_edit_minus_1_button,
                                     R.id.inventory_edit_plus_1_button,
                                     R.id.inventory_edit_plus_10_button};
    private int[] mCountButtonValues = {-10,-1,1,10};
    private TextView mCountTextView;
    private TextView mCancel;
    private TextView mSave;

    //Data
    Inventory mInventory;
    private int mId;
    private boolean mEditMode;
    private Product mProduct;

    public static InventoryEditFragment newInstance(){
        InventoryEditFragment frag = new InventoryEditFragment();
        return frag;
    }

    public static InventoryEditFragment newInstance(int id){
        InventoryEditFragment frag = new InventoryEditFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_ID, id);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
        //Inflate the view
        View v = inflater.inflate(R.layout.fragment_inventory_item_edit, container,false);

        //Init data
        mInventory = Inventory.getInstance();

        //Find the UI elements
        mNameEditText = v.findViewById(R.id.inventory_edit_name_textview);
        mPriceEditText = v.findViewById(R.id.inventory_edit_price_textview);
        mPriceEditText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        mChoseImageButton = v.findViewById(R.id.inventory_edit_select_image_button);
        mImageView = v.findViewById(R.id.inventory_edit_immageview);
        mCountTextView = v.findViewById(R.id.inventory_edit_count_textview);
        mCountButtons = new Button[4];
        for (int i = 0; i<mCountButtons.length;i++){
            final int modifier = mCountButtonValues[i];
            mCountButtons[i] = v.findViewById(mCountButtonIds[i]);
            mCountButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int countValue = Utilities.parseInt(mCountTextView.getText().toString());
                    countValue= Math.max(countValue + modifier,0);
                    mCountTextView.setText(String.valueOf(countValue));
                }
            });
        }
        mCancel = v.findViewById(R.id.inventory_edit_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(RESULT_CANCELED);
            }
        });
        mSave = v.findViewById(R.id.inventory_edit_save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Make sure a name was entered
                if (mNameEditText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "You must enter a name", Toast.LENGTH_LONG).show();
                    return;
                }

                //Create a new Inventory Item
                if (!mEditMode) {
                    mProduct = new Product();
                }
                mProduct.setName(mNameEditText.getText().toString());
                mProduct.setPrice(Utilities.formatDecimal(mPriceEditText.getText().toString().isEmpty() ? "0" : mPriceEditText.getText().toString(), 2));
                //mProduct.setImage(new Blob(Utilities.imageViewToByteArray(mImageView)));
                mProduct.setCount(Utilities.parseInt(mCountTextView));
                if (mEditMode){
                    mInventory.updateItem(mProduct);
                } else {
                    mId = mInventory.addItem(mProduct);
                }
                sendResult(RESULT_OK);
            }
        });

        //Get the product ID if sent
        if (getArguments()!=null) {
            mId = getArguments().getInt(BUNDLE_ID);
            mProduct = mInventory.getItembyId(mId);
            if (mProduct !=null){
                mEditMode = true;
                mNameEditText.setText(mProduct.getName());
                mPriceEditText.setText(mProduct.getPrice());
                mCountTextView.setText(String.valueOf(mProduct.getCount()));
            }
        }

        return v;

    }

    private void sendResult(int resultCode) {
        Intent intent = new Intent();
        intent.putExtra(BUNDLE_ID, mId);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(), resultCode, intent);
    }
}
