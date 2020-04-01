package cd.belhanda.imbishavideostream.ui.gospel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;
import cd.belhanda.imbishavideostream.Adapters.DownloadClick;
import cd.belhanda.imbishavideostream.Adapters.SliderPageAdapter;
import cd.belhanda.imbishavideostream.Adapters.itemClick;
import cd.belhanda.imbishavideostream.Adapters.modele_adapter;
import cd.belhanda.imbishavideostream.Main;
import cd.belhanda.imbishavideostream.Modele.Chanson;
import cd.belhanda.imbishavideostream.R;

import static android.content.Context.DOWNLOAD_SERVICE;

public class gospel_accueil extends Fragment implements itemClick, DownloadClick {

    private gospel_view_model gospelviewmodel;

    private static final int PERMISSION_STORAGE_CODE = 1000 ;
    ArrayList<Chanson> mList = new ArrayList<>();

    RecyclerView modele;

    Dialog dialog;
    View viewLancer;

    private modele_adapter modele_adapter_1;

    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;

    private ViewPager sliderpager;
    private TabLayout indicator;
    private List<Chanson> lstSlides;
    SliderPageAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gospelviewmodel =
                ViewModelProviders.of(this).get(gospel_view_model.class);
        View root = inflater.inflate(R.layout.fragment_gospel, container, false);


        modele  = root.findViewById(R.id.recycler_view_gospel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        modele.setLayoutManager(linearLayoutManager);


        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference("Musique");
        reference = reference.child("Gospel");
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mList.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Chanson chanson = dataSnapshot1.getValue(Chanson.class);
                    mList.add(chanson);
                }sliderpager = root.findViewById(R.id.slider_pager);
                indicator = root.findViewById(R.id.indicator);

                iniSlider();
                modele.setAdapter(modele_adapter_1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        modele_adapter_1 = new modele_adapter(getActivity(), mList);
        modele_adapter_1.setOnitemclick(this);
        modele_adapter_1.setDownloadClick(this);



        return root;
    }

    public void dialogconfig(String VIDEO_TEST_URL, Chanson chanson){
        dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        viewLancer = LayoutInflater.from(getActivity()).inflate(R.layout.player_dialog, null);

        TextView title_dialog = viewLancer.findViewById(R.id.title_dialog);
        TextView art_dialog = viewLancer.findViewById(R.id.artiste_dialog);
        ImageView download = viewLancer.findViewById(R.id.download_dialog);

        title_dialog.setText(chanson.getTitre() + " ----- ");
        art_dialog.setText(chanson.getNom_art());

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload(VIDEO_TEST_URL, chanson);
            }
        });

        dialog.setContentView(viewLancer);


        playerView = viewLancer.findViewById(R.id.movie_exo_player);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity());
        playerView.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "appname"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(VIDEO_TEST_URL));
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(false);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                simpleExoPlayer.release();
            }
        });
        dialog.show();

    }

    @Override
    public void onItemClick(Chanson chanson) {
        String youtubeLink = chanson.getUrl();

        @SuppressLint("StaticFieldLeak") YouTubeUriExtractor ytEx = new YouTubeUriExtractor(getActivity()) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                if (ytFiles != null) {
                    int itag = ytFiles.keyAt(0);;
                    // Here you can get download url
                    String downloadUrl = ytFiles.get(itag).getUrl();
                    dialogconfig(downloadUrl, chanson);

                }
            }
        };

        ytEx.execute(youtubeLink);
    }

    @Override
    public void String_url(Chanson chanson) {
        String youtubeLink = chanson.getUrl();

        @SuppressLint("StaticFieldLeak") YouTubeUriExtractor ytEx = new YouTubeUriExtractor(getActivity()) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                if (ytFiles != null) {
                    int itag = ytFiles.keyAt(0);
                    // Here you can get download url
                    final String downloadUrl = ytFiles.get(itag).getUrl();
                    download_oklm(downloadUrl, chanson);

                }
            }
        };

        ytEx.execute(youtubeLink);
    }

    public void download_oklm(String down_url, Chanson chanson){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                //Permission denied
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_STORAGE_CODE);
            } else{
                startDownload(down_url, chanson);
            }
        } else{
            startDownload(down_url, chanson);
        }
    }

    private void startDownload(String url, Chanson chanson) {
        DownloadManager.Request request = new DownloadManager.Request((Uri.parse(url)));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Imbisha Video Stream Download");
        request.setDescription("Description");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                chanson.getTitre() + " __" + chanson.getNom_art() + "___" + " Imbisha Video Stream");

        DownloadManager manager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);

        Toast.makeText(getActivity(), "TELECHARGEMENT EN COURS ...", Toast.LENGTH_LONG).show();
    }

    //Permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                } else{

                }
            }
        }
    }

    private void iniSlider() {

        lstSlides = new ArrayList();

        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference("Slider1");
        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstSlides.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Chanson chanson = dataSnapshot1.getValue(Chanson.class);
                    lstSlides.add(chanson);
                }
                sliderpager.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new SliderPageAdapter(getActivity(), lstSlides);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

        indicator.setupWithViewPager(sliderpager, true);
    }

    class SliderTimer extends TimerTask {
        @Override
        public void run() {
            try{
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(sliderpager.getCurrentItem()<lstSlides.size()-1){
                            sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);
                        } else{
                            sliderpager.setCurrentItem(0);
                        }
                    }
                });
            }
            catch (NullPointerException e){

            }
        }
    }
}
