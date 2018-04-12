package com.abn.amro.example.futures.model;

public class ReportItem {

    private String clientInfo;
    private String productInfo;
    private int transactionAmount;

    public ReportItem(String clientInfo, String productInfo, int transactionAmount){
        this.clientInfo = clientInfo;
        this.productInfo = productInfo;
        this.transactionAmount = transactionAmount;
    }
    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
