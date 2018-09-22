package joesogard.mymoney;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import joesogard.mymoney.TransactionDayFragment.OnListFragmentInteractionListener;
import joesogard.mymoney.dummy.DummyContent.DummyItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTransactionDayRecyclerViewAdapter extends RecyclerView.Adapter<MyTransactionDayRecyclerViewAdapter.ViewHolder> {

    private final List<Calendar> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTransactionDayRecyclerViewAdapter(List<Calendar> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transactionday, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(mValues.size() - position - 1);
        holder.mDateView.setText(
                TransactionDayUtils.dateFormat.format(new Date(holder.mItem.getTimeInMillis())));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDateView;
        public Calendar mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = (TextView) view.findViewById(R.id.transactionDayDate);
        }

        @Override
        public String toString() {
            return mDateView.getText().toString();
        }
    }
}
