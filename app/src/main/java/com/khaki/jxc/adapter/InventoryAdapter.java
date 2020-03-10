package com.khaki.jxc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.khaki.jxc.bean.Inventory;
import com.khaki.jxc.bean.PurOrdersVo;
import com.khaki.jxc.client.R;

import org.json.JSONArray;

import java.util.List;

/**
 * 清单列表adapter
 * <p>
 * Created by DavidChen on 2018/5/30.
 */

public class InventoryAdapter extends BaseRecyclerViewAdapter<PurOrdersVo> {

    private OnDeleteClickLister mDeleteClickListener;

    public InventoryAdapter(Context context, List<PurOrdersVo> data) {
        super(context, data, R.layout.item_inventroy);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, PurOrdersVo bean, int position) {
        View view = holder.getView(R.id.tv_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        ((TextView) holder.getView(R.id.ivouchrow)).setText(bean.getIvouchrowno()+"");
        ((TextView) holder.getView(R.id.cInvCode)).setText(bean.getcInvCode());
        ((TextView) holder.getView(R.id.cInvName)).setText(bean.getcInvName());
        ((TextView) holder.getView(R.id.cInvStd)).setText(bean.getcInvStd());
        ((TextView) holder.getView(R.id.iQuantity)).setText(bean.getiQuantity()+"" );
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}
