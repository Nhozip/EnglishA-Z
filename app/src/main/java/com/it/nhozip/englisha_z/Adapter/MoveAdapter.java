package com.it.nhozip.englisha_z.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.NativeExpressAdView;
import com.it.nhozip.englisha_z.MainActivity;
import com.it.nhozip.englisha_z.Model.Move;
import com.it.nhozip.englisha_z.R;
import com.it.nhozip.englisha_z.View.ActivityDetailMove;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by huyen on 12/23/2016.
 */


public class MoveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> arrMove;
    private Context _context;
    private final int MOVE = 1;
    private final int ADV = 0;
    public static final String MOVE_OBJ = "move_obj";
    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    // The Native Express ad view type.
    private static final int NATIVE_EXPRESS_AD_VIEW_TYPE = 1;


    public MoveAdapter(ArrayList<Object> arrMove, Context _context) {
        this.arrMove = arrMove;
        this._context = _context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.item_move, viewGroup, false);
                return new RecyclerViewHolder(menuItemLayoutView);
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                // fall through
            default:
                View nativeExpressLayoutView = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.native_express_ad_container,
                        viewGroup, false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
                RecyclerViewHolder holder= (RecyclerViewHolder) viewHolder;
                final Move menuItem = (Move) arrMove.get(i);
                Glide.with(_context).load("http://img.youtube.com/vi/"+menuItem.getThumbi_move().toString()+"/mqdefault.jpg")
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imgHinh);
                holder.txtActor.setText(menuItem.getActor());

                holder.txtName.setText(menuItem.getName());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity.dem=0;
                        Intent intent=new Intent(_context, ActivityDetailMove.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(MOVE_OBJ, menuItem);
                        _context.startActivity(intent);
                    }
                });
                break;
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                // fall through
            default:
                try{
                    NativeExpressAdViewHolder nativeExpressHolder =
                            (NativeExpressAdViewHolder) viewHolder;
                    NativeExpressAdView adView = (NativeExpressAdView) arrMove.get(i);
                    ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;
                    if (adCardView.getChildCount() > 0) {
                        adCardView.removeAllViews();
                    }
                    if (adView.getParent() != null) {
                        ((ViewGroup) adView.getParent()).removeView(adView);
                    }

                    adCardView.addView(adView);
                }
               catch (Exception e){

               }
        }

    }

    @Override
    public int getItemViewType(int position) {
        return (position % MainActivity.ITEMS_PER_AD == 0) ? NATIVE_EXPRESS_AD_VIEW_TYPE
                : MENU_ITEM_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return arrMove.size();
    }

    @Override
    public String toString() {
        return "FoodAdapter{" +
                "arrFoods=" + arrMove +
                ", _context=" + _context +
                '}';
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName,  txtActor;
        private ImageView imgHinh;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            imgHinh = (ImageView) itemView.findViewById(R.id.img_hinh);
            txtActor = (TextView) itemView.findViewById(R.id.txtActor);
        }
    }

    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {

        NativeExpressAdViewHolder(View view) {
            super(view);
        }
    }
}

