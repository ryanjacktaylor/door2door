package com.duckmethod.door2door.Inventory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duckmethod.door2door.Models.Product;
import com.duckmethod.door2door.R;
import com.duckmethod.door2door.Utilities.Utilities;

import java.util.List;

/**
 * Created by Ryan on 10/22/2017.
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ThisViewHolder>{

    private List<Product> mItemList;

    //Callbacks
    private OnCallbackListener mOnCallbackListener;
    public interface OnCallbackListener {
        void onEditClick(Product item);
        void onDeleteClick(Product item);
    }
    public void setOnCallbackListener(OnCallbackListener l) {
        mOnCallbackListener = l;
    }


    public class ThisViewHolder extends RecyclerView.ViewHolder {
        public TextView mName, mCount, mPrice, mDelete, mEdit;
        public LinearLayout mDetailsView;
        public RelativeLayout mLine;

        public ThisViewHolder(View v) {
            super(v);
            mName = v.findViewById(R.id.recycler_view_inventory_description_textview);
            mCount = v.findViewById(R.id.recycler_view_inventory_amount_textview);
            mPrice = v.findViewById(R.id.recycler_view_inventory_price_textview);
            mDetailsView = v.findViewById(R.id.recycler_view_inventory_edit_bar);
            mLine = v.findViewById(R.id.recycler_view_inventory_line);
            mDelete = v.findViewById(R.id.recycler_view_inventory_delete_textview);
            mEdit = v.findViewById(R.id.recycler_view_inventory_edit_textview);
        }
    }

    public InventoryAdapter(List<Product> inventoryList) {
        this.mItemList = inventoryList;
    }

    @Override
    public ThisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_inventory_item, parent, false);

        return new ThisViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ThisViewHolder holder, int position) {
        final Product item = mItemList.get(position);

        //Get the name.  If it's over 14 characters, wrap the line
        String name = item.getName();
        if (name.length()>14){
            //Find the last " " and replace with /n
            int lastSpaceIndex = name.indexOf(' ', 11);
            if (lastSpaceIndex >0) {
                name = name.substring(0, lastSpaceIndex) + "\n" + name.substring(lastSpaceIndex + 1);
            }
        }
        holder.mName.setText(name);
        holder.mCount.setText(String.valueOf(item.getCount()));
        holder.mPrice.setText(Utilities.formatCurrency(item.getPrice()));

        //Set the selection listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mDetailsView.getVisibility() == View.GONE){
                    holder.mDetailsView.setVisibility(View.VISIBLE);
                    holder.mLine.setVisibility(View.VISIBLE);
                } else {
                    holder.mDetailsView.setVisibility(View.GONE);
                    holder.mLine.setVisibility(View.GONE);
                }
            }
        });

        //Set the onClick listener for edit
        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCallbackListener.onEditClick(item);
            }
        });

        //Set the onClick listener for edit
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCallbackListener.onDeleteClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setItemList(List<Product> items){
        mItemList = items;
        notifyDataSetChanged();
    }

}
