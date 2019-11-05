package cu.uci.fiai.uciencia.util;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tyto on 26/6/2018.
 */

public class Utils {

    public static final String MONDAY = "lunes";
    public static final String TUESDAY = "martes";
    public static final String WEDNESDAY = "miércoles";
    public static final String THURSDAY = "jueves";
    public static final String FRIDAY = "viernes";
    public static final String SATURDAY = "sábado";
    public static final String SUNDAY = "domingo";

    public static final String JANUARY = "enero";
    public static final String FEBRARY = "febrero";
    public static final String MARCH = "marzo";
    public static final String APRIL = "abril";
    public static final String MAY = "mayo";
    public static final String JUNE = "junio";
    public static final String JULY = "julio";
    public static final String AUGUST = "agosto";
    public static final String SEPTEMBER = "septiembre";
    public static final String OCTOBER = "octubre";
    public static final String NOVEMBER = "noviembre";
    public static final String DECEMBER = "diciembre";

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String format = sdf.format(date);

        return format;
    }

    public static String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String format = sdf.format(date);

        return format;
    }

    @NonNull
    public static String getDayWeek(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sdf.parse(date);
            Calendar calendar = sdf.getCalendar();

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                return MONDAY;
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                return TUESDAY;
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                return WEDNESDAY;
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                return THURSDAY;
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                return FRIDAY;
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                return SATURDAY;
            } else {
                return SUNDAY;
            }
        } catch (ParseException ex) {
            return date;
        }
    }

    public static Date getDateFromString(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        return sdf.parse(date);
    }

    public static Calendar getCalendar(String date) throws ParseException {
        return getCalendar(date, "yyyy/MM/dd");
    }

    public static Calendar getCalendar(String date, String format)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.parse(date);
        return sdf.getCalendar();
    }

    public static Calendar getCalendar(String date, String time, String format)
            throws ParseException {


        final int hours = Integer.valueOf(time.substring(0, 2));
        final int minutes = Integer.valueOf(time.substring(2));
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        sdf.parse(date);

        Calendar calendar = sdf.getCalendar();

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);

        return calendar;
    }

    public static String dayAndMonthFromText(String date){
        String day = date.substring(5);
        String month = day.substring(0, 2);

        if (month.equalsIgnoreCase("01")) {
            month = JANUARY;
        } else if (month.equalsIgnoreCase("02")) {
            month = FEBRARY;
        } else if (month.equalsIgnoreCase("03")) {
            month = MARCH;
        } else if (month.equalsIgnoreCase("04")) {
            month = APRIL;
        } else if (month.equalsIgnoreCase("05")) {
            month = MAY;
        } else if (month.equalsIgnoreCase("06")) {
            month = JUNE;
        } else if (month.equalsIgnoreCase("07")) {
            month = JULY;
        } else if (month.equalsIgnoreCase("08")) {
            month = AUGUST;
        } else if (month.equalsIgnoreCase("09")) {
            month = SEPTEMBER;
        } else if (month.equalsIgnoreCase("10")) {
            month = OCTOBER;
        } else if (month.equalsIgnoreCase("11")) {
            month = NOVEMBER;
        } else if (month.equalsIgnoreCase("12")) {
            month = DECEMBER;
        }

        if (day.charAt(3) == '0') {
            day = String.valueOf(day.charAt(4));
        } else {
            day = day.substring(3);
        }

        return day + " de " + month;
    }

    public static String dateFromText(String date){
        StringBuffer dateBuffer = new StringBuffer();

        String year = date.substring(0, 4);
        String day = date.substring(5);
        String month = day.substring(0, 2);

        if (day.charAt(3) == '0') {
            day = String.valueOf(day.charAt(4));
        } else {
            day = day.substring(3);
        }

        dateBuffer.append(day);
        dateBuffer.append(" de ");

        if (month.equalsIgnoreCase("01")) {
            dateBuffer.append(JANUARY);
        } else if (month.equalsIgnoreCase("02")) {
            dateBuffer.append(FEBRARY);
        } else if (month.equalsIgnoreCase("03")) {
            dateBuffer.append(MARCH);
        } else if (month.equalsIgnoreCase("04")) {
            dateBuffer.append(APRIL);
        } else if (month.equalsIgnoreCase("05")) {
            dateBuffer.append(MAY);
        } else if (month.equalsIgnoreCase("06")) {
            dateBuffer.append(JUNE);
        } else if (month.equalsIgnoreCase("07")) {
            dateBuffer.append(JULY);
        } else if (month.equalsIgnoreCase("08")) {
            dateBuffer.append(AUGUST);
        } else if (month.equalsIgnoreCase("09")) {
            dateBuffer.append(SEPTEMBER);
        } else if (month.equalsIgnoreCase("10")) {
            dateBuffer.append(OCTOBER);
        } else if (month.equalsIgnoreCase("11")) {
            dateBuffer.append(NOVEMBER);
        } else if (month.equalsIgnoreCase("12")) {
            dateBuffer.append(DECEMBER);
        }

        dateBuffer.append(" de ");
        dateBuffer.append(year);

        return dateBuffer.toString();
    }

    public static String smallDateFromText(String date){
        StringBuffer buffer = new StringBuffer();

        String year = date.substring(0, 4);
        String day = date.substring(5);
        String month = day.substring(0, 2);

        if (day.charAt(3) == '0') {
            day = String.valueOf(day.charAt(4));
        } else {
            day = day.substring(3);
        }

        buffer.append(day);
        buffer.append(" de ");

        if (month.equalsIgnoreCase("01")) {
            buffer.append(JANUARY.substring(0, 3));
        } else if (month.equalsIgnoreCase("02")) {
            buffer.append(FEBRARY.substring(0, 3));
        } else if (month.equalsIgnoreCase("03")) {
            buffer.append(MARCH.substring(0, 3));
        } else if (month.equalsIgnoreCase("04")) {
            buffer.append(APRIL.substring(0, 3));
        } else if (month.equalsIgnoreCase("05")) {
            buffer.append(MAY.substring(0, 3));
        } else if (month.equalsIgnoreCase("06")) {
            buffer.append(JUNE.substring(0, 3));
        } else if (month.equalsIgnoreCase("07")) {
            buffer.append(JULY.substring(0, 3));
        } else if (month.equalsIgnoreCase("08")) {
            buffer.append(AUGUST.substring(0, 3));
        } else if (month.equalsIgnoreCase("09")) {
            buffer.append(SEPTEMBER.substring(0, 3));
        } else if (month.equalsIgnoreCase("10")) {
            buffer.append(OCTOBER.substring(0, 3));
        } else if (month.equalsIgnoreCase("11")) {
            buffer.append(NOVEMBER.substring(0, 3));
        } else if (month.equalsIgnoreCase("12")) {
            buffer.append(DECEMBER.substring(0, 3));
        }

        buffer.append(". de ");
        buffer.append(year);

        return buffer.toString();
    }

    public static String dateAndTimeFromText(String date, String time){
        String dateString = dateFromText(date);
        StringBuffer timeBuffer = new StringBuffer();
        int hours = Integer.valueOf(time.substring(0, 2));
        String minutes = time.substring(2);
        String am_pm = hours < 12 ? "AM" : "PM";

        hours = hours12(hours);

        timeBuffer.append(hours);
        timeBuffer.append(":");
        timeBuffer.append(minutes);
        timeBuffer.append(" ");
        timeBuffer.append(am_pm);

        return dateString + ", " + timeBuffer.toString();
    }

    public static String smallDateAndTimeFromText(String date, String time){
        StringBuffer buffer = new StringBuffer();
        String dayWeek = getDayWeek(date).substring(0, 3);
        dayWeek = dayWeek.substring(0,1).toUpperCase().concat(dayWeek.substring(1));
        int hours = Integer.valueOf(time.substring(0, 2));
        String minutes = time.substring(2);
        String am_pm = hours < 12 ? "a.m." : "p.m.";
        hours = hours12(hours);

        buffer.append(hours);
        buffer.append(":");
        buffer.append(minutes);
        buffer.append(" ");
        buffer.append(am_pm);
        buffer.append(" - ");
        buffer.append(dayWeek);
        buffer.append(". ");
        buffer.append(smallDateFromText(date));

        return buffer.toString();
    }

    public static String timeFromText(String time) {
        StringBuilder builder = new StringBuilder();
        int hours = Integer.valueOf(time.substring(0, 2));
        String minutes = time.substring(2);
        String am_pm = hours < 12 ? "a.m." : "p.m.";
        hours = hours12(hours);

        builder.append(hours);
        builder.append(":");
        builder.append(minutes);
        builder.append(" ");
        builder.append(am_pm);

        return builder.toString();
    }

    public static int hours12(int hours24) {
        if (hours24 == 13) {
            return 1;
        } else if (hours24 == 14) {
            return 2;
        } else if (hours24 == 15) {
            return 3;
        } else if (hours24 == 16) {
            return 4;
        } else if (hours24 == 17) {
            return 5;
        } else if (hours24 == 18) {
            return 6;
        } else if (hours24 == 19) {
            return 7;
        } else if (hours24 == 20) {
            return 8;
        } else if (hours24 == 21) {
            return 9;
        } else if (hours24 == 22) {
            return 10;
        } else if (hours24 == 23) {
            return 11;
        } else if (hours24 == 0) {
            return 12;
        } else {
            return hours24;
        }
    }

    public static String stringFromCalendar(Calendar calendar) {
        return calendar.get(Calendar.YEAR) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" +
                calendar.get(Calendar.DAY_OF_MONTH) + " " +
                calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                calendar.get(Calendar.MINUTE) + ":" +
                calendar.get(Calendar.SECOND) + "." +
                calendar.get(Calendar.MILLISECOND);
    }

    public static ArrayList<String> toArrayListFromString(String text,
                                                          char separator) {
        ArrayList<String> arrayList = new ArrayList<>();
        String temp = text.trim().replace(" ", "");
        int end = temp.indexOf(separator);

        while (end != -1) {
            arrayList.add(temp.substring(0, end));
            temp = temp.substring(end + 1);
            end = temp.indexOf(separator);
        }

        arrayList.add(temp);

        return arrayList;
    }

    /**
     * Rounds a double to the given number of decimal places.
     *
     * @param value the double value
     * @param afterDecimalPoint the number of digits after the decimal point
     * @return the double rounded to the given precision
     */
    public static/* @pure@ */double roundDouble(double value,
                                                int afterDecimalPoint) {

        double mask = Math.pow(10.0, afterDecimalPoint);

        return (Math.round(value * mask)) / mask;
    }

    /**
     * Causa la vibración del dispositivo durante 250 milisegundos.
     */
    public static void vibrate(Context context) {
        vibrate(context, 250);
    }

    /**
     * Causa la vibración del dispositivo durante los milisegundos
     * dados.
     *
     * @param milliseconds duración en milisegundos de la vibración
     *                     del dispositivos
     */
    public static void vibrate(Context context, int milliseconds) {
        Vibrator vibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        }
    }

}
