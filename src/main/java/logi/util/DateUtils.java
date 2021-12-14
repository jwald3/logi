package logi.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class DateUtils {

    public String getTransitTime(LocalDateTime start, LocalDateTime end) {
        long days = ChronoUnit.DAYS.between(start, end);
        long hours = ChronoUnit.HOURS.between(start, end) % 24;
        long minutes = ChronoUnit.MINUTES.between(start, end) % 60;

        StringBuilder stringBuilder = new StringBuilder();

        if (days > 1) {
            stringBuilder.append(days).append(" days, ");
        } else if (days == 1) {
            stringBuilder.append(days).append(" day, ");
        }

        if (hours > 1) {
            stringBuilder.append(hours).append(" hours, ");
        } else if (hours == 1) {
            stringBuilder.append(hours).append(" hour, ");
        }

        if (minutes > 1) {
            stringBuilder.append(minutes).append(" minutes ");
        } else if (minutes == 1) {
            stringBuilder.append(minutes).append(" minute ");
        }

        return  stringBuilder.toString().trim().replaceAll(",$", "");
    }

    public LocalDateTime convertDateFormat(Timestamp rs) {
        LocalDateTime date = rs.toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
        String formatDateTime = date.format(formatter);

        LocalDateTime formattedDateTime = LocalDateTime.parse(formatDateTime, formatter);

        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(formattedDateTime.format(newFormatter), newFormatter);
    }
}
