package joesogard.mymoney.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import joesogard.mymoney.TransactionDayUtils;

public class TransactionModel {

    public static HashMap<Integer, TransactionModel> TRANSACTION_MAP;
    public static List<TransactionModel> TRANSACTION_LIST = new ArrayList<>();
    private static final long seed = System.currentTimeMillis();
    private static Random random;

    public int id;
    public String title;
    public String description;
    public float amount;
    public Calendar date;

    public TransactionModel(int id, String title, String description, float amount, Calendar date){
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public static void AddTransaction(TransactionModel transactionModel){
        TRANSACTION_MAP.put(transactionModel.id, transactionModel);
    }

    public static TransactionModel GenerateTransaction(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                random.nextInt() % TransactionDayUtils.getToday().get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, random.nextInt() % 24);
        calendar.set(Calendar.MINUTE, random.nextInt() % 60);

        int id = random.nextInt();
        float balance = (random.nextFloat() - 0.5f) * 100f;

        TransactionModel transactionModel = new TransactionModel(id, "TransactionModel_" + id, "", balance, calendar);
        return transactionModel;
    }

    public static void getTransactionData() {
        random = new Random(seed);
        TRANSACTION_MAP = new HashMap<>();
        for (int i = 0; i < 15; i++) {
            AddTransaction(GenerateTransaction());
        }
    }
}
