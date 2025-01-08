package com.tka.electosphere.service;

import com.tka.electosphere.entities.Candidate;
import java.util.List;

public interface CandidateService {

    boolean saveCandidate(Candidate candidate);

    List<Candidate> getAllCandidates();

    Candidate getCandidateById(Long id);

    boolean updateCandidate(Candidate candidate);

    boolean deleteCandidate(Long id);

    boolean validateCandidateName(String name);

    boolean validateAge(int age);

    boolean validateElection(Long electionId);

    boolean validateParty(String party);

    boolean validateBiography(String biography);

    boolean validateDuplicateCandidate(Candidate candidate);

    List<Candidate> filterCandidatesByAge(int minAge, int maxAge);

    List<Candidate> filterCandidatesByParty(String party);

    List<Candidate> filterCandidatesByElection(Long electionId);

    List<Candidate> filterCandidatesByName(String name);
}
