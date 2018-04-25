package com.somnus.blockchain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somnus.blockchain.contract.UserContract;

@Service
public class UserService {
	
	@Autowired
    private UserContract contract;

    public boolean login(String username, String password) throws Exception {
        return contract.login(username,password).send().getValue();
    }

    public boolean checkRegister(String adress,String username) throws Exception {
        return contract.checkRegister(adress,username).send().getValue();
    }

    public boolean Register(String adress, String username, String password) throws Exception {
        if(!checkRegister(adress,username)){
            contract.register(adress,username,password).send();
            return true;
        }
        return false;
    }

    public void UpdatePassword(String username, String newPass) throws Exception {
        contract.updatePassword(username,newPass).send();
    }

}
