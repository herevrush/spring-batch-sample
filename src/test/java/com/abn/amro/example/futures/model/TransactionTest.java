package com.abn.amro.example.futures.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class TransactionTest {

    private Transaction transaction;
    //CL  ,432100020001,SGXDC ,FUSGX NK    20100910
    @Before
    public void setUp() throws Exception {
        transaction = Mockito.mock(Transaction.class);
        when(transaction.getClientType()).thenReturn("CL  ");
        when(transaction.getClientNumber()).thenReturn("4321");
        when(transaction.getAccountNumber()).thenReturn("0002");
        when(transaction.getSubAccountNumber()).thenReturn("0001");
        when(transaction.getExchangeCode()).thenReturn("SGX ");
        when(transaction.getProductGroupCode()).thenReturn("FU");
        when(transaction.getSymbol()).thenReturn("NK    ");
        when(transaction.getExpirationDate()).thenReturn("20100910");
        when(transaction.getQuantityLong()).thenReturn(Integer.parseInt("0000000003"));
        when(transaction.getQuantityShort()).thenReturn(Integer.parseInt("0000000003"));
    }


    @Test
    public void testTransactionDetails(){
        Assert.assertEquals("CL  ",transaction.getClientType());
        Assert.assertEquals("4321",transaction.getClientNumber());
        Assert.assertEquals("0002",transaction.getAccountNumber());
        Assert.assertEquals("0001",transaction.getSubAccountNumber());
        Assert.assertEquals("SGX ",transaction.getExchangeCode());
        Assert.assertEquals("FU",transaction.getProductGroupCode());
        Assert.assertEquals("NK    ",transaction.getSymbol());
        Assert.assertEquals("20100910",transaction.getExpirationDate());
        Assert.assertEquals(3,transaction.getQuantityLong());
        Assert.assertEquals(3,transaction.getQuantityShort());

    }

    @Test
    public  void testClientId(){
        transaction = new Transaction();
        transaction.setClientType("CL  ");
        transaction.setClientNumber("4321");
        transaction.setAccountNumber("0002");
        transaction.setSubAccountNumber("0001");
        transaction.setExchangeCode("SGX");
        transaction.setProductGroupCode("FU");
        transaction.setSymbol("NK    ");
        transaction.setExpirationDate("20100910");
        Assert.assertEquals("CL-4321-00020001",transaction.getClientId());
    }

    @Test
    public  void testProductId(){
        transaction = new Transaction();
        transaction.setClientType("CL  ");
        transaction.setClientNumber("4321");
        transaction.setAccountNumber("0002");
        transaction.setSubAccountNumber("0001");
        transaction.setExchangeCode("SGX");
        transaction.setProductGroupCode("FU");
        transaction.setSymbol("NK    ");
        transaction.setExpirationDate("20100910");
        Assert.assertEquals("SGX(FU)-NK-20100910",transaction.getProductId());
    }

}
