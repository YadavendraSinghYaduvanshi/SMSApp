package com.yadu.smsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yadu.smsapp.constants.CommonStrings;
import com.yadu.smsapp.gettersetter.Contact;

public class ContactInfoActivity extends AppCompatActivity {

    TextView tv_name, tv_mob_no, tv_email;
    Button btn_send_msg;

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantact_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_name = findViewById(R.id.tv_name);
        tv_mob_no = findViewById(R.id.tv_mob);
        tv_email = findViewById(R.id.tv_email);
        btn_send_msg = findViewById(R.id.btn_send_msg);

        contact = (Contact) getIntent().getParcelableExtra(CommonStrings.KEY_CONTACTS);

        tv_name.setText("Name - "+ contact.getName());
        tv_mob_no.setText("Mobile No. - "+ contact.getMobile());
        tv_email.setText("Email - "+ contact.getEmail());

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), SendMessageActivity.class);

                in.putExtra(CommonStrings.KEY_CONTACTS, contact);

                startActivity(in);

                finish();
            }
        });

    }

}
