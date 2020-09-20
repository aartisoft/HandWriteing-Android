package com.copypasteit.handwriting.competition.HomeActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.copypasteit.handwriting.competition.R;


public class ViewHolder extends RecyclerView.ViewHolder {
    TextView dress_name, dress_model_no, date, prize;
    ImageView dress_image;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        dress_name = itemView.findViewById(R.id.id_derss_name);
        dress_model_no = itemView.findViewById(R.id.id_derss_model);
        date = itemView.findViewById(R.id.textView_date);
        prize = itemView.findViewById(R.id.textView_prize);
        dress_image = itemView.findViewById(R.id.dress_image_id);

       itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mClickListener.onItemClick(v, getAdapterPosition());
           }
       });
    }
    private ViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void  onItemClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }


}
