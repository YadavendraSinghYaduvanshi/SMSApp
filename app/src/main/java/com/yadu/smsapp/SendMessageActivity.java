package com.yadu.smsapp;

import android.app.Dialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yadu.smsapp.constants.CommonStrings;
import com.yadu.smsapp.database.MessageDatabse;
import com.yadu.smsapp.gettersetter.Contact;
import com.yadu.smsapp.gettersetter.MessageGetterSetter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendMessageActivity extends AppCompatActivity {

    TextView tv_name, tv_mob_no, tv_msg;
    Button btn_send;
    int max = 999999;
    int min = 100000;

    String ACCOUNT_SID = "AC42b2410eade3df637935ebfeed6e72a7";
    String AUTH_TOKEN = "fa27507bec0feaa78e9b2b87a3ac3b69";

    Contact contact;

    int randomNum;

    MessageDatabse db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new MessageDatabse(this);
        db.open();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.setThreadPolicy( new StrictMode.ThreadPolicy.Builder().permitAll().build() );
        }

        contact = (Contact) getIntent().getParcelableExtra(CommonStrings.KEY_CONTACTS);

        tv_name = findViewById(R.id.tv_name);
        tv_mob_no = findViewById(R.id.tv_mob);
        tv_msg = findViewById(R.id.tv_msg);
        btn_send = findViewById(R.id.btn_msg);

        tv_name.setText("Name - "+ contact.getName());
        tv_mob_no.setText("Mobile No. - "+ contact.getMobile());

        Random rand=new Random();

        randomNum = rand.nextInt((max - min) + 1) + min;

        String msg = "Message - " + CommonStrings.OTP_MSG + randomNum;

        tv_msg.setText(msg);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageGetterSetter msg  = new MessageGetterSetter();
                msg.setName(contact.getName());
                msg.setOtp(randomNum+"");
                msg.setTime(getCurrentDateTime());
                msg.setMob_no(contact.getMobile());

                db.open();
                db.InsertMessageData(msg);

                sendSms(contact.getMobile(),CommonStrings.OTP_MSG + randomNum);
            }
        });

    }

    private void sendSms(String toPhoneNumber, String message){
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.twilio.com/2010-04-01/Accounts/"+ACCOUNT_SID+"/SMS/Messages";
        String base64EncodedCredentials = "Basic " + Base64.encodeToString((ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP);

        RequestBody body = new FormBody.Builder()
                .add("From", "+16174053945")
                .add("To", toPhoneNumber)
                .add("Body", message)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", base64EncodedCredentials)
                .build();
        try {
            Response response = client.newCall(request).execute();
            //Log.d("MYSMS", "sendSms: "+ response.body().string());

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(response.body().string()));
            xpp.next();
            int eventType = xpp.getEventType();
            ResponseGetterSetter resp = responseXMLHandler(xpp, eventType);

            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_layout);
            TextView tv_response = (TextView) dialog.findViewById(R.id.tv_response);
            Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
            if(resp.getCode()==null){
                tv_response.setText("Message Sent Successfully");
            }
            else {
                tv_response.setText("Code - "+resp.getCode()+"\n"+ "Message - "+resp.getMsg());
            }

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            dialog.show();

            //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG);

        } catch (IOException e) { e.printStackTrace(); } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }


    public String getCurrentDateTime(){

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        //return DateFormat.getDateTimeInstance().format(new Date());
        return formattedDate;
    }

    // BRAND_MASTER XML HANDLER
    public static ResponseGetterSetter responseXMLHandler(XmlPullParser xpp,
                                                                int eventType) {

        ResponseGetterSetter response_getterSetter = new ResponseGetterSetter();

        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("Code")) {
                        response_getterSetter.setCode(xpp.nextText());
                    }
                    if (xpp.getName().equals("Message")) {
                        response_getterSetter.setMsg(xpp.nextText());
                    }

                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return response_getterSetter;
    }

    static class ResponseGetterSetter{

        String code, msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
