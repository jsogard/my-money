package joesogard.mymoney.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TransactionModel {

    public static HashMap<Integer, TransactionModel> TRANSACTION_MAP =
            new HashMap<>();
    public static List<TransactionModel> TRANSACTION_LIST = new ArrayList<>();

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

    static {
        TransactionModel transactionModel;
        Random random = new Random();
        Calendar septFirst = Calendar.getInstance(), calendar;
        septFirst.set(2018, 9, 1, 0, 0, 1);
        for(int i = 0; i < 15; i++){
            calendar = (Calendar)septFirst.clone();
            calendar.add(Calendar.DAY_OF_MONTH, random.nextInt(5));
            transactionModel = new TransactionModel(
                    i, "TransactionModel_" + i,
                    "Lorem Ipsum Dolor",
                    (random.nextFloat() - 0.5f) * 100,
                    calendar
            );
            TRANSACTION_LIST.add(transactionModel);
            TRANSACTION_MAP.put(transactionModel.id, transactionModel);
        }
    }

}
