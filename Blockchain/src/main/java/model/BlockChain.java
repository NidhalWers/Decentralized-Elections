package model;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {

    public static final BlockChain BLOCK_CHAIN = new BlockChain();

    private List<Block> blockChain;

    public BlockChain() {
        blockChain = new ArrayList<Block>();
    }

    public List<Block> getBlockChain() {
        return blockChain;
    }

    public List<Block> addBlock(Block block){
        blockChain.add(block);
        return blockChain;
    }

    public void emptyTheChain(){
        blockChain.clear();
    }
}
