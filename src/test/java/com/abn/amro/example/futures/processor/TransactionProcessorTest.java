package com.abn.amro.example.futures.processor;

import com.abn.amro.example.futures.model.ReportItem;
import com.abn.amro.example.futures.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TransactionProcessorTest {

    private TransactionProcessor transactionProcessor;

    private List<Transaction> transactions = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        transactionProcessor = new TransactionProcessor();

        Transaction transaction = new Transaction();
        transaction.setClientType("CL  ");
        transaction.setClientNumber("4321");
        transaction.setAccountNumber("0002");
        transaction.setSubAccountNumber("0001");
        transaction.setExchangeCode("SGX");
        transaction.setProductGroupCode("FU");
        transaction.setSymbol("NK    ");
        transaction.setExpirationDate("20100910");
        transaction.setQuantityLong(3);
        transaction.setQuantityShort(0);
        transactions.add(transaction);

        transaction = new Transaction();
        transaction.setClientType("CL  ");
        transaction.setClientNumber("4321");
        transaction.setAccountNumber("0002");
        transaction.setSubAccountNumber("0001");
        transaction.setExchangeCode("SGX");
        transaction.setProductGroupCode("FU");
        transaction.setSymbol("NK    ");
        transaction.setExpirationDate("20100910");
        transaction.setQuantityLong(3);
        transaction.setQuantityShort(0);
        transactions.add(transaction);

        transaction = new Transaction();
        transaction.setClientType("CL  ");
        transaction.setClientNumber("4321");
        transaction.setAccountNumber("0002");
        transaction.setSubAccountNumber("0001");
        transaction.setExchangeCode("SGX");
        transaction.setProductGroupCode("FU");
        transaction.setSymbol("NK    ");
        transaction.setExpirationDate("20100910");
        transaction.setQuantityLong(0);
        transaction.setQuantityShort(2);
        transactions.add(transaction);

        transaction = new Transaction();
        transaction.setClientType("CL  ");
        transaction.setClientNumber("4321");
        transaction.setAccountNumber("0002");
        transaction.setSubAccountNumber("0001");
        transaction.setExchangeCode("CME");
        transaction.setProductGroupCode("FU");
        transaction.setSymbol("N1    ");
        transaction.setExpirationDate("20100910");
        transaction.setQuantityLong(0);
        transaction.setQuantityShort(2);
        transactions.add(transaction);

    }

    @Test
    public void testNullInput(){
       List<ReportItem> items =  transactionProcessor.process(null);
        Assert.assertNull(items);
    }

    @Test
    public void testProcess(){

        List<ReportItem> items = transactionProcessor.process(transactions);
        Assert.assertNotNull(items);
        ReportItem item = items.get(0);

        Assert.assertEquals("CL-4321-00020001", item.getClientInfo());
        Assert.assertEquals("CME(FU)-N1-20100910", item.getProductInfo());
        Assert.assertEquals(-2, item.getTransactionAmount());
        item = items.get(1);
        Assert.assertEquals("CL-4321-00020001", item.getClientInfo());
        Assert.assertEquals("SGX(FU)-NK-20100910", item.getProductInfo());
        Assert.assertEquals(4, item.getTransactionAmount());

    }
}
