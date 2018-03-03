package com.duckmethod.door2door.Sell;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Models.Product;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.R;

import java.util.List;

/**
 * Created by Ryan on 10/22/2017.
 */

public class ItemPickerAdapter extends RecyclerView.Adapter<ItemPickerAdapter.ThisViewHolder>{

    private List<Product> mItemList;

    //ItemSelectedListener
    private ItemSelectedListener mItemSelectedListener;


    public class ThisViewHolder extends RecyclerView.ViewHolder {
        public TextView mName, mCount;
        public RelativeLayout mCard;


        public ThisViewHolder(View v) {
            super(v);
            mName = v.findViewById(R.id.item_picker_name);
            mCount = v.findViewById(R.id.item_picker_count);
            mCard = v.findViewById(R.id.item_picker_card);
        }
    }

    public ItemPickerAdapter(List<Product> inventoryList) {
        this.mItemList = inventoryList;
    }

    @Override
    public ThisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_picker, parent, false);

        return new ThisViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ThisViewHolder holder, int position) {
        final Product item = mItemList.get(position);

        //Get the name.  If it's over 14 characters, wrap the line
        String name = item.getName();
        if (name.length()>20){
            //Find the last " " and replace with /n
            int lastSpaceIndex = name.indexOf(' ', 11);
            if (lastSpaceIndex >0) {
                name = name.substring(0, lastSpaceIndex) + "\n" + name.substring(lastSpaceIndex + 1);
            }
        }
        holder.mName.setText(name);
        holder.mCount.setText(String.valueOf(item.getCount()));

        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemSelectedListener.ItemSelected(item);
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

    public void setOnItemSelected(ItemSelectedListener listener){
        mItemSelectedListener = listener;
    }

}
