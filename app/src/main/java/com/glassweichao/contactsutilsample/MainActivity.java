package com.glassweichao.contactsutilsample;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.glassweichao.contactsutil.Adapter.SortListAdapter;
import com.glassweichao.contactsutil.Bean.ContactsBean;
import com.glassweichao.contactsutil.ContactsManager;
import com.glassweichao.contactsutil.utils.CharacterParser;
import com.glassweichao.contactsutil.utils.PinyinComparator;
import com.glassweichao.contactsutil.widget.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by weichao on 15-6-4.
 */
public class MainActivity extends Activity {

    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private SideBar sidebar;
    private TextView diglog;
    private SortListAdapter adapter;
    private ListView listView;
    private List<ContactsBean> contactsBeanList;
    private EditText etSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
    }

    private void initLayout(){
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        etSearch = (EditText) findViewById(R.id.filter_edit);
        sidebar = (SideBar)findViewById(R.id.sidebar);
        diglog = (TextView)findViewById(R.id.dialog);
        listView = (ListView) findViewById(R.id.contacts_lvcountry);
        sidebar.setTextView(diglog);

        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    listView.setSelection(position);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactsBean data = (ContactsBean) adapter.getItem(position);
                ArrayList<String> phones = ContactsManager.getInstance().getPhoneById(getApplicationContext(), data.getContactId());
                StringBuffer showData = new StringBuffer();
                showData.append(data.getName() + " : ");
                for (int i = 0; i < phones.size(); i++) {
                    showData.append(phones.get(i) + ",");
                }
                Toast.makeText(getApplication(), showData.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        contactsBeanList = filledData(ContactsManager.getInstance().getContacts(this));
        Collections.sort(contactsBeanList,pinyinComparator);
        adapter = new SortListAdapter(this,contactsBeanList);
        listView.setAdapter(adapter);


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 为ListView填充数据
     * @param data
     * @return
     */
    private List<ContactsBean> filledData(List<ContactsBean> data){
        List<ContactsBean> mSortList = new ArrayList<ContactsBean>();

        for(int i=0; i<data.size(); i++){
            ContactsBean ContactsBean = data.get(i);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(ContactsBean.getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                ContactsBean.setSortLetters(sortString.toUpperCase());
            }else{
                ContactsBean.setSortLetters("#");
            }

            mSortList.add(ContactsBean);
        }

        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ContactsBean> filterDateList = new ArrayList<ContactsBean>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = contactsBeanList;
        } else {
            filterDateList.clear();
            for (ContactsBean ContactsBean : contactsBeanList) {
                String name = ContactsBean.getName();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(ContactsBean);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
}