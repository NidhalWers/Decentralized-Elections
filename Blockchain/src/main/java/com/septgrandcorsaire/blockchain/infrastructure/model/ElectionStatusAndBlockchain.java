package com.septgrandcorsaire.blockchain.infrastructure.model;

import com.septgrandcorsaire.blockchain.domain.BlockChain;

public class ElectionStatusAndBlockchain {

    public final String status;

    public final BlockChain blockChain;

    private ElectionStatusAndBlockchain(String status, BlockChain blockChain) {
        this.status = status;
        this.blockChain = blockChain;
    }

    public static ElectionStatusAndBlockchain of(String status, BlockChain blockChain) {
        return new ElectionStatusAndBlockchain(status, blockChain);
    }

    public static ElectionStatusAndBlockchain of(BlockChain blockChain) {
        return new ElectionStatusAndBlockchain(null, blockChain);
    }
}
