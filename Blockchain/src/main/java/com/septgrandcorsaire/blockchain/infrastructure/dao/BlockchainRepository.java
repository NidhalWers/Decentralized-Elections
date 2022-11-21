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


    public void addBlockchain(String electionName, BlockChain blockChain, String electionStatus) {
        nameBlockchainMap.put(electionName, ElectionStatusAndBlockchain.of(electionStatus, blockChain));
    }

    public void addBlockchain(String electionName, BlockChain blockChain) {
        addBlockchain(electionName, blockChain, null);
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
            if (electionStatus == null
                    || electionStatus.equals("")
                    || electionStatus.equals(value.status)) {
                return value.blockChain;
            }
        }
        return null;
    }

    public BlockChain getBlockchain(String electionName) {
        return getBlockchain(electionName, null);
    }

    public boolean electionAlreadyExistsWithThisNameAndStatus(String electionName, String electionStatus) {
        return getBlockchain(electionName, electionStatus) != null;
    }

    public boolean electionAlreadyExistsWithThisName(String electionName) {
        return electionAlreadyExistsWithThisNameAndStatus(electionName, null);
    }

}
