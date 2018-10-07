package joesogard.mymoney;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;

import joesogard.mymoney.model.TransactionModel;

public class TransactionModelTest {

    private static final String dateStringValid;
    private static final Calendar calendarValid;
    private final float balancePositiveValid = 123.45f;
    private final long idValid = 12345L;
    private final String titleValid = "Valid Title";

    static {
        int year = 2018, month = 11, day = 10;

        calendarValid = Calendar.getInstance();
        calendarValid.set(year, month-1, day);

        dateStringValid = new StringBuilder()
                .append(year).append('-').append(month).append('-').append(day).toString();
    }

    @Test
    public void jsonConstructor_ValidJSONSuccessful(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Consts.TransactionFields.DATE, dateStringValid);
        jsonObject.put(Consts.TransactionFields.BALANCE, new Double(balancePositiveValid));
        jsonObject.put(Consts.TransactionFields.ID, idValid);
        jsonObject.put(Consts.TransactionFields.TITLE, titleValid);

        TransactionModel newTransaction;
        try {
            newTransaction = new TransactionModel(jsonObject);
        } catch (ParseException e) {
            Assert.fail();
            return;
        }
        Assert.assertEquals(calendarValid.get(Calendar.YEAR), newTransaction.date.get(Calendar.YEAR));
        Assert.assertEquals(calendarValid.get(Calendar.MONTH), newTransaction.date.get(Calendar.MONTH));
        Assert.assertEquals(calendarValid.get(Calendar.DAY_OF_MONTH), newTransaction.date.get(Calendar.DAY_OF_MONTH));
    }
}
