package com.abn.amro.example.futures.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class ReportItemTest {

    private ReportItem reportItem;

    @Before
    public void setUp() throws Exception {
        reportItem = Mockito.mock(ReportItem.class);
        when(reportItem.getClientInfo()).thenReturn("CL-1234-00020001");
        when(reportItem.getProductInfo()).thenReturn("SGX(FU)-NK-20100910");
        when(reportItem.getTransactionAmount()).thenReturn(-52);
    }


    @Test
    public void testReportItem1(){
        Assert.assertEquals("CL-1234-00020001",reportItem.getClientInfo());
        Assert.assertEquals("SGX(FU)-NK-20100910",reportItem.getProductInfo());
        Assert.assertEquals(-52,reportItem.getTransactionAmount());
    }


    @Test
    public void testReportItem2(){
        ReportItem reportItem2 = new ReportItem("CL-1234-00020001","SGX(FU)-NK-20100910",52);
        Assert.assertEquals("CL-1234-00020001",reportItem2.getClientInfo());
        Assert.assertEquals("SGX(FU)-NK-20100910",reportItem2.getProductInfo());
        Assert.assertEquals(52,reportItem2.getTransactionAmount());
    }


}
