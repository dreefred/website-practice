package ph.activelearning;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author giann
 */
public class DateTimeGenerator {
    public static String generateDateTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}