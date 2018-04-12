package com.abn.amro.example.futures.reader;

import com.abn.amro.example.futures.config.TransactionsConfig;
import com.abn.amro.example.futures.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ReaderNotOpenException;
import org.springframework.batch.item.file.FlatFileItemReader;

import java.util.List;

public class TransactionReaderTest {

    private static final String INPUT_FILE = "src/test/resources/input/input.txt";
    private TransactionReader transactionReader;
    private static final int EXPECTED_COUNT = 20;


    @Before
    public void setUp() throws Exception {
        transactionReader = new TransactionReader();
        TransactionsConfig config = new TransactionsConfig();
        FlatFileItemReader<Transaction> flatFileItemReader = config.fileItemReader(INPUT_FILE);
        transactionReader.setItemReader(flatFileItemReader);
    }

    @Test
    public void testRead() throws Exception {
        ExecutionContext executionContext = new ExecutionContext();
        transactionReader.open(executionContext);
        List<Transaction> transactionList = transactionReader.read();
        Assert.assertNotNull(transactionList);
        Assert.assertEquals(EXPECTED_COUNT, transactionList.size());

        Transaction transaction = transactionList.get(0);
        Assert.assertEquals("CL",transaction.getClientType());
        Assert.assertEquals("4321",transaction.getClientNumber());
        Assert.assertEquals("0002",transaction.getAccountNumber());
        Assert.assertEquals("0001",transaction.getSubAccountNumber());
        Assert.assertEquals("SGX",transaction.getExchangeCode());
        Assert.assertEquals("FU",transaction.getProductGroupCode());
        Assert.assertEquals("NK",transaction.getSymbol());
        Assert.assertEquals("20100910",transaction.getExpirationDateString());
        Assert.assertEquals(1,transaction.getQuantityLong());
        Assert.assertEquals(0,transaction.getQuantityShort());
        Assert.assertEquals("CL-4321-00020001", transaction.getClientId());
        Assert.assertEquals("SGX(FU)-NK-20100910",transaction.getProductId());
        transactionReader.close();

    }



    @Test (expected = ReaderNotOpenException.class)
    public void testReadWithException() throws Exception {
        List<Transaction> transactionList = transactionReader.read();
    }
}
