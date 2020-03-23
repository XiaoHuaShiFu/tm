package com.xiaohuashifu.tm.util.email;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * 发送邮件工具类
 */
public class EmailUtils {

    public static boolean sendEmail(Email mb) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mb.getHost());
        properties.put("mail.smtp.auth", Boolean.TRUE.toString());

        Authenticator authenticator = new AuthenticatorImpl(mb.getUsername(), mb.getPassword());
        Session session = Session.getInstance(properties, authenticator);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(mb.getFrom()));
            InternetAddress[] address = new InternetAddress[mb.getTo().size()];
            for (int i = 0; i < mb.getTo().size(); i++) {
                address[i] = new InternetAddress(mb.getTo().get(i));
            }

            mimeMessage.setRecipients(Message.RecipientType.TO, address);
            mimeMessage.setSubject(mb.getSubject());

            Multipart multipart = new MimeMultipart();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(mb.getContent());
            multipart.addBodyPart(mimeBodyPart);

            if (mb.getFile() != null) {
                for (String s : mb.getFile()) {
                    mimeBodyPart = new MimeBodyPart();
                    FileDataSource fileDataSource = new FileDataSource(s);
                    mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
                    mimeBodyPart.setFileName(fileDataSource.getName());
                    multipart.addBodyPart(mimeBodyPart);
                }
            }
            mimeMessage.setContent(multipart);
            mimeMessage.setSentDate(new Date());
            Transport.send(mimeMessage);
        }  catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * AuthenticatorImpl
     */
    private static class AuthenticatorImpl extends Authenticator {
        private String username;
        private String password;
        AuthenticatorImpl(String username, String password) {
            super();
            this.username = username;
            this.password = password;
        }
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }

}