package com.glassweichao.contactsutil.utils;

import com.glassweichao.contactsutil.Bean.ContactsBean;

import java.util.Comparator;

/**
 * Created by weichao on 15-6-4.
 */
public class PinyinComparator implements Comparator<ContactsBean>{
    @Override
    public int compare(ContactsBean o1, ContactsBean o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
