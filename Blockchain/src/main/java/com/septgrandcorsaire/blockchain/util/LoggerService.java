package com.septgrandcorsaire.blockchain.util;



import com.septgrandcorsaire.blockchain.model.Block;
import com.septgrandcorsaire.blockchain.model.BlockChain;
import com.septgrandcorsaire.blockchain.util.error.ErrorMessageService;
import com.septgrandcorsaire.blockchain.util.error.WarningType;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerService {

    private static Logger logger = Logger.getLogger(BlockChain.class.getName());
    private final ErrorMessageService errorMessageService = new ErrorMessageService();

    public void logInvalidBlockHash(Block block){
        logWarning(WarningType.INVALID_BLOCK_HASH, errorMessageService.createInvalidBlockHashMessage(block));
    }

    public void logInvalidPreviousHash(Block block, Block previousBlock){
        logWarning(WarningType.INVALID_PREVIOUS_HASH, errorMessageService.createInvalidPreviousHashMessage(block, previousBlock));
    }

    public void logInvalidIndexes(Block block, Block previousBlock) {
        logWarning(WarningType.INVALID_INDEXES, errorMessageService.createInvalidIndexesMessage(block, previousBlock));
    }

    private void logWarning(WarningType warningType, String message){
        logger.log(Level.WARNING, warningType.name()+" "+message);
    }


}
