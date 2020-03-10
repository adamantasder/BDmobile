package com.khaki.jxc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.khaki.jxc.bean.Inventory;
import com.khaki.jxc.bean.PurOrdersVo;
import com.khaki.jxc.client.R;
import com.khaki.jxc.view.AddSubView;


import java.util.List;

/**
 * 清单列表adapter
 */

public class InventoryOutAdapter extends BaseRecyclerViewAdapter<Inventory> {

    private OnDeleteClickLister mDeleteClickListener;
    private OnAddClickLister mAddClickListener;

    private Button btnClose;
    public InventoryOutAdapter(Context context, List<Inventory> data) {
        super(context, data, R.layout.item_inventroyout);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, Inventory bean, int position) {
        View view = holder.getView(R.id.tv_deleteout);

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


        ((TextView) holder.getView(R.id.ivouchrownoout)).setText(position+1+"");
      ((TextView) holder.getView(R.id.cInvCodeout)).setText(bean.getcInvCode());
        ((TextView) holder.getView(R.id.cInvNameout)).setText(bean.getcInvName());
        ((TextView) holder.getView(R.id.cInvStdout)).setText(bean.getcInvStd());
        Log.e("currentIQ", "currentIQ----->"+bean.getCurrentIQ());
        Log.e("currentIQ", "currentIQ----->"+bean.getiQuantity());
        ((TextView) holder.getView(R.id.currentIQ)).setText(bean.getCurrentIQ()+"");
      ((AddSubView) holder.getView(R.id.iQuantityout)).setValue(bean.getiQuantity()+"" );
        holder.itemView.setTag(position);
        Log.e("位置", "postion3----->"+position);
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }
    //数值加减方法
    public void setOnAddClickListener(OnAddClickLister listener) {
        this.mAddClickListener = listener;
    }



    public interface OnAddClickLister {


        void onItemClick(View v,  int i, Integer valueOf);

    }
    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }


}
