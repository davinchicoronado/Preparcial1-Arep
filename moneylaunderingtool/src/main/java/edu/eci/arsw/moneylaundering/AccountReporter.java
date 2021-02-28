package edu.eci.arsw.moneylaundering;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.HttpPut;

public class AccountReporter {
        public static void report(String account, int amountOfSuspectTransactions) {
            //System.out.println(amountOfSuspectTransactions+" cuenta"+ account);
            java.lang.String payload = "{"
                    + "\"accountId\": \""+account+"\", "
                    + "\"amountOfSmallTransactions\": \""+amountOfSuspectTransactions+"\" "
                    + "}";

            StringEntity entity = new StringEntity(payload,
                    ContentType.APPLICATION_JSON);

            try {
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost request = new HttpPost("http://localhost:8080/fraud-bank-accounts");
                request.setEntity(entity);

                HttpResponse response;
                response = httpClient.execute(request);
                
                if(response.getStatusLine().getStatusCode()==400){
                    HttpPut requestTwo = new HttpPut("http://localhost:8080/fraud-bank-accounts/"+account);
                    response = httpClient.execute(requestTwo);
                    
                }
                
                
                System.out.println(response.getStatusLine().getStatusCode());

            } catch (IOException ex) {
                Logger.getLogger(AccountReporter.class.getName()).log(Level.SEVERE, "Unable to report fraudulent transactions for account", ex);
            }

        }
}
