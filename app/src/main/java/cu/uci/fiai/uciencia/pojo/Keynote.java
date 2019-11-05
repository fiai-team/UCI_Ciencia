package cu.uci.fiai.uciencia.pojo;

/**
 * Created by Tyto on 20/9/2018.
 */

public class Keynote {

    public static final String TAG = "K3YN0T35";
    static final String TABLE_NAME = "keynotes";

    static final String FIELD_ID = "id";
    static final String FIELD_SPEAKER = "speaker";
    static final String FIELD_NAME = "name";
    static final String FIELD_META_ES = "metadata_es";
    static final String FIELD_META_EN = "metadata_en";
    static final String FIELD_ABSTRACT = "abstract";
    static final String FIELD_SPANISH = "spanish";
    static final String FIELD_RESOURCE = "resource";
    static final String FIELD_DAY = "day";
    static final String FIELD_LOCATION = "location";
    static final String FIELD_TIME_START = "time_start";
    static final String FIELD_TIME_END = "time_end";
    static final String FIELD_POI = "poi";

    long id;
    String speaker;
    String name;
    String metadataEs;
    String metadataEn;
    String abstrakt;
    boolean isSpanish;
    String resource;
    int res;
    String day;
    String location;
    String timeStart;
    String timeEnd;
    POIMap poiMap;

    public Keynote() {
        this(DatabaseHelper.INVALID_ID, "", "", "", "", "", false, "", "",
                "", "", "");
    }

    public Keynote(long id, String speaker, String name, String metadataEs,
                   String metadataEn, String abstrakt, boolean isSpanish,
                   String resource, String day, String location, String timeStart,
                   String timeEnd) {
        this.id = id;
        this.speaker = speaker;
        this.name = name;
        this.metadataEs = metadataEs;
        this.metadataEn = metadataEn;
        this.abstrakt = abstrakt;
        this.isSpanish = isSpanish;
        this.resource = resource;
        this.day = day;
        this.location = location;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.res = 0;
        this.poiMap = null;
    }

    public Keynote(long id, String speaker, String name, String metadataEs,
                   String metadataEn, String abstrakt, String isSpanish,
                   String resource, String day, String location, String timeStart,
                   String timeEnd) {
        this(DatabaseHelper.INVALID_ID, speaker, name, metadataEs, metadataEn,
                abstrakt, Boolean.parseBoolean(isSpanish), resource, day, location,
                timeStart, timeEnd);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetadataEs() {
        return metadataEs;
    }

    public void setMetadataEs(String metadataEs) {
        this.metadataEs = metadataEs;
    }

    public String getMetadataEn() {
        return metadataEn;
    }

    public void setMetadataEn(String metadataEn) {
        this.metadataEn = metadataEn;
    }

    public String getAbstract() {
        return abstrakt;
    }

    public void setAbstract(String abstrakt) {
        this.abstrakt = abstrakt;
    }

    public boolean isSpanish() {
        return isSpanish;
    }

    public void setIsSpanish(boolean isSpanish) {
        this.isSpanish = isSpanish;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public POIMap getPoiMap() {
        return poiMap;
    }

    public void setPoiMap(POIMap poiMap) {
        this.poiMap = poiMap;
    }

}
