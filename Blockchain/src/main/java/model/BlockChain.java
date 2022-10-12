package model;

import service.LoggerService;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {

    public static final BlockChain BLOCK_CHAIN = new BlockChain();

    public BlockChain() {
        blockChain = new ArrayList<Block>();
    }

    private final LoggerService loggerService = new LoggerService();

    private List<Block> blockChain;

    public List<Block> getBlockChain() {
        return blockChain;
    }

    public Block getBlock(int i){
        return blockChain.get(i);
    }

    public List<Block> addBlock(Block block){
        blockChain.add(block);
        return blockChain;
    }

    public void emptyTheChain(){
        blockChain.clear();
    }

    public boolean isValid(){
        boolean validityTest = true;
        for(int i=0; i < blockChain.size(); i++){
            Block block = blockChain.get(i);

            if(!block.isValid()){
                loggerService.logInvalidBlockHash(block);
                validityTest = false;
            }
            if(i>0) {
                Block previousBlock = blockChain.get(i - 1);
                if(isPreviousHashNotEqualToPreviousBlocksHash(block, previousBlock)){
                    loggerService.logInvalidPreviousHash(block, previousBlock);
                    validityTest = false;
                }
            }
        }
        return validityTest;
    }

    private static boolean isPreviousHashNotEqualToPreviousBlocksHash(Block block, Block previousBlock) {
        return !block.getPreviousHash().equals(previousBlock.getHash());
    }
}
