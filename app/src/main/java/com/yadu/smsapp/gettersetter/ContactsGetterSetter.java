
package com.yadu.smsapp.gettersetter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactsGetterSetter implements Parcelable
{

    @SerializedName("contacts")
    @Expose
    private List<Contact> contacts = null;
    public final static Creator<ContactsGetterSetter> CREATOR = new Creator<ContactsGetterSetter>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ContactsGetterSetter createFromParcel(Parcel in) {
            return new ContactsGetterSetter(in);
        }

        public ContactsGetterSetter[] newArray(int size) {
            return (new ContactsGetterSetter[size]);
        }

    }
    ;

    protected ContactsGetterSetter(Parcel in) {
        in.readList(this.contacts, (Contact.class.getClassLoader()));
    }

    public ContactsGetterSetter() {
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(contacts);
    }

    public int describeContents() {
        return  0;
    }

}
