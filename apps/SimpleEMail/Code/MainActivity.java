package com.example.root.simpleemail;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity
{
    private EditText editTextTo;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button buttonSend;

    private String toEmail;
    private String subject;
    public String messageToSend;

    private String myEmail;
    private Session session;

    private ProgressDialog progressDialog;

    private MyOnclickListener onclickListener;
    private BackGroundThread backGroundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeClassVariables();
        buttonSend.setOnClickListener(onclickListener);
    }

    private void initializeViews()
    {
        editTextTo = (EditText) findViewById(R.id.editTextTo);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        buttonSend = (Button) findViewById(R.id.buttonSend);
    }

    private void initializeClassVariables()
    {
        onclickListener=  new MyOnclickListener();
        backGroundThread = new BackGroundThread();
    }

    private class MyOnclickListener implements View.OnClickListener
    {
        private LoginSession loginSession;

        private String myPassword;
        private String myEmailHost;
        private String myEmailPort;

        @Override
        public void onClick(View v)
        {
            storeUserInput();
            fillMyEmailDetails();
            loginSession = new LoginSession(myEmail, myPassword,myEmailHost, myEmailPort );
            session = loginSession.getSession();
            progressDialog = ProgressDialog.show(MainActivity.this, "", "Sending Mail...", true);
            backGroundThread.execute();
        }

        private void storeUserInput()
        {
            toEmail = editTextTo.getText().toString();
            subject = editTextSubject.getText().toString();
            messageToSend = editTextMessage.getText().toString();
        }

        private void fillMyEmailDetails()
        {
            myEmail = "saketh9977.test@gmail.com";
            myPassword = "test_password";
            myEmailHost = "smtp.gmail.com";
            myEmailPort = "465";
        }
    }

    private class BackGroundThread extends AsyncTask<String, Void, String>
    {
        private Message mimeMessage;

        @Override
        protected String doInBackground(String... parameters)
        {
            try
            {
                mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom( new InternetAddress(myEmail) );
                mimeMessage.setRecipients( Message.RecipientType.TO, InternetAddress.parse(toEmail) );
                mimeMessage.setSubject(subject);
                mimeMessage.setContent(messageToSend, "text/html; charset=utf-8");
                Transport.send(mimeMessage);
            }
            catch(MessagingException e)
            {
                e.printStackTrace();
            }

            catch(Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String parameter)
        {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Message sent!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
