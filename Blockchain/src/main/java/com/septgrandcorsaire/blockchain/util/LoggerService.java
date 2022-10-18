package com.septgrandcorsaire.blockchain.util;


import com.septgrandcorsaire.blockchain.Application;
import com.septgrandcorsaire.blockchain.model.Block;
import com.septgrandcorsaire.blockchain.util.warning.WarningMessageService;
import com.septgrandcorsaire.blockchain.util.warning.WarningType;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class LoggerService {

    private static Logger logger = Application.LOGGER;

    private WarningMessageService warningMessageService;

    public void logInvalidBlockHash(Block block) {
        logWarning(WarningType.INVALID_BLOCK_HASH, warningMessageService.createInvalidBlockHashMessage(block));
    }

    public void logInvalidPreviousHash(Block block, Block previousBlock) {
        logWarning(WarningType.INVALID_PREVIOUS_HASH, warningMessageService.createInvalidPreviousHashMessage(block, previousBlock));
    }

    public void logInvalidIndexes(Block block, Block previousBlock) {
        logWarning(WarningType.INVALID_INDEXES, warningMessageService.createInvalidIndexesMessage(block, previousBlock));
    }

    private void logWarning(WarningType warningType, String message) {
        logger.log(Level.WARNING, warningType.name() + " " + message);
    }


}
