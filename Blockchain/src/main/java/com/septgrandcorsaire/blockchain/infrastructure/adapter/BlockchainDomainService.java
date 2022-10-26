package com.septgrandcorsaire.blockchain.infrastructure.adapter;

import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.util.LoggerService;
import org.springframework.stereotype.Service;

@Service
public class BlockchainDomainService {

    private LoggerService loggerService;

    public BlockchainDomainService() {
        this.loggerService = new LoggerService();
    }

    public boolean isBlockchainValid(BlockChain blockChain) {
        if (!blockChain.isFirstBlockValid()) {
            return false;
        }
        boolean validityTest = true;
        for (int i = 1; i < blockChain.getBlocks().size(); i++) {
            Block block = blockChain.getBlocks().get(i);
            Block previousBlock = blockChain.getBlocks().get(i - 1);

            validityTest = isValidAddedBlock(validityTest, block, previousBlock);
        }
        return validityTest;
    }

    private boolean isValidAddedBlock(boolean validityTest, Block block, Block previousBlock) {
        if (!block.isValid()) {
            loggerService.logInvalidBlockHash(block);
            validityTest = false;
        }
        if (doesPreviousIndexNotMatch(block, previousBlock)) {
            loggerService.logInvalidIndexes(block, previousBlock);
            validityTest = false;
        }
        if (isPreviousHashNotEqualToPreviousBlocksHash(block, previousBlock)) {
            loggerService.logInvalidPreviousHash(block, previousBlock);
            validityTest = false;
        }
        return validityTest;
    }

    private static boolean doesPreviousIndexNotMatch(Block block, Block previousBlock) {
        return block.getIndex() != previousBlock.getIndex() + 1;
    }

    private static boolean isPreviousHashNotEqualToPreviousBlocksHash(Block block, Block previousBlock) {
        return !block.getPreviousHash().equals(previousBlock.getHash());
    }

}
