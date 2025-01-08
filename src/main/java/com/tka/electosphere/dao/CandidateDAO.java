package com.tka.electosphere.dao;

import com.tka.electosphere.entities.Candidate;
import java.util.List;

public interface CandidateDAO {

    boolean saveCandidate(Candidate candidate);

    List<Candidate> getAllCandidates();

    Candidate getCandidateById(Long id);

    boolean updateCandidate(Candidate candidate);

    boolean deleteCandidate(Long id);
}
