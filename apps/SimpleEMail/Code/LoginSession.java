package com.example.root.simpleemail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;


/**
 * Created by root on 4/3/17.
 */

public class LoginSession
{
    private String eMail;
    private String password;
    private String host;
    private String port;

    private Properties properties;
    private MyAuthenticator authenticator;
    private Session session;

    public LoginSession(String inpEmail, String inpPassword, String inpHost, String inpPort)
    {
        eMail = inpEmail;
        password = inpPassword;
        host = inpHost;
        port = inpPort;

        login();
    }

    private void login()
    {
        fillProperties();
        startSession();
    }

    private void fillProperties()
    {
        properties = new Properties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", port);
    }

    private void startSession()
    {
        authenticator = new MyAuthenticator();
        session = Session.getDefaultInstance(properties, authenticator);
    }

    private class MyAuthenticator extends Authenticator
    {
        protected PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(eMail, password);
        }
    }

    public Session getSession()
    {
        return session;
    }
}
