package com.android.customrecyclerview.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.customrecyclerview.R;
import com.android.customrecyclerview.model.Student;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Object> studentArrayList;

    private final int USER = 0, IMAGE = 1 , ADS =2;

    public ListAdapter(ArrayList<Object> studentArrayList) {
        this.studentArrayList = studentArrayList;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        switch (i) {
            case USER:
                View view1 = layoutInflater.inflate(R.layout.item_student, viewGroup, false);
                Log.d("test", "onCreateViewHolder: "+ view1.toString());
                return  new StudentViewHolder(view1);

            case IMAGE:
                View view2 = layoutInflater.inflate(R.layout.item_image, viewGroup, false);
                Log.d("test", "onCreateViewHolder: "+ view2.toString());
                return  new ImageViewHolder(view2);

            case ADS:
                View view3 = layoutInflater.inflate(R.layout.item_add, viewGroup, false);
                Log.d("test", "onCreateViewHolder: "+ view3.toString());
                return  new NativeExpressAdViewHolder(view3);

            default:
                View v = layoutInflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                return new RecyclerViewSimpleTextViewHolder(v);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        switch (viewHolder.getItemViewType()) {
            case USER:
                Log.d("test", "onBindViewHolder: "+ viewHolder.toString());
                StudentViewHolder studentViewHolder = (StudentViewHolder) viewHolder;
                Student student = (Student) studentArrayList.get(i);
                studentViewHolder.tvName.setText(student.getName());
                studentViewHolder.tvId.setText(student.getId());
                break;
            case IMAGE:
                Log.d("test", "onBindViewHolder: "+ viewHolder.toString());
                ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
                Drawable ivImage = (Drawable) studentArrayList.get(i);
                imageViewHolder.ivImage.setImageDrawable(ivImage);
                break;
            case ADS:
                Log.d("test", "onBindViewHolder: "+ viewHolder.toString());
               NativeExpressAdViewHolder nativeExpressAdViewHolder = (NativeExpressAdViewHolder) viewHolder;
               ArrayList<View> adView = new ArrayList<>();
               for(int position =i,j=0;position<=studentArrayList.size();position+=i,j++) {
                   View view = (View) studentArrayList.get(i);
                   adView.add(view);
               }
               nativeExpressAdViewHolder.cardView.addTouchables(adView);

               break;
            default:
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (studentArrayList.get(position) instanceof Student) {
            Log.d("test", "getItemViewType: "+USER);
            return USER;
        } else if (studentArrayList.get(position) instanceof Drawable) {
            Log.d("test", "getItemViewType: "+IMAGE);
            return IMAGE;
        }
        else if(studentArrayList.get(position) instanceof NativeExpressAdView){
            Log.d("test", "getItemViewType: "+ADS);
            return ADS;
        }

        return -1;

    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvId;

        private StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvId = itemView.findViewById(R.id.tvId);
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;

        private ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.iv_image);
        }


    }
    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        NativeExpressAdViewHolder(View view) {
            super(view);
            cardView = itemView.findViewById(R.id.add_view);
        }
    }


    private class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {
        private RecyclerViewSimpleTextViewHolder(View v) {
            super(v);
        }
    }
}
