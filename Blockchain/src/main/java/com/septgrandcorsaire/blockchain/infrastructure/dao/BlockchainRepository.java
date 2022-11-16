package com.septgrandcorsaire.blockchain.infrastructure.dao;

import com.septgrandcorsaire.blockchain.domain.BlockChain;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BlockchainRepository {

    public static BlockchainRepository INSTANCE = new BlockchainRepository();


    private Map<String, BlockChain> nameBlockchainMap = new HashMap<>();
    
    private BlockchainRepository() {
        this.nameBlockchainMap = nameBlockchainMap;
    }


    public void addBlockchain(String electionName, BlockChain blockChain) {
        nameBlockchainMap.put(electionName, blockChain);
    }

    public void clearRepository() {
        nameBlockchainMap.clear();
    }

    public BlockChain getBlockchain(String electionName) {
        return nameBlockchainMap.get(electionName);
    }

    public boolean electionAlreadyExistsWithThisName(String electionName) {
        return nameBlockchainMap.containsKey(electionName);
    }
}
