package com.septgrandcorsaire.blockchain.infrastructure.model.message;

import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.code.VoteState;

public class MessageVoteInElection {

    public final Block vote;

    public final BlockChain election;

    public final VoteState code;

    private MessageVoteInElection(Block vote, BlockChain election, VoteState code) {
        this.vote = vote;
        this.election = election;
        this.code = code;
    }

    public static MessageVoteInElection of(Block block, BlockChain blockChain) {
        return new MessageVoteInElection(block, blockChain, VoteState.VOTE_FOUND);
    }

    public static MessageVoteInElection of(Block block) {
        return new MessageVoteInElection(block, null, VoteState.VOTE_FOUND);
    }

    public static MessageVoteInElection of(BlockChain blockChain) {
        return new MessageVoteInElection(null, blockChain, VoteState.VOTE_NOT_FOUND);
    }
}
