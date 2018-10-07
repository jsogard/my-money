package joesogard.mymoney;

import java.util.Calendar;
import java.util.Date;

public class Consts {

    public static int DEFAULT_INT = Integer.MIN_VALUE;
    public static String DEFAULT_STRING = "defualt-string";
    public static float DEFAULT_FLOAT = Float.MIN_VALUE;

    public static class Debug {

        public static boolean FAKE_TRANSACTION_DATA = true;
        public static final String TRANSACTION_FILE_NAME = "transactions.json";
        public static final int TRANSACTION_DATA_OBJECT_COUNT = 30;

        public static boolean SPOOF_CURRENT_DAY = true;
        public static long SPOOFED_CURRENT_DAY = 1538278392000L;
    }

    public static class TransactionFields {
        public static final String ID = "id",
                DATE = "date",
                TITLE = "title",
                NOTES = "notes",
                BALANCE = "balance",
                JSON_DATA = "data";
    }
}
