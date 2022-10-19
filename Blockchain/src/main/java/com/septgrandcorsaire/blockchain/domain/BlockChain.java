package com.septgrandcorsaire.blockchain.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class BlockChain {

    private String name;

    private List<Block> blocks;
    private int difficulty;

    private static final String GENESIS_BLOCK_DATA = "Genesis block";

    public BlockChain(String name, int difficulty) {
        this.blocks = new ArrayList<Block>();
        this.name = name;
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

    public String getName() {
        return name;
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


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < blocks.size(); i++) {
            builder.append(blocks.get(i).getData() + "\n");
        }
        return builder.toString();
    }


    public boolean isFirstBlockValid() {
        Block firstBlock = blocks.get(0);
        return firstBlock.getIndex() == 0 &&
                firstBlock.getPreviousHash() == null &&
                firstBlock.isValid();
    }
}
