package cu.uci.fiai.uciencia.pojo;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Tyto on 21/9/2018.
 */

public class Workshop implements Serializable {

    public static final String TAG = "W0RKSH0P";
    static final String TABLE_NAME = "workshops";

    static final String FIELD_ID = "id";
    static final String FIELD_NAME_ES = "name_es";
    static final String FIELD_NAME_EN = "name_en";
    static final String FIELD_CHAIRMAN = "chairman";
    static final String FIELD_META_ES = "metadata_es";
    static final String FIELD_META_EN = "metadata_en";
    static final String FIELD_LOCATION = "location";
    static final String FIELD_POI = "poi";

    long id;
    String name_es;
    String name_en;
    String chairman;
    String metadataEs;
    String metadataEn;
    String location;
    POIMap poi;

    public Workshop(long id, String name_es, String name_en, String chairman,
                    String metadataEs, String metadataEn, String location) {
        this.id = id;
        this.name_es = name_es;
        this.name_en = name_en;
        this.chairman = chairman;
        this.metadataEs = metadataEs;
        this.metadataEn = metadataEn;
        this.location = location;
        this.poi = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        Locale locale = Locale.getDefault();

        if (locale.toString().contains("es")) {//es_US
            return name_es.trim();
        } else {
            return name_en.trim();
        }
    }

    public String getName_es() {
        return name_es;
    }

    public void setName_es(String name_es) {
        this.name_es = name_es;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getChairman() {
        return chairman;
    }

    public void setChairman(String chairman) {
        this.chairman = chairman;
    }

    public String getMetadataEs() {
        return metadataEs;
    }

    public void setMetadataEs(String metadataEs) {
        this.metadataEs = metadataEs;
    }

    public String getMetadata() {
        Locale locale = Locale.getDefault();

        if (locale.toString().contains("es")) {//es_US
            return metadataEs;
        } else {
            return metadataEn;
        }
    }

    public String getMetadataEn() {
        return metadataEn;
    }

    public void setMetadataEn(String metadataEn) {
        this.metadataEn = metadataEn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public POIMap getPoi() {
        return poi;
    }

    public void setPoi(POIMap poi) {
        this.poi = poi;
    }
}
