package io.github.artenes.speedbro.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.github.artenes.speedbro.models.RunsListWithMapViewModel;
import io.github.artenes.speedbro.speedrun.com.models.MapCluster;

/**
 * Map that displays markers with runs on them
 */
public class RunsMapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private List<MapCluster> mClusters;
    private RunsListWithMapViewModel mViewModel;

    public void setClusters(List<MapCluster> cluster) {
        mClusters = cluster;
        getMapAsync(this);
    }

    public void setViewModel(RunsListWithMapViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
        for (MapCluster cluster : mClusters) {
            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(cluster.getLatitude(), cluster.getLongitude()))
                    .title(cluster.getCountry());
            Marker marker = googleMap.addMarker(options);
            marker.setTag(cluster);
        }
        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        MapCluster cluster = (MapCluster) marker.getTag();
        openCluster(cluster);
        return true;
    }

    private void openCluster(MapCluster cluster) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogRunsFragment newFragment = new DialogRunsFragment();
        newFragment.setState(cluster.getRuns(), 0);
        newFragment.show(ft, "dialog");
        mViewModel.setVisibleCluster(mClusters.indexOf(cluster));
    }

}