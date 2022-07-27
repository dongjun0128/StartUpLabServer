package com.startuplab.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;
import com.startuplab.common.vo.FcmData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FcmService {

  @Autowired
  private CommonService common;

  private FirebaseMessaging fm;
  private String firebaseJson = "startuplab-devteam-firebase-adminsdk-jtsql-257eea8667.json";

  @PostConstruct
  public void serviceInit() {
    try {
      InputStream serviceAccount = (new ClassPathResource(firebaseJson)).getInputStream();
      FirebaseOptions options = FirebaseOptions.builder() //
          .setCredentials(GoogleCredentials.fromStream(serviceAccount)) //
          // .setDatabaseUrl(databaseUrl) //
          .build();
      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
      }
      fm = FirebaseMessaging.getInstance();
      // sendMsg("1f24re754ihYpO0uuL5HmFW:APA91bGwj_qeTfUpIPLU1AQWCRK42xdSDqFRpYqGJg8sS5-b4i4fqL9zZsXQ3RTzlwr7xP2plb_6ynNPQMFgvw_3GGXVg16ubFi4rmcrRH-7FwfUvZsVepvMp6x8frDMfNE-erOL-f4h");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public String sendTestMsg(String registrationToken) {
    String response = "";
    try {
      FcmData f = new FcmData();
      f.setTitle("Firebase cloud messaging");
      f.setBody("Rome was not built in a day!");
      Message message = Message.builder() //
          .putAllData(f.toMap()) //
          .setToken(registrationToken) //
          .build();
      try {
        response = fm.send(message);
      } catch (Exception e) {
        log.error("fcm send error: {}", e.getMessage());
        response = e.getMessage();
        // common.deleteErrorToken(registrationToken);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public String sendMessage(String registrationToken, FcmData fcmData) {
    String response = "";
    try {
      Message message = Message.builder() //
          .putAllData(fcmData.toMap()) //
          .setToken(registrationToken) //
          .build();
      try {
        response = fm.send(message);
      } catch (Exception e) {
        log.error("fcm send error: {}", e.getMessage());
        response = e.getMessage();
        // common.deleteErrorToken(registrationToken);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  public void sendMulticastMessage(List<String> registrationTokens, FcmData fcmData) {
    try {
      MulticastMessage message = MulticastMessage.builder() //
          .putAllData(fcmData.toMap()) //
          .addAllTokens(registrationTokens) //
          .build();
      BatchResponse response = fm.sendMulticast(message);
      if (response.getFailureCount() > 0) {
        List<SendResponse> responses = response.getResponses();
        List<String> failedTokens = new ArrayList<>();
        for (int i = 0; i < responses.size(); i++) {
          if (!responses.get(i).isSuccessful()) {
            failedTokens.add(registrationTokens.get(i));
            // common.deleteErrorToken(registrationTokens.get(i));
          }
        }
        System.out.println("List of tokens that caused failures: " + failedTokens);
      }
    } catch (Exception e) {
      log.error("fcm send error: {}", e.getMessage());
    }
  }

}
