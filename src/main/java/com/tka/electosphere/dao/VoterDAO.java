package com.tka.electosphere.dao;

import com.tka.electosphere.entities.Voter;

import java.util.List;

public interface VoterDAO {
    boolean saveVoter(Voter voter);

    List<Voter> getAllVoters();

    Voter getVoterById(Long id);

    boolean updateVoter(Voter voter);

    boolean deleteVoter(Long id);
}
