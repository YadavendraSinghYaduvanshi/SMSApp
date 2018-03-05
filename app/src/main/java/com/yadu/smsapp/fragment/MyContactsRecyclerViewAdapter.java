package com.yadu.smsapp.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yadu.smsapp.R;
import com.yadu.smsapp.fragment.dummy.DummyContent.DummyItem;
import com.yadu.smsapp.gettersetter.Contact;
import com.yadu.smsapp.gettersetter.ContactsGetterSetter;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link ContactListFragment.OnContactListClickedListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyContactsRecyclerViewAdapter extends RecyclerView.Adapter<MyContactsRecyclerViewAdapter.ViewHolder> {

    private final List<Contact> mValues;
    private final ContactListFragment.OnContactListClickedListener mListener;

    public MyContactsRecyclerViewAdapter(ContactsGetterSetter contacts, ContactListFragment.OnContactListClickedListener listener) {
        mValues = contacts.getContacts();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contacts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tv_name.setText(mValues.get(position).getName());
        holder.tv_mob_no.setText(mValues.get(position).getMobile());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnContactListClicked(holder.mItem);
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
        public final TextView tv_name;
        public final TextView tv_mob_no;
        public Contact mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_mob_no = (TextView) view.findViewById(R.id.tv_mob_no);
        }

    }
}
