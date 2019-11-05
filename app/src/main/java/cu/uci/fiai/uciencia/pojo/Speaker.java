package cu.uci.fiai.uciencia.pojo;

import java.io.Serializable;

/**
 * Awesome Pojo Generator
 */
public class Speaker implements Serializable {

    public static final String TAG = "Sp34k3r";
    static final String TABLE_NAME = "speakers";

    static final String FIELD_ID = "id";
    static final String FIELD_NAME = "name";
    static final String FIELD_META_ES = "metadata_es";
    static final String FIELD_META_EN = "metadata_en";

    private long id;
    private String name;
    private String metadata_en;
    private String metadata_es;

    public Speaker(long id, String name, String metadata_en, String metadata_es) {
        this.id = id;
        this.name = name;
        this.metadata_en = metadata_en;
        this.metadata_es = metadata_es;
    }

    public Speaker(String name, String metadata_en, String metadata_es) {
        this(DatabaseHelper.INVALID_ID, name, metadata_en, metadata_es);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMetadata_en(String metadata_en) {
        this.metadata_en = metadata_en;
    }

    public String getMetadata_en() {
        return metadata_en;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setMetadata_es(String metadata_es) {
        this.metadata_es = metadata_es;
    }

    public String getMetadata_es() {
        return metadata_es;
    }
}