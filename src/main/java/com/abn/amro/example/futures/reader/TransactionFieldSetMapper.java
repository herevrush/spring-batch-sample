package com.abn.amro.example.futures.reader;

import com.abn.amro.example.futures.model.Transaction;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class TransactionFieldSetMapper implements FieldSetMapper<Transaction>{
    @Override
    public Transaction mapFieldSet(FieldSet fieldSet) throws BindException {
        Transaction transaction = new Transaction();
        transaction.setClientType(fieldSet.readRawString("clientType").trim());
        transaction.setClientNumber(fieldSet.readRawString("clientNumber"));
        transaction.setAccountNumber(fieldSet.readRawString("accountNumber"));
        transaction.setSubAccountNumber(fieldSet.readRawString("subAccountNumber"));
        transaction.setExchangeCode(fieldSet.readRawString("exchangeCode").trim());
        transaction.setProductGroupCode(fieldSet.readRawString("productGroupCode").trim());
        transaction.setSymbol(fieldSet.readRawString("symbol").trim());
        transaction.setExpirationDate(fieldSet.readDate("expirationDate", "yyyyMMdd"));
        transaction.setQuantityLong(fieldSet.readInt("quantityLong"));
        transaction.setQuantityShort(fieldSet.readInt("quantityShort"));
        return transaction;
    }
}
