package com.khaki.jxc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;

import com.khaki.jxc.Contants;
import com.khaki.jxc.bean.Contact;
import com.khaki.jxc.bean.untils.ImageUtils;
import com.khaki.jxc.client.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

/**
 * Created by 嘉进 on 11:19.
 */

public class ContactAdapter extends ContactListAdapter<ContactAdapter.ContactViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private boolean flag=false;//判断是好友还是手机通讯录

    public ContactAdapter(Context mContext, boolean flag) {
        this.mContext = mContext;
        this.flag = flag;
    }

    @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         /*  View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_item, parent, false);
            Log.e("ContactViewHolder", "Create");*/
            return null;// new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, final int position) {
       /*     Log.e("adapterItem", getItem(position).getName());
            Contact contact = getItem(position);
            holder.contactST.setLeftTopString(getItem(position).getName());
            holder.contactST.setLeftBottomString(getItem(position).getPhone());


            holder.contactST.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag){
                        Intent intent=new Intent(mContext, FriendsInfoActivity.class);
                        Contact contact=getItem(position);
                        intent.putExtra("phone",contact.getPhone());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });

            if (contact.getPhotoURL() == null || contact.getPhotoURL().equals("")){
                holder.icon.setImageDrawable(ImageUtils.getIcon(contact.getName(), 23));
            }else {
                Glide.with(mContext)
                        .load(Contants.PHOTO_SERVER_IP + contact.getPhotoURL())
                        .into(holder.icon);
            }
*/

        }

        @Override
        public long getHeaderId(int position) {
            return 0;//getItem(position).getFirstLetter().charAt(0);
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_header, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
          /*  Log.e("adapterHeard", String.valueOf(getItem(position).getFirstLetter()));
            textView.setText(String.valueOf(getItem(position).getFirstLetter()));*/
        }

        class ContactViewHolder extends RecyclerView.ViewHolder{
            SuperTextView contactST;
            ImageView icon;
            public ContactViewHolder(View view){
                super(view);
              //  contactST = (SuperTextView) view.findViewById(R.id.st_contact);
                icon = (ImageView) view.findViewById(R.id.image);
            }

        }



}
