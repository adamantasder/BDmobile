package com.khaki.jxc.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class purOrderAdapter extends BaseAdapter implements CharSequence {

    private Context ctx;
    // 定义需要包装的JSONArray对象
    private JSONArray jsonArray;
    // 定义列表项显示JSONObject对象的哪个属性
    private String property;

    public purOrderAdapter(Context ctx, JSONArray jsonArray, String property) {
        this.ctx = ctx;
        this.jsonArray = jsonArray;
        this.property = property;
    }
    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return jsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        try {
            // 返回订单号
            return ((JSONObject) getItem(position)).getInt("cPOID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // 创建一个TextView，并设置它显示内容的字体大小
            textView = new TextView(ctx);
            textView.setTextSize(20);
        } else {
            textView = (TextView) convertView;
        }
        // 获取JSONArray数组元素的property属性
        try {
            String itemName = ((JSONObject) getItem(position))
                    .getString(property);
            // 设置TextView所显示的内容
            textView.setText(itemName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return textView;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }
}
