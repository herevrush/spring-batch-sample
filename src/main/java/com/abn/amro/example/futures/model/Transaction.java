package com.abn.amro.example.futures.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Transaction object to parse the transaction record.
 */
public class Transaction {
    private String clientType;

    private String clientNumber;

    private  String accountNumber;

    private String subAccountNumber;

    private String exchangeCode;

    private String productGroupCode;

    private String symbol;

    private Date expirationDate;

    private int quantityLong;

    private int quantityShort;

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSubAccountNumber() {
        return subAccountNumber;
    }

    public void setSubAccountNumber(String subAccountNumber) {
        this.subAccountNumber = subAccountNumber;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getProductGroupCode() {
        return productGroupCode;
    }

    public void setProductGroupCode(String productGroupCode) {
        this.productGroupCode = productGroupCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
//
//    public String getExpirationDate() {
//        return expirationDate;
//    }
//
//    public void setExpirationDate(String expirationDate) {
//        this.expirationDate = expirationDate;
//    }


    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getQuantityLong() {
        return quantityLong;
    }

    public int getQuantityShort() {
        return quantityShort;
    }

    public void setQuantityLong(int quantityLong) {
        this.quantityLong = quantityLong;
    }

    public void setQuantityShort(int quantityShort) {
        this.quantityShort = quantityShort;
    }


    public String getProductId(){
        return getExchangeCode().trim() + "("+ getProductGroupCode().trim()
                + ")-"+getSymbol().trim()+ "-"+getExpirationDateString();
    }

    public String getClientId(){
        return getClientType().trim()+ "-"+ getClientNumber()
                + "-"+getAccountNumber()+getSubAccountNumber();
    }

    public String getExpirationDateString(){
        return  new SimpleDateFormat("yyyyMMdd").format(expirationDate);
    }
}

