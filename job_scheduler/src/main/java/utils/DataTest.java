package main.java.utils;

import java.time.LocalDateTime;

/**
 * Created by Marcin Frankowski on 10.08.2016.
 */
public class DataTest {
    public static void main(String args[]) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String year = String.format("%04d", localDateTime.getYear());
        String month = String.format("%02d", localDateTime.getMonthValue());
        String day = String.format("%02d", localDateTime.getDayOfMonth());
        String hour = String.format("%02d", localDateTime.getHour());
        String minute = String.format("%02d", localDateTime.getMinute());
        String second = String.format("%02d", localDateTime.getSecond());
        String dateString = year + '-' + month + '-' + day + 'T' + hour + ':' + minute + ':' + second;
        System.out.println(dateString);
    }
}
