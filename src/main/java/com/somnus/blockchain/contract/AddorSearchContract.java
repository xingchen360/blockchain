package com.somnus.blockchain.contract;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
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
public class AddorSearchContract extends Contract{
	
    /**
     * @param addorAdress 合约地址
     * @param rpcAddr　rpc地址
     * @param walletPath　钱包地址
     * @param gasPrice　gas价格
     * @param gasLimit　gas限度
     */
    public AddorSearchContract(
    		@Value(value = "${addorAdress}") String addorAdress, 
    		@Value("${rpcAddr}") String rpcAddr,
    		@Value("${walletPath}") String walletPath,
    		@Value("${password}") String password,
    		@Value("${gasPrice}") BigInteger gasPrice, 
    		@Value("${gasLimit}") BigInteger gasLimit) throws IOException, CipherException {
    	
        super("", addorAdress, 
        		Web3j.build(new HttpService(rpcAddr)), 
        		WalletUtils.loadCredentials(password,walletPath), 
        		gasPrice,
        		gasLimit);
    }

    public RemoteCall<TransactionReceipt> addMsg(String username, String ID, String violateRecord, BigInteger lowPoint, String AdministratorName) throws IOException {
        Function function = new Function("addMsg",
        		Arrays.asList(new Utf8String(username),
        							new Utf8String(ID),
        							new Utf8String(violateRecord),
        							new Uint(lowPoint),
        							new Utf8String(AdministratorName)),
        		Arrays.<TypeReference<?>>asList());
        return executeRemoteCallTransaction(function);
    }

    /**
     * 获取区块链中违章记录数量
     */
    public RemoteCall<Uint>  returnTotal() throws IOException {
        Function function = new Function("returnTotal",
        		Arrays.asList(),
        		Arrays.<TypeReference<?>>asList(new TypeReference<Uint>(){})
        );
        return executeRemoteCallSingleValueReturn(function);
    }

    /**
     * 获取违章者姓名
     */
    public RemoteCall<Utf8String>  getuserName(BigInteger id) throws IOException {
        Function function = new Function("getuserName",
        		Arrays.asList(new Uint(id)),
        		Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(){})
        );
        return executeRemoteCallSingleValueReturn(function);
    }
    
    /**
     * 获取违章者身份证
     */
    public RemoteCall<Utf8String>  getID(BigInteger id) throws IOException {
        Function function = new Function("getID",
        		Arrays.asList(new Uint(id)),
        		Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(){})
        );
        return executeRemoteCallSingleValueReturn(function);
    }
    /**
     * 获取违章者违章记录
     */
    public RemoteCall<Utf8String>  getviolateRecord(BigInteger id) throws IOException {
        Function function = new Function("getviolateRecord",
        		Arrays.asList(new Uint(id)),
        		Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(){})
        );
        return executeRemoteCallSingleValueReturn(function);
    }
    /**
     * 获取违章者违章扣分情况
     */
    public RemoteCall<Uint>  getlowPoint(BigInteger id) throws IOException {
        Function function = new Function("getlowPoint",
        		Arrays.asList(new Uint(id)),
        		Arrays.<TypeReference<?>>asList(new TypeReference<Uint>(){})
        );
        return executeRemoteCallSingleValueReturn(function);
    }
    /**
     * 获取写入此条违章记录的执法人员
     */
    public RemoteCall<Utf8String>  getAdministrator(BigInteger id) throws IOException {
        Function function = new Function("getAdministrator",
        		Arrays.asList(new Uint(id)),
        		Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(){})
        );
        return executeRemoteCallSingleValueReturn(function);
    }
}
