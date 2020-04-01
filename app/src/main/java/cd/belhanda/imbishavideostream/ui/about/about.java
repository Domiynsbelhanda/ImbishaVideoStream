package cd.belhanda.imbishavideostream.ui.about;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;
import cd.belhanda.imbishavideostream.Adapters.DownloadClick;
import cd.belhanda.imbishavideostream.Adapters.itemClick;
import cd.belhanda.imbishavideostream.Adapters.modele_adapter;
import cd.belhanda.imbishavideostream.Modele.Chanson;
import cd.belhanda.imbishavideostream.R;

import static android.content.Context.DOWNLOAD_SERVICE;

public class about extends Fragment {

    private static final int PERMISSION_STORAGE_CODE = 1000 ;
    private about_view_model urbaineviewmodel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        urbaineviewmodel =
                ViewModelProviders.of(this).get(about_view_model.class);
        View root = inflater.inflate(R.layout.fragment_layout, container, false);


        return root;
    }

    private void startDownload(String url) {
        DownloadManager.Request request = new DownloadManager.Request((Uri.parse(url)));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Update Imbisha App");
        request.setDescription("Description");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Imbisha Video Stream");

        DownloadManager manager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);

        Toast.makeText(getActivity(), "APK EN COURS DE TELECHARGEMENT ...", Toast.LENGTH_LONG).show();
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
}
