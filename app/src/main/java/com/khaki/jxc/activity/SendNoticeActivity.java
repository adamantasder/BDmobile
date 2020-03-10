package com.khaki.jxc.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.khaki.app.base.BaseActivity;
import com.khaki.jxc.Contants;
import com.khaki.jxc.bean.untils.OkHttpUntil;
import com.khaki.jxc.bean.untils.UserUntil;
import com.khaki.jxc.client.R;
import com.khaki.jxc.event.RefreshNoticeEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import static com.khaki.jxc.utils.OkHttpUtil.okHttpClient;

public class SendNoticeActivity extends BaseActivity {

    private EditText titleET;
    private EditText authorET;
    private EditText contentET;
    private TextView titleTV;
    private Toolbar toolbar;
    private int groupId;


    @Override
    public void initView() {
        setContentView(R.layout.activity_send_notice);
        Intent intent = getIntent();
        groupId = intent.getIntExtra("groupId", -1);
        titleTV = (TextView) findViewById(R.id.toolbar_title);
        titleET = (EditText) findViewById(R.id.et_notice_title);
        authorET = (EditText) findViewById(R.id.et_notice_author);
        contentET = (EditText) findViewById(R.id.et_notice_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        titleTV.setText("发公告");
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        titleET.setSingleLine();
        authorET.setSingleLine();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }


    public void send(String title, String author, String content) {
        FormBody formBody = new FormBody.Builder()
                .add("aTitle", title)
                .add("aContent", content)
                .add("aType", "notice")
                .add("aCreatedUserPhone", UserUntil.phone)
                .add("groupId", groupId+"")
                .build();


        //step 3: 创建请求
        Request request = new Request.Builder().url(Contants.SERVER_IP + "/permission/publicAnnouncement.shtml")
                .addHeader("cookie", OkHttpUntil.loginSessionID)
                .post(formBody)
                .build();

        //step 4： 建立联系 创建Call对象
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // TODO: 17-1-4  请求失败
                Log.e("sendNotice", "fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // TODO: 17-1-4 请求成功
                Log.e("sendNotice", response.body().string());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_send_notice_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
            case R.id.menu_notice_send:
                String title = titleET.getText().toString();
                String author = authorET.getText().toString();
                String content = contentET.getText().toString();

                Log.e("notice", "send");
                if (title.equals("") || author.equals("") || content.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "标题、作者、内容都不能为空", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "合格", Toast.LENGTH_SHORT);
                    toast.show();
                    send(title, author, content);
                    Intent intent = new Intent(SendNoticeActivity.this, MainActivity.class);
                    startActivity(intent);
                    EventBus.getDefault().post(new RefreshNoticeEvent());

                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
    }
}
