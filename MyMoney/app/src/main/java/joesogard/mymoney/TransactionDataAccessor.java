package joesogard.mymoney;

import android.content.Context;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import joesogard.mymoney.model.TransactionModel;

public class TransactionDataAccessor {
    protected static HashMap<Long, TransactionModel> __TRANSACTION_MAP = new HashMap<>();
    protected static List<TransactionModel> __TRANSACTION_LIST = new ArrayList<>();
    protected static Map<Integer, List<TransactionModel>> TRANSACTION_MAP = new HashMap<>();

    private Context context;

    public TransactionDataAccessor() {
    }

    public TransactionDataAccessor(Context context){
        this.context = context;
    }

    protected List<TransactionModel> fetchTransactions(Context context) {
        List<TransactionModel> fetchedList = new ArrayList<>();
        if(Consts.Debug.FAKE_TRANSACTION_DATA){

            JSONArray jsonArray = readJSONFile(context, Consts.Debug.TRANSACTION_FILE_NAME);
            jsonArray.forEach(
                    (jsonObject) -> {
                        try {
                            fetchedList.add(new TransactionModel((org.json.simple.JSONObject)jsonObject));
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }
                    });
            return fetchedList;
        }

        return null;
    }

    protected JSONArray readJSONFile(Context context, String fileName){
        try{
            InputStream inputStream = context.getAssets().open(fileName);
            JSONArray object = (JSONArray)new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));
            return (JSONArray) object;
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tests:
     * syncTransactions_AddMultipleToMap
     * syncTransactions_AddMultipleToList
     */
    public void syncTransactions() {
        List<TransactionModel> fetch = fetchTransactions(context), newValue;
        int dateKey;
        boolean exists;

        for (TransactionModel transaction :
                fetch) {
            dateKey = transaction.date.get(Calendar.DAY_OF_MONTH);
            exists = TRANSACTION_MAP.containsKey(dateKey);
            newValue = TRANSACTION_MAP.getOrDefault(dateKey, new ArrayList<>());
            newValue.add(transaction);
            if(exists)
                TRANSACTION_MAP.replace(dateKey, newValue);
            else
                TRANSACTION_MAP.put(dateKey, newValue);
//            __TRANSACTION_MAP.put(transaction.id, transaction);
//            insertTransactionByDate(transaction);
        }
//        __TRANSACTION_LIST.addAll(list);
//        __TRANSACTION_LIST.sort((o1, o2) -> o1.date.compareTo(o2.date));
    }

    public TransactionModel getTransaction(long id){
        return __TRANSACTION_MAP.get(id);
    }

    public ListIterator<TransactionModel> getTransactionListIterator(){
        if(__TRANSACTION_LIST.size() == 0)
            return null;
        return __TRANSACTION_LIST.listIterator(__TRANSACTION_LIST.size());
    }

    private void insertTransactionByDate(TransactionModel transaction){
        Calendar before = null, after = null;
        for(int index = __TRANSACTION_LIST.size()-1; index >= 0; index--){
            after = before;
            before = __TRANSACTION_LIST.get(index).date;
            if(transaction.date.after(before) && (after == null || transaction.date.before(after)) ){
                __TRANSACTION_LIST.add(index + 1, transaction);
                return;
            }
        }
        __TRANSACTION_LIST.add(0, transaction);
    }

    /**
     *
     * Tests:
     * getTransactionsByDay_OneTransactionReturnList
     * getTransactionsByDay_NoTransactionsReturnEmpty
     * @param mDate
     * @return
     */
    public List<TransactionModel> getTransactionsByDay(Calendar mDate) {
//        ListIterator<TransactionModel> transactionIterator = __TRANSACTION_LIST.listIterator();
//        TransactionModel transactionModel;
//        int compare, day = mDate.get(Calendar.DAY_OF_MONTH);
//        List<TransactionModel> dayTransactions = new ArrayList<>();
//
//        while(transactionIterator.hasNext()){
//            transactionModel = transactionIterator.next();
//            compare = transactionModel.date.get(Calendar.DAY_OF_MONTH);
//            if(compare > day){
//                break;
//            }
//            if(compare == day){
//                dayTransactions.add(transactionModel);
//            }
//        }
        return TRANSACTION_MAP.getOrDefault(mDate.get(Calendar.DAY_OF_MONTH), new ArrayList<>());
    }
}
