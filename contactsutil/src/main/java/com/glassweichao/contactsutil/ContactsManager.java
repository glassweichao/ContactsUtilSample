package com.glassweichao.contactsutil;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.glassweichao.contactsutil.Bean.ContactsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weichao on 15-6-4.
 */
public class ContactsManager {

    private static final String[] PROJECTIONS = {
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    private static ContactsManager instance;

    private ContactsManager() {
    }

    public static ContactsManager getInstance() {
        if (instance == null) {
            instance = new ContactsManager();
        }
        return instance;
    }

    public List<ContactsBean> getContacts(Context context) {
        List<ContactsBean> list = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor c = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                PROJECTIONS,
                null,
                null,
                null);

        ContactsBean contactsBean;
        c.moveToFirst();
        while (c.moveToNext()) {
            contactsBean = new ContactsBean();
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            int hasNumber = c.getInt(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            if (hasNumber > 0) {
                contactsBean.setName(name);
                contactsBean.setContactId(id);
                list.add(contactsBean);
            }
        }
        c.close();
        return list;
    }

    public ArrayList<String> getPhoneById(Context context, String id) {
        if (id == null) {
            return null;
        }

        ArrayList<String> phoneList = new ArrayList<>();
        Cursor cNumber = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                null,
                null);
        while (cNumber.moveToNext()) {
            phoneList.add(cNumber.getString(cNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }
        return phoneList;
    }

    public boolean deleteContacts(Context context, String id) {
        if (id == null) {
            return false;
        }
        int isDelete = context.getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI,
                ContactsContract.RawContacts._ID + "=" + id,
                null);
        if(isDelete>0)
            return true;
        else
            return false;
    }

}
