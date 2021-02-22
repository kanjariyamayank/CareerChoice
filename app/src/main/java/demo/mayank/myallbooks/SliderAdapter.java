package demo.mayank.myallbooks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewholder> {

    private List<Slideritem> slideritems;
    private ViewPager2 viewPager2;

     SliderAdapter(List<Slideritem> slideritems, ViewPager2 viewPager2) {
        this.slideritems = slideritems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_contener
                , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewholder holder, int position) {
        holder.setImage(slideritems.get(position));
        if (position == slideritems.size() - 2){
            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return slideritems.size();
    }

    class SliderViewholder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;

        SliderViewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(Slideritem slideritem) {
            imageView.setImageResource(slideritem.getImage());

        }
    }
    private  Runnable runnable = new Runnable() {
        @Override
        public void run() {
        slideritems.addAll(slideritems);
        notifyDataSetChanged();
        }
    };
}
