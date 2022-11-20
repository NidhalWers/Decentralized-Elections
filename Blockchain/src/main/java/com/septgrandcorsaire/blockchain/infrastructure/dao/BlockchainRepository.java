package com.septgrandcorsaire.blockchain.infrastructure.dao;

import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.model.ElectionStatusAndBlockchain;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BlockchainRepository {

    public static BlockchainRepository INSTANCE = new BlockchainRepository();


    private Map<String, ElectionStatusAndBlockchain> nameBlockchainMap = new HashMap<>();

    private BlockchainRepository() {
        this.nameBlockchainMap = nameBlockchainMap;
    }


    public void addBlockchain(String electionName, BlockChain blockChain) {
        nameBlockchainMap.put(electionName, ElectionStatusAndBlockchain.of(blockChain));
    }


    public void addBlockchain(String electionName, String electionStatus, BlockChain blockChain) {
        nameBlockchainMap.put(electionName, ElectionStatusAndBlockchain.of(electionStatus, blockChain));
    }

    public void clearRepository() {
        nameBlockchainMap.clear();
    }

    public BlockChain getBlockchain(String electionName, String electionStatus) {
        ElectionStatusAndBlockchain value = nameBlockchainMap.get(electionName);
        if (value != null) {
            if (electionStatus == null || (electionStatus != null && electionStatus.equals(value.status))) {
                return value.blockChain;
            }
        }
        return null;
    }

    public BlockChain getBlockchain(String electionName) {
        return getBlockchain(electionName, null);
    }

    public boolean electionAlreadyExistsWithThisName(String electionName) {
        return nameBlockchainMap.containsKey(electionName);
    }
}
