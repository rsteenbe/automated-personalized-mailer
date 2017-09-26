//////////////////////////////////////////////////////////////////////////
//                                                                      //
//      2017-09-26 - Created by Renco Steenbergen                       //
//                                                                      //
//////////////////////////////////////////////////////////////////////////

package com.rencomusic.apm.mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailSession {
    public static Properties getProperties() {
        final Properties properties = new Properties();
        InputStream input = null;
        try {
            input = input = MailSession.class.getClassLoader().getResourceAsStream("smtp.properties");
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public static Session getSession() {
        return Session.getInstance(getProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(getProperties().getProperty("mail.smtp.username"), getProperties().getProperty("mail.smtp.password"));
                    }
                });
    }
}
