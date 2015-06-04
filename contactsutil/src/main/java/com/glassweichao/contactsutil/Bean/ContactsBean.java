package com.glassweichao.contactsutil.Bean;

/**
 * Created by weichao on 15-6-4.
 */
public class ContactsBean {

    private String name;   //联系人姓名
    private String contactId;
    private int hasPhoneNumber;
    private String sortLetters;  //显示的数据的拼音首字母

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public int getHasPhoneNumber() {
        return hasPhoneNumber;
    }

    public void setHasPhoneNumber(int hasPhoneNumber) {
        this.hasPhoneNumber = hasPhoneNumber;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
