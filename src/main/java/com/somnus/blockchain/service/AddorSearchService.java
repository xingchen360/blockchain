package com.somnus.blockchain.service;

import com.somnus.blockchain.contract.AddorSearchContract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

@Service
public class AddorSearchService {

	@Autowired
    private AddorSearchContract contract;

    public TransactionReceipt addMsg(String username, String ID, String violateRecord, BigInteger lowPoint, String AdministratorName) throws Exception{
        return contract.addMsg(username, ID, violateRecord, lowPoint, AdministratorName).send();
    }

    public BigInteger returnTotal() throws Exception {
        return contract.returnTotal().send().getValue();
    }

    public String getuserName(BigInteger id) throws Exception {
        return contract.getuserName(id).send().getValue();
    }

    public String getID(BigInteger id) throws Exception{
        return contract.getID(id).send().getValue();
    }

    public String getviolateRecord(BigInteger id) throws Exception{
        return contract.getviolateRecord(id).send().getValue();
    }

    public String getlowPoint(BigInteger id) throws Exception{
        return contract.getlowPoint(id).send().getValue().toString();
    }

    public String getAdministrator(BigInteger id) throws Exception{
        return contract.getAdministrator(id).send().getValue();
    }
}
