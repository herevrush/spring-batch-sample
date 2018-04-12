package com.abn.amro.example.futures.processor;

import com.abn.amro.example.futures.model.Client;
import com.abn.amro.example.futures.model.ReportItem;
import com.abn.amro.example.futures.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.text.SimpleDateFormat;
import java.util.*;


public class TransactionProcessor implements ItemProcessor<List<Transaction>,List<ReportItem>> {

    private static final Logger log = LoggerFactory.getLogger(TransactionProcessor.class);



    @Override
    public List<ReportItem> process(List<Transaction> transactions) {

        if(transactions != null){
//            Date date = new Date();
//            String dateString = new SimpleDateFormat("yyyyMMdd").format(date);
            Map<String,Client> clientsMap=new HashMap<>();
            transactions.forEach(transaction -> {

//                if(transaction.getExpirationDateString().equals(dateString)){
                    String clientId = transaction.getClientId();
                    String productId = transaction.getProductId();
                    int quantityLong = transaction.getQuantityLong();
                    int quantityShort = transaction.getQuantityShort();
                    int quantity = quantityLong - quantityShort;
                    Client client;
                    if(clientsMap.containsKey(clientId)){
                        client = clientsMap.get(clientId);
                    }
                    else{
                        client = new Client(clientId);
                        clientsMap.put(clientId,client);
                    }
                    client.addProductWithQuantity(productId,quantity);
//                }

            });

            //create summary output
            List<ReportItem> reportSummary = new ArrayList<>();
            clientsMap.forEach((clientId, client) -> client.getProducts().forEach((productId, integer) -> {
                ReportItem item = new ReportItem(clientId,productId,integer);
                reportSummary.add(item);
            }));
            return reportSummary;
        }

        return null;
    }


}
