package cd.belhanda.imbishavideostream.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import cd.belhanda.imbishavideostream.Modele.Chanson;
import cd.belhanda.imbishavideostream.R;

public class SliderPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<Chanson> mList;

    public SliderPageAdapter(Context mContext, List<Chanson> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slider_item, null);

        ImageView slideImg = slideLayout.findViewById(R.id.slider_img);
        TextView slideText = slideLayout.findViewById(R.id.slider_titre);

        Picasso.get().load(mList.get(position).getImg_url()).into(slideImg);
        slideText.setText(mList.get(position).getTitre());

        container.addView(slideLayout);
        return slideLayout;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
