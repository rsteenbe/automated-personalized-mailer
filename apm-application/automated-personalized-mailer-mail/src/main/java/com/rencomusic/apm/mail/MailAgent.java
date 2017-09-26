//////////////////////////////////////////////////////////////////////////
//                                                                      //
//      2017-09-26 - Created by Renco Steenbergen                       //
//                                                                      //
//////////////////////////////////////////////////////////////////////////

package com.rencomusic.apm.mail;

import com.rencomusic.apm.persistence.Mail;
import com.rencomusic.apm.persistence.Recipient;
import com.rencomusic.apm.persistence.RecipientService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Stateless
public class MailAgent {

    @Inject
    RecipientService recipientService;

    public void sendGroup(Mail mail) throws InterruptedException {
        String cat = mail.getCategory();
        List<Recipient> recipients = recipientService.findByCategory(cat);
        System.out.println("E-mails will be sent to the following recipients: ");
        for (int j = 0; j < recipients.size(); j++) {
            System.out.println(j+1 + " - " + recipients.get(j).getName());
        }

        for (int i = 0; i < recipients.size(); i++) {
                sendMail(mail, recipients.get(i));
        }
        System.out.println("Process finished. All mails are sent.");
    }

    private void sendMail(Mail mail, Recipient recipient) {
        try {
            Session session = MailSession.getSession();
            Message message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(MailSession.getProperties().getProperty("mail.smtp.username"), MailSession.getProperties().getProperty("mail.smtp.name")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient.getMail()));
            message.setSubject(mail.getSubject());
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = mail.getMessage()
                    .replace("<recipient>", recipient.getName());
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Message sent.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        Random rand = new Random();
        int  n = rand.nextInt(4000)*9 + 1;
        try {
            System.out.println("Timeout for " + (42000+n) + " milliseconds. Generated random number: " + n);
            TimeUnit.MILLISECONDS.sleep(42000 + n);
            System.out.println("Process continues.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
