package com.somnus.blockchain.contract;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

@Component
public class UserContract extends Contract{
	
    /**
     *
     * @param registerAddress 合约地址
     * @param rpcAddr　rpc地址
     * @param gasPrice　gas价格
     * @param gasLimit　gas限制
     * @throws CipherException 
     * @throws IOException 
     */
    /*自带构造方法*/
    public UserContract(
    		@Value("${userAddress}") String userAddress, 
    		@Value("${rpcAddr}") String rpcAddr,
    		@Value("${walletPath}") String walletPath,
    		@Value("${password}") String password,
    		@Value("${gasPrice}") BigInteger gasPrice, 
    		@Value("${gasLimit}") BigInteger gasLimit) throws IOException, CipherException {
        super("", userAddress, 
        		Web3j.build(new HttpService(rpcAddr)), 
        		WalletUtils.loadCredentials(password,walletPath), 
        		gasPrice, 
        		gasLimit);
    }

    /**
     * 登录
     */
    public RemoteCall<Bool> login(String username,String password) throws IOException {
        Function function = new Function("login", 
        		Arrays.asList(new Utf8String(username),new Utf8String(password)),
        		Arrays.<TypeReference<?>>asList(new TypeReference<Bool>(){})
        );
        return executeRemoteCallSingleValueReturn(function);
    }

    /**
     * 注册检测是否有相同凭证的用户存在
     */
    public RemoteCall<Bool> checkRegister(String adress,String username) throws IOException {
        Function function = new Function("checkRegister",
        		Arrays.asList(new Address(adress),new Utf8String(username)),
        		Arrays.<TypeReference<?>>asList(new TypeReference<Bool>(){})
        );
        return executeRemoteCallSingleValueReturn(function);
    }

    /**
     * 注册
     */
    public RemoteCall<TransactionReceipt> register(String adress,String username,String password) throws IOException {
        Function function = new Function("register",
        		Arrays.asList(new Address(adress),new Utf8String(username),new Utf8String(password)),
        		Arrays.<TypeReference<?>>asList()
        );
        return executeRemoteCallTransaction(function);
    }

    /**
     *更新密码
     */
    public RemoteCall<TransactionReceipt> updatePassword(String username,String password) throws IOException {
        Function function = new Function("updatePassword",
        		Arrays.asList(new Utf8String(username),new Utf8String(password)),
        		Arrays.<TypeReference<?>>asList()
        );
        return executeRemoteCallTransaction(function);
    }
}
