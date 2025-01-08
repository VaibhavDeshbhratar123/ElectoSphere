package com.tka.electosphere.dao;

import com.tka.electosphere.entities.Vote;

import java.util.List;

public interface VoteDAO {
    boolean saveVote(Vote vote);
    List<Vote> getAllVotes();
    Vote getVoteById(Long id);
    boolean updateVote(Vote vote);
    boolean deleteVote(Long id);
}
