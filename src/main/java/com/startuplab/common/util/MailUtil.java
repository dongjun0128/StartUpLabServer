package com.startuplab.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailUtil {
  // gmail
  // private String username = ""; // gmail 사용자;
  // private String password = ""; // 패스워드 not login pass;
  // private String smtp_host = "smtp.gmail.com";
  // private String from_mail = this.username

  // naver
  private String username = "";
  private String password = ""; // 패스워드 not login pass;
  private String smtp_host = "smtp.naver.com";
  private String from_mail = this.username + "@naver.com";

  private String from_name = "스타트업랩";

  public void sendMail(String email, String subject, String txt) {
    List<String> emails = new ArrayList<>();
    emails.add(email);
    sendMail(emails, subject, txt);
  }

  public void sendMail(List<String> emails, String subject, String txt) {
    log.info("SENDMAIL Start");
    Properties p = System.getProperties();
    p.put("mail.smtp.host", smtp_host); // smtp 서버 주소
    p.put("mail.smtp.port", 587);
    p.put("mail.smtp.auth", "true");
    p.put("mail.smtp.ssl.trust", smtp_host);
    p.put("mail.smtp.ssl.auth", "true");
    p.put("mail.smtp.starttls.enable", "true");
    p.put("mail.smtp.ssl.protocols", "TLSv1.2");
    // p.put("mail.debug", "true");
    Authenticator auth = new SMTPAuthenticator(this.username, this.password);
    // session 생성 및 MimeMessage생성
    Session session = Session.getInstance(p, auth);
    MimeMessage msg = new MimeMessage(session);
    try {
      msg.setSentDate(new Date());
      // email sender
      InternetAddress from = new InternetAddress(this.from_mail, this.from_name, "UTF-8");
      msg.setFrom(from);
      // Recipient
      InternetAddress[] addArray = new InternetAddress[emails.size()];
      int i = 0;
      for (String email : emails) {
        addArray[i] = new InternetAddress(email);
        i++;
      }
      if (emails.size() == 1) {
        msg.setRecipient(Message.RecipientType.TO, addArray[0]);
      } else {
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(this.from_mail));
        msg.setRecipients(Message.RecipientType.BCC, addArray);
      }
      // Header
      msg.setHeader("content-Type", "text/html;charset=UTF-8");
      // Title
      msg.setSubject(subject, "UTF-8");
      // Contents
      // msg.setText(txt, "UTF-8");
      msg.setContent(txt, "text/html;charset=UTF-8");
      // Send
      javax.mail.Transport.send(msg);
    } catch (AddressException addr_e) {
      addr_e.printStackTrace();
    } catch (MessagingException msg_e) {
      msg_e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    log.info("SENDMAIL END");
  }
}

class SMTPAuthenticator extends Authenticator {
  private String username;
  private String password;

  public SMTPAuthenticator(String username, String password) {
    this.username = username;
    this.password = password;
  }

  protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(this.username, this.password);
  }
}
