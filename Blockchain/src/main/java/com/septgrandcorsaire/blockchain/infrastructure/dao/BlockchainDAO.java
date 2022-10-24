package com.septgrandcorsaire.blockchain.infrastructure.dao;

import com.septgrandcorsaire.blockchain.domain.BlockChain;

import java.util.HashMap;
import java.util.Map;

public class BlockchainDAO {

    public static BlockchainDAO INSTANCE = new BlockchainDAO();

    private Map<String, BlockChain> nameBlockchainMap = new HashMap<>();

    public void addBlockchain(String electionName, BlockChain blockChain) {
        nameBlockchainMap.put(electionName, blockChain);
    }

    public BlockChain getBlockchain(String electionName) {
        return nameBlockchainMap.get(electionName);
    }

    public boolean electionAlreadyExistsWithThisName(String electionName) {
        return nameBlockchainMap.containsKey(electionName);
    }
}
