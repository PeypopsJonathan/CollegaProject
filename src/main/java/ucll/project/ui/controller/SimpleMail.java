package ucll.project.ui.controller;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import java.util.Properties;


public class SimpleMail {

    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_AUTH_USER = "project2team9zonderkuba@gmail.com";
    private static final String SMTP_AUTH_PWD  = "MikeOxlong69";


    public static void send(String receiver, String receiverName) throws Exception{
        //mail send
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
        //mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("Received star on Stargazing");
        message.setContent(setupMessage(receiverName),"text/plain");
        message.setFrom(new InternetAddress(SMTP_AUTH_USER));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(receiver));

        transport.connect();
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }

    public static void sendManager(String receiver, String receiverName, String senderName, String managerName) throws Exception{
        //mail send
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
        //mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("Received star on Stargazing");
        message.setContent(setupMessageManager(senderName, receiverName, managerName),"text/plain");
        message.setFrom(new InternetAddress(SMTP_AUTH_USER));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(receiver));

        transport.connect();
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }


    private static class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }

    private static String setupMessage(String receiverName){
        String header = "Dear " + receiverName + "\n\n";
        String body = "You have been sent a star, go check it out at: ";
        String link = "https://project2team9-zonder-kuba.projectweek.be/\n\n";
        String footer = "Kind regards from the Stargazing team";
        return header + body + link + footer;
    }

    private static String setupMessageManager(String senderName, String receiverName, String managerName){
        String header = "Dear " + managerName + "\n\n";
        String body = "A star has been sent to " + receiverName + " by " + senderName + ".\n";
        body += "You can check it at: ";
        String link = "https://project2team9-zonder-kuba.projectweek.be/\n\n";
        String footer = "Kind regards from the Stargazing team";
        return header + body + link + footer;
    }
}