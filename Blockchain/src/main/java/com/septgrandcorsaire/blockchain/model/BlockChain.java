package com.septgrandcorsaire.blockchain.model;

import com.septgrandcorsaire.blockchain.util.LoggerService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class BlockChain {

    public static final int MINING_DIFFICULTY = 4;
    public static final BlockChain BLOCK_CHAIN = new BlockChain(MINING_DIFFICULTY);

    private LoggerService loggerService;

    private List<Block> blocks;
    private int difficulty;

    private static final String GENESIS_BLOCK_DATA = "Genesis block";

    public BlockChain(int difficulty) {
        this.blocks = new ArrayList<Block>();
        this.difficulty = difficulty;
        Block genesisBlock = Block.builder()
                .index(0)
                .timeStamp(LocalDateTime.now())
                .previousHash(null)
                .data(GENESIS_BLOCK_DATA)
                .build();
        genesisBlock.mineBlock(difficulty);
        blocks.add(genesisBlock);
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public Block getBlock(int i) {
        return blocks.get(i);
    }

    public Block getLatestBlock() {
        return blocks.get(blocks.size() - 1);
    }

    public Block newBlock(String data) {
        return Block.builder()
                .index(getLatestBlock().getIndex() + 1)
                .previousHash(getLatestBlock().getHash())
                .timeStamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    public List<Block> addBlock(Block block) {
        if (block != null) {
            block.mineBlock(difficulty);
            blocks.add(block);
        }
        return blocks;
    }

    public void emptyTheChain() {
        blocks.subList(1, blocks.size()).clear();
    }

    public void printBlockchain() {
        blocks.stream()
                .forEach(block -> System.out.println(block.toString() + "\n"));
    }

    public boolean isFirstBlockValid() {
        Block firstBlock = blocks.get(0);
        return firstBlock.getIndex() == 0 &&
                firstBlock.getPreviousHash() == null &&
                firstBlock.isValid();
    }

    public boolean isBlockchainValid() {
        if (!isFirstBlockValid()) {
            return false;
        }
        boolean validityTest = true;
        for (int i = 1; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            Block previousBlock = blocks.get(i - 1);

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
