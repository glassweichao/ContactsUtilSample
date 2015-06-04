package com.glassweichao.contactsutil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.glassweichao.contactsutil.Bean.ContactsBean;
import com.glassweichao.contactsutil.R;


import java.util.List;

/**
 * Created by weichao on 15-6-4.
 */
public class SortListAdapter extends BaseAdapter implements SectionIndexer {

    private Context context;
    private List<ContactsBean> contactsBeanList;
    final static class UiHolder{
        TextView tvName;
        TextView tvTitle;
    }

    public SortListAdapter(Context context,List<ContactsBean> list){
        this.context = context;
        this.contactsBeanList = list;
    }

    public void updateListView(List<ContactsBean> list){
        this.contactsBeanList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return contactsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UiHolder uiHolder = null;
        final ContactsBean contactsBean = contactsBeanList.get(position);
        if(convertView == null){
            uiHolder = new UiHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.ui_contacts_list_item,null);
            uiHolder.tvTitle = (TextView) convertView.findViewById(R.id.ui_contacts_li_title);
            uiHolder.tvName = (TextView) convertView.findViewById(R.id.ui_contacts_li_catalog);
            convertView.setTag(uiHolder);
        }else{
            uiHolder = (UiHolder) convertView.getTag();
        }

        int section = getSectionForPosition(position);

        if(position == getPositionForSection(section)){
            uiHolder.tvName.setVisibility(View.VISIBLE);
            uiHolder.tvName.setText(contactsBean.getSortLetters());
        }else{
            uiHolder.tvName.setVisibility(View.GONE);
        }

        uiHolder.tvTitle.setText(this.contactsBeanList.get(position).getName());

        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0;i < getCount();i++){
            String sort = contactsBeanList.get(i).getSortLetters();
            char firstChar = sort.toUpperCase().charAt(0);
            if(firstChar == sectionIndex){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return contactsBeanList.get(position).getSortLetters().charAt(0);
    }
}
