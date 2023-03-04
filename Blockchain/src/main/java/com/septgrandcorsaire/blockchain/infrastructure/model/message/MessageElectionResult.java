package com.septgrandcorsaire.blockchain.infrastructure.model.message;

import com.septgrandcorsaire.blockchain.infrastructure.model.message.code.ElectionState;

public interface MessageElectionResult {
    ElectionState code();
}
