package cu.uci.fiai.uciencia.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconButton;

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
import cu.uci.fiai.uciencia.util.CopyMapTask;

/**
 * Created by Tyto on 16/9/2018.
 */

public class MapDialog extends DialogFragment {

    public static final String TAG = "MapDialog";

    private static final String ARG_POI_COUNT = "P01C0UNT";
    private static final String ARG_POI_MAP = POIMap.TAG + "_";

    private ArrayList<POIMap> poiMaps;

    private FrameLayout fl;
    private MapView mapView;
    private TileCache tileCache;
    private TileRendererLayer tileRendererLayer;
    private IconButton ibClose;

    public MapDialog() {}

    public static MapDialog newInstance(ArrayList<POIMap> poiMaps) {
        MapDialog dialog = new MapDialog();
        Bundle args = new Bundle();

        args.putInt(ARG_POI_COUNT, poiMaps.size());

        for (int i = 0; i < poiMaps.size(); i++) {
            args.putSerializable(ARG_POI_MAP + i, poiMaps.get(i));
        }

        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidGraphicFactory.createInstance(getActivity().getApplication());

        poiMaps = new ArrayList<>();

        if (getArguments() != null) {
            final int count = getArguments().getInt(ARG_POI_COUNT);

            for (int i = 0; i < count; i++) {
                POIMap poiMap = (POIMap) getArguments().getSerializable(ARG_POI_MAP + i);

                poiMaps.add(poiMap);
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_map, null);

        fl = (FrameLayout) v.findViewById(R.id.container);
        mapView = new MapView(getContext());
        ibClose = (IconButton) v.findViewById(R.id.ibCloseMap);

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

            for (POIMap poi : poiMaps) {
                if (poi.isUci()) {
                    createPositionMarker(poi);
                }
            }
        }

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity()).setView(v)
                .create();
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

    private void createPositionMarker(POIMap poi) {
        InfoMarker positionmarker = new InfoMarker(poi,
                AndroidGraphicFactory.convertToBitmap(getContext().getResources()
                        .getDrawable(poi.getResID())));

        mapView.getLayerManager().getLayers().add(positionmarker);
        mapView.getLayerManager().redrawLayers();
    }

    private class InfoMarker extends Marker {

        private LatLong latLong;
        private POIMap poi;
        private int offsetX;
        private int offsetY;
        private Paint paint;

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
                Toast.makeText(getContext(), poi.getName(),
                        Toast.LENGTH_SHORT).show();

                return true;
            }

            return false;
        }

    }

}
