package com.yadu.smsapp.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yadu.smsapp.R;
import com.yadu.smsapp.gettersetter.MessageGetterSetter;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MessageGetterSetter} and makes a call to the
 * specified {@link MessageListFragment.OnMessageListClickedListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMessagesRecyclerViewAdapter extends RecyclerView.Adapter<MyMessagesRecyclerViewAdapter.ViewHolder> {

    private final List<MessageGetterSetter> mValues;
    private final MessageListFragment.OnMessageListClickedListener mListener;

    public MyMessagesRecyclerViewAdapter(List<MessageGetterSetter> items, MessageListFragment.OnMessageListClickedListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(mValues.get(position).getName());
        holder.otp.setText("OTP - "+mValues.get(position).getOtp());
        holder.time.setText(mValues.get(position).getTime());
        holder.mob_no.setText(mValues.get(position).getMob_no());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onMessageListClicked(holder.mItem);
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
        public final TextView name, otp, time, mob_no;
        public MessageGetterSetter mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.name);
            otp = (TextView) view.findViewById(R.id.otp);
            time = (TextView) view.findViewById(R.id.time);
            mob_no = (TextView) view.findViewById(R.id.mob_no);

        }

    }
}
