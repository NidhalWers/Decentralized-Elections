// SPDX-License-Identifier: MIT
pragma solidity >=0.7.0 <0.9.0;

contract Vote{
    struct Voter{
        uint weight;
        bool voted;
        address delegate;
        uint index; // the index for the proposal
    }

    struct Proposal {
        bytes32 name;
        uint count;
    }

    Proposal[] public Proposals;
    address public Creator;
    mapping(address => Voter) public Voters;

    constructor(bytes32[] memory ProposalNames){
        Creator = msg.sender;
        Voters[msg.sender].weight = 1;
        // todo:
        // remove the duplicate item from ProposalNames
        for(uint i=0; i<ProposalNames.length; i++){
            Proposals.push(Proposal({
                name: ProposalNames[i],
                count: 0
            }));
        }
    }

    function authorizeTo(address voter) public {
        require(
            msg.sender == Creator,
            "Uniquement le contrat du createur peut autoriser d'autre personne a voter"
        );
        require(Voters[voter].weight == 0, "Le voteur actuel a ete autorise");
        Voters[voter].weight = 1;
    }

    function delegate(address to) public {
        Voter memory sender = Voters[msg.sender];
        require(sender.weight == 1, "Tu n'as pas ete autorise a vote");
        require(!sender.voted, 'Tu as vote');
        require(to != msg.sender, "L'auto delegation n'est pas supporte");
        require(Voters[to].weight == 1, "L'agent n'est pas autorise");

        while(Voters[to].delegate != address(0)){
            to = Voters[to].delegate;
            require(to != msg.sender, "Delegation dans la boucle non autorise");
        }

        sender.voted = true;
        sender.delegate = to;
        if(Voters[to].voted){
            Proposals[Voters[to].index].count += sender.weight;
        } else {
            Voters[to].weight += sender.weight;
        }
    }

    function vote(uint proposal) public {
        Voter memory sender = Voters[msg.sender];
        require(sender.weight == 1, "Tu n'as pas la permission de voter");
        require(!sender.voted, 'A deja vote');

        sender.voted = true;
        sender.index = proposal;
        Proposals[proposal].count += sender.weight;
    }

    function getWinningProposal() public view returns (uint winningProposal){
        uint winningVoteCount = 0;
        for(uint i=0; i < Proposals.length; i++){
            if(Proposals[i].count > winningVoteCount) {
                winningVoteCount = Proposals[i].count;
                winningProposal = i;
            }
        }
    }
}