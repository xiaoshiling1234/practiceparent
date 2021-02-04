import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

public class MapTest {
    public static void main(String[] args) {
        System.out.println(getNearMonths(201201, 8));
    }

    private static List<Integer> getNearMonths(Integer dateCode, int range) {
        ArrayList<Integer> months = new ArrayList<>();
        int year = Integer.parseInt(dateCode.toString().substring(0, 4));
        int month = Integer.parseInt(
                dateCode.toString().substring(4).startsWith("0") ?
                        dateCode.toString().substring(4).replace("0", "") : dateCode.toString().substring(4));
        if (month >= 2 && month < 8) {
            for (int i = month; i >= 2; i--) {
                months.add(year * 100 + i);
            }
        } else {
            for (int i = month; (i >= 8 && i <= 12) || (i == 1); i--) {
                months.add(year * 100 + i);
                if (i == 1) {
                    i = 13;
                    year -= 1;
                }
            }
        }
        if (months.size() >= range) {
            return months.subList(0, range);
        }
        return months;
    }
}
