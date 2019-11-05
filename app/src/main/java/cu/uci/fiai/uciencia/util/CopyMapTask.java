package cu.uci.fiai.uciencia.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Tyto on 28/7/2018.
 */

public class CopyMapTask extends AsyncTask<String, Integer, Integer> {

    private static final int COPY_ERROR = -1;
    private static final int EXIST = 0;
    private static final int COPIED = 1;
    public static final String MAP_FILE = "uci.map";

    private final Context context;
    private boolean canCopy;

    public CopyMapTask(Context context) {
        this.context = context;
        canCopy = false;
    }

    @Override
    protected void onPreExecute() {
        File file = new File(context.getFilesDir().getAbsolutePath()
                + File.separator + MAP_FILE);

        canCopy = !file.exists();
    }

    @Override
    protected Integer doInBackground(String...strings) {
        if (canCopy) {
            AssetManager assetManager = context.getAssets();

            try {
                InputStream inputStream = assetManager.open(MAP_FILE);
                OutputStream outputStream = context.openFileOutput(MAP_FILE,
                        Context.MODE_PRIVATE);

                try {
                    byte[] buffer = new byte[1024];
                    int read;

                    while((read = inputStream.read(buffer)) != -1){
                        outputStream.write(buffer, 0, read);
                    }
                } finally {
                    inputStream.close();
                    outputStream.close();
                }
            } catch (IOException ex) {
                return COPY_ERROR;
            }

            return COPIED;
        } else {
            return EXIST;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer) {
            case COPY_ERROR:
                Toast.makeText(context, "Error copiando mapa de la UCI",
                        Toast.LENGTH_LONG).show();
                break;
            case EXIST:
                Log.i(context.getPackageName(), "Mapa existente en el dispositivo.");
                break;
            case COPIED:
                Log.i(context.getPackageName(), "Mapa copiado en el dispositivo.");
                break;
        }
    }

}
