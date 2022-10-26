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

    public BlockChain(String name, int difficulty) {
        this.blocks = new ArrayList<Block>();
        this.name = name;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public List<Block> getVotingBlock() {
        return blocks.subList(1, blocks.size());
    }

    public Block getBlock(int i) {
        return blocks.get(i);
    }

    public Block getLatestBlock() {
        return blocks.get(blocks.size() - 1);
    }

    public ElectionInitializationData getInitializationData() {
        return (ElectionInitializationData) blocks.get(0).getData();
    }

    public Block newBlock(Data data, int index, String latestHash) {
        return Block.builder()
                .index(index)
                .previousHash(latestHash)
                .timeStamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    public Block newBlock(Data data) {
        return newBlock(data, getLatestBlock().getIndex() + 1, getLatestBlock().getHash());
    }

    public Block newBlock(String data) {
        return newBlock(StringData.of(data));
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
