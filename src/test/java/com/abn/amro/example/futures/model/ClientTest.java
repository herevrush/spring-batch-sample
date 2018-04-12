package com.abn.amro.example.futures.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class ClientTest {
    private Client client;

    @Before
    public void setup(){
        client = Mockito.mock(Client.class);
        when(client.getClientId()).thenReturn("CL-1234-00020001");
        Map<String,Integer> products = new HashMap<>();
        products.put("SGX(FU)-NK-20100910",-50);
        products.put("CME(FU)-N1-20100910",250);
        when(client.getProducts()).thenReturn(products);
    }

    @Test
    public void testClientData(){
        Assert.assertEquals("CL-1234-00020001",client.getClientId());
        Assert.assertEquals(true, client.getProducts().containsKey("SGX(FU)-NK-20100910"));
        Assert.assertEquals(-50, client.getProducts().get("SGX(FU)-NK-20100910").intValue());
        Assert.assertEquals(true, client.getProducts().containsKey("CME(FU)-N1-20100910"));
        Assert.assertEquals(250, client.getProducts().get("CME(FU)-N1-20100910").intValue());
    }
}
