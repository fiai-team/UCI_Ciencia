package cu.uci.fiai.uciencia.pojo.old;

import java.io.Serializable;

import cu.uci.fiai.uciencia.pojo.DatabaseHelper;

/**
 * Created by Tyto on 8/9/2018.
 */

public class Conference implements Serializable {

    public static final String TAG = "C0nf3r3nc3";

    public static final String TABLE_NAME = "conferences";

    public static final String FIELD_ID = "id_conference";
    public static final String FIELD_KEYNOTE = "keynote_conference";
    public static final String FIELD_PEOPLE = "people_conference";
    public static final String FIELD_ORIGIN = "origin_conference";
    public static final String FIELD_ABSTRACT = "abstract_conference";
    public static final String FIELD_RES = "res_conference";
    public static final String FIELD_SPANISH = "spanish_abstract";

    private long id;
    private String keynote;
    private String people;
    private String origin;
    private String abstr;
    private String res;
    private boolean spanish;
    private int resID;

    public Conference() {
        this("", "", "", "", "", false);
    }

    public Conference(long id, String keynote, String people, String origin,
                      String abstr, String res, boolean spanish) {
        this.id = id;
        this.keynote = keynote;
        this.people = people;
        this.origin = origin;
        this.abstr = abstr;
        this.res = res;
        this.spanish = spanish;
        this.resID = 0;
    }

    public Conference(String keynote, String people, String origin,
                      String abstr, String res, boolean spanish) {
        this(DatabaseHelper.INVALID_ID, keynote, people, origin, abstr, res, spanish);
    }

    public Conference(long id, String keynote, String people, String origin,
                      String abstr, String res, String spanish) {
        this(id, keynote, people, origin, abstr, res, Boolean.parseBoolean(spanish));
    }

    public Conference(String keynote, String people, String origin,
                      String abstr, String res, String spanish) {
        this(DatabaseHelper.INVALID_ID, keynote, people, origin, abstr, res, spanish);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeynote() {
        return keynote;
    }

    public void setKeynote(String keynote) {
        this.keynote = keynote;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAbstract() {
        return abstr;
    }

    public void setAbstract(String abstr) {
        this.abstr = abstr;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public boolean isSpanish() {
        return spanish;
    }

    public String getSpanish() {
        return String.valueOf(spanish);
    }

    public void setSpanish(boolean spanish) {
        this.spanish = spanish;
    }

    public void setSpanish(String spanish) {
        setSpanish(Boolean.parseBoolean(spanish));
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "\n id=" + id +
                "\n keynote='" + keynote + '\'' +
                "\n people='" + people + '\'' +
                "\n origin='" + origin + '\'' +
                "\n abstr='" + abstr + '\'' +
                "\n res='" + res + '\'' +
                "\n spanish=" + spanish +
                "\n resID=" + resID +
                "\n}";
    }

}
