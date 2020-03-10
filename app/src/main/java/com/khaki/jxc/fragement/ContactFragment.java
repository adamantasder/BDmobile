package com.khaki.jxc.fragement;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.khaki.jxc.activity.FriendActivity;
import com.khaki.jxc.activity.GroupActivity;
import com.khaki.jxc.activity.PersonalContactActivity;
import com.khaki.jxc.client.R;


/**
 * 底部导航栏中联系人的Fragment
 */

public class ContactFragment extends Fragment {

    private View view;
    private Context mContext;
    private TextView titleTV;
    private SuperTextView friendST;
    private SuperTextView groupST;
    private SuperTextView personalContactST;

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACT = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e("contactFragment", "create");
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        mContext = getActivity();
        initView();
        return view;
    }

    private void initView(){



        titleTV = (TextView) view .findViewById(R.id.toolbar_title);
        titleTV.setText("联系人");

        friendST = (SuperTextView) view.findViewById(R.id.st_contact_friend);
        friendST.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
            @Override
            public void onClickListener(SuperTextView superTextView) {

            }


            public void onSuperTextViewClick() {
                openFriendActivity();
            }
        });

        groupST = (SuperTextView) view.findViewById(R.id.st_contact_group);
        groupST.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
            @Override
            public void onClickListener(SuperTextView superTextView) {

            }


            public void onSuperTextViewClick() {
                openGroupActivity();
            }
        });

        personalContactST = (SuperTextView) view.findViewById(R.id.st_contact_personal);
        personalContactST.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
            @Override
            public void onClickListener(SuperTextView superTextView) {

            }


            public void onSuperTextViewClick() {
                if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity() ,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACT);
                }else {
                    openPersonalActivity();
                }

            }
        });

    }


    public void openFriendActivity(){
        Intent intent = new Intent(mContext, FriendActivity.class);
        startActivity(intent);
    }


    public void openGroupActivity(){
        Intent intent = new Intent(mContext, GroupActivity.class);
        startActivity(intent);
    }


    public void openPersonalActivity(){
        Intent intent = new Intent(mContext, PersonalContactActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACT){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.e("permission", "accept");
                openPersonalActivity();
            } else
            {
                // Permission Denied
                Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
