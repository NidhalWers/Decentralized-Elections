package com.septgrandcorsaire.blockchain.infrastructure.util;


import com.septgrandcorsaire.blockchain.Application;
import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.infrastructure.util.warning.WarningMessageService;
import com.septgrandcorsaire.blockchain.infrastructure.util.warning.WarningType;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class LoggerService {

    private static Logger logger = Application.LOGGER;

    private WarningMessageService warningMessageService = new WarningMessageService();

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
