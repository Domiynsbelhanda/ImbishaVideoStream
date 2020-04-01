package cd.belhanda.imbishavideostream.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cd.belhanda.imbishavideostream.Modele.Chanson;
import cd.belhanda.imbishavideostream.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class modele_adapter extends RecyclerView.Adapter<modele_adapter.RecyclerVH> {

    Context c;
    ArrayList<Chanson> mList;

    itemClick onitemclick;
    DownloadClick downloadClick;

    public void setOnitemclick(itemClick onitemclick) {
        this.onitemclick = onitemclick;
    }

    public void setDownloadClick(DownloadClick downloadClick) {
        this.downloadClick = downloadClick;
    }

    public modele_adapter(Context c, ArrayList<Chanson> mList) {
        this.c = c;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.modele_file, parent, false);
        return new RecyclerVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerVH holder, int position) {
        final Chanson chanson = mList.get(position);
        Picasso.get().load(mList.get(position).getImg_url()).into(holder.img_art);
        holder.artiste.setText(mList.get(position).getNom_art());
        holder.titre.setText(mList.get(position).getTitre());
        holder.play_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onitemclick !=null ){
                    onitemclick.onItemClick(chanson);
                }
            }
        });

        holder.down_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(downloadClick != null){
                    downloadClick.String_url(chanson);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class RecyclerVH extends RecyclerView.ViewHolder{

        CircleImageView img_art;
        TextView titre, artiste;
        ImageView play_img, down_img;

        public RecyclerVH(@NonNull View itemView) {
            super(itemView);
            img_art = itemView.findViewById(R.id.img_art);
            titre = itemView.findViewById(R.id.titre_chanson);
            artiste = itemView.findViewById(R.id.nom_art);
            play_img = itemView.findViewById(R.id.play_img);
            down_img = itemView.findViewById(R.id.down_img);
        }
    }
}
