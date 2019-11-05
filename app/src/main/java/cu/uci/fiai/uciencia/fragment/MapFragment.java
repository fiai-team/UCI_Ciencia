package cu.uci.fiai.uciencia.fragment;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.mapsforge.core.graphics.Canvas;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.BoundingBox;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.core.util.MercatorProjection;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapDataStore;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import cu.uci.fiai.uciencia.R;
import cu.uci.fiai.uciencia.pojo.POIMap;
import cu.uci.fiai.uciencia.util.AppUCI;
import cu.uci.fiai.uciencia.util.CopyMapTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private static final int FLIP_DURATION = 3000;

    private ArrayList<POIMap> poisMap;

    private FrameLayout fl;
    private MapView mapView;
    private TileCache tileCache;
    private TileRendererLayer tileRendererLayer;
    private BottomSheetDialog bottomSheetDialog;
    private ViewFlipper viewFlipper;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidGraphicFactory.createInstance(getActivity().getApplication());
        getActivity().setTitle(R.string.nav_drawer_5);

        poisMap = ((AppUCI) getActivity().getApplication()).getPOIsMap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        setupView(view);

        return view;
    }

    private void setupView(View view) {
        fl = (FrameLayout) view.findViewById(R.id.container);
        mapView = new MapView(getContext());

        mapView.layout(0, 0, fl.getWidth(), fl.getHeight());
        fl.addView(mapView);
        mapView.setClickable(true);
        mapView.getMapScaleBar().setVisible(false);
        mapView.setBuiltInZoomControls(true);
        mapView.getMapZoomControls().setZoomLevelMin((byte) 10);
        mapView.getMapZoomControls().setZoomLevelMax((byte) 20);

        // create a tile cache of suitable count
        tileCache = AndroidUtil.createTileCache(getContext(), "mapcache",
                mapView.getModel().displayModel.getTileSize(), 1f,
                mapView.getModel().frameBufferModel.getOverdrawFactor());

        mapView.getModel().mapViewPosition.setCenter(
                new LatLong(22.9881563, -82.4648623));
        mapView.getModel().mapViewPosition.setZoomLevel((byte) 15);
        mapView.getMapZoomControls().setZoomLevelMin((byte) 15);
        mapView.getMapZoomControls().setZoomLevelMax((byte) 24);

        // tile renderer layer using internal render theme
        File mapFile = getMapFile();

        if(!mapFile.exists()) {
            Toast.makeText(getContext(),"No se encuentra el archivo de mapas",
                    Toast.LENGTH_LONG).show();
        } else {
            MapDataStore mapDataStore = new MapFile(mapFile);
            this.tileRendererLayer = new TileRendererLayer(tileCache, mapDataStore,
                    this.mapView.getModel().mapViewPosition, false, true,
                    AndroidGraphicFactory.INSTANCE);
            tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER);

            // only once a layer is associated with a mapView the rendering starts
            this.mapView.getLayerManager().getLayers().add(tileRendererLayer) ;

            for (POIMap poi : poisMap) {
                if (poi.isUci()) {
                    createPositionMarker(poi);
                }
            }
        }
    }

    private void createPositionMarker(POIMap poi) {
        InfoMarker positionmarker = new InfoMarker(poi,
                AndroidGraphicFactory.convertToBitmap(getContext().getResources()
                        .getDrawable(poi.getResID())));

        mapView.getLayerManager().getLayers().add(positionmarker);
        mapView.getLayerManager().redrawLayers();
    }

    private File getMapFile() {
        File f = new File(getContext().getFilesDir().getAbsolutePath()
                + File.separator + CopyMapTask.MAP_FILE);

        if (f.exists()) {
            return f;
        } else {
            AssetManager assetManager = getContext().getAssets();

            try {
                InputStream in = assetManager.open(CopyMapTask.MAP_FILE);
                OutputStream out = getContext().openFileOutput(CopyMapTask.MAP_FILE,
                        Context.MODE_PRIVATE);

                try {
                    byte[] buffer = new byte[1024];
                    int read;

                    while((read = in.read(buffer)) != -1){
                        out.write(buffer, 0, read);
                    }
                } finally {
                    in.close();
                    out.close();
                }
            } catch (IOException ex) {
                Toast.makeText(getContext(), "Error accediendo al mapa",
                        Toast.LENGTH_LONG).show();
            }

            return new File(getContext().getFilesDir().getAbsolutePath()
                    + File.separator + CopyMapTask.MAP_FILE);
        }
    }

    private class InfoMarker extends Marker {

        private LatLong latLong;
        private POIMap poi;
        private int offsetX;
        private int offsetY;
        private Paint paint;

        private ImageView placeView1;
        private ImageView placeView2;
        private TextView placeNameView;
        private LinearLayout cardviewPlace;
        private AnimationDrawable animationDrawable;

        InfoMarker(POIMap poi, org.mapsforge.core.graphics.Bitmap bitmap){
            super(new LatLong(poi.getLatitude(),poi.getLongitude()), bitmap, 0, 0);

            this.poi = poi;
            this.latLong = new LatLong(poi.getLatitude(),poi.getLongitude());
            this.offsetX = 0;
            this.offsetY = 0;

            this.paint = AndroidGraphicFactory.INSTANCE.createPaint();
            this.paint.setColor(0x77ff0000);
            this.paint.setStrokeWidth(0);
            this.paint.setStyle(Style.FILL);
        }

        @Override
        public synchronized void draw(BoundingBox boundingBox, byte zoomLevel,
                                      Canvas canvas, Point topLeftPoint) {
            super.draw(boundingBox, zoomLevel, canvas, topLeftPoint);

            int pixelX = (int) (MercatorProjection.longitudeToPixelX(latLong.longitude,
                    zoomLevel) - topLeftPoint.x);
            int pixelY = (int) (MercatorProjection.latitudeToPixelY(latLong.latitude,
                    zoomLevel) - topLeftPoint.y);
            canvas.drawText(poi.getName(), pixelX + offsetX, pixelY + offsetY, paint);
        }

        @Override
        public boolean onTap(LatLong tapLatLong, Point layerXY, Point tapXY) {
            double centerX = layerXY.x + getHorizontalOffset();
            double centerY = layerXY.y + getVerticalOffset();

            double radiusX = (getBitmap().getWidth() / 2) *1.1;
            double radiusY = (getBitmap().getHeight() / 2) *1.1;

            double distX = Math.abs(centerX - tapXY.x);
            double distY = Math.abs(centerY - tapXY.y);

            if( distX < radiusX && distY < radiusY){
                View view = getLayoutInflater(getArguments())
                        .inflate(R.layout.card_place, null);

                viewFlipper = (ViewFlipper) view.findViewById(R.id.ivFlipper);
                placeView1 = (ImageView) view.findViewById(R.id.viewPlaceMap1);
                placeView2 = (ImageView) view.findViewById(R.id.viewPlaceMap2);
                placeNameView = (TextView) view.findViewById(R.id.viewPlaceNameMap);

                cardviewPlace = (LinearLayout) view.findViewById(R.id.mainRootLayout);
                animationDrawable = (AnimationDrawable) cardviewPlace.getBackground();
                animationDrawable.setEnterFadeDuration(2500);
                animationDrawable.setExitFadeDuration(4500);
                animationDrawable.start();

                placeView1.setImageResource(poi.getResIDPict1());
                placeView2.setImageResource(poi.getResIDPict2());

                placeNameView.setText(poi.getName());

                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.fade_in_slideshow));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.fade_out_slideshow));
                viewFlipper.setFlipInterval(FLIP_DURATION);

                if (poi.getResIDPict2() != 0) {
                    viewFlipper.setAutoStart(true);
                    viewFlipper.startFlipping();
                }

                bottomSheetDialog = new BottomSheetDialog(getContext());
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

                return true;
            }

            return false;
        }

    }

}
