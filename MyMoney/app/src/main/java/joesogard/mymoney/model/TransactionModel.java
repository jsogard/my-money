package joesogard.mymoney.model;

import android.support.constraint.ConstraintLayout;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import joesogard.mymoney.Consts;
import joesogard.mymoney.TransactionDayUtils;

public class TransactionModel {

    public static HashMap<Integer, TransactionModel> TRANSACTION_MAP;
    public static List<TransactionModel> TRANSACTION_LIST = new ArrayList<>();
    private static final long seed = System.currentTimeMillis();

    public long id;
    public String title;
    public float balance;
    public Calendar date;

    public TransactionModel(JSONObject jsonObject)
            throws ParseException {
        id = (long)jsonObject.get(Consts.TransactionFields.ID);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TransactionDayUtils.READ_DATE_FORMAT.parse((String)jsonObject.get(Consts.TransactionFields.DATE)));
        date = calendar;
        balance = new Float((double)jsonObject.get(Consts.TransactionFields.BALANCE));
        title = (String)jsonObject.get(Consts.TransactionFields.TITLE);
    }

    public TransactionModel(int id, String title, float balance, Calendar date){
        this.id = id;
        this.title = title;
        this.balance = balance;
        this.date = date;
    }
}
