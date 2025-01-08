package com.tka.electosphere.service;

import com.tka.electosphere.dao.CandidateDAO;
import com.tka.electosphere.entities.Candidate;
import com.tka.electosphere.entities.Election;
import com.tka.electosphere.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateDAO candidateDAO;

    // CRUD Operations from CandidateDAO

    public boolean saveCandidate(Candidate candidate) {
        try {
            // Perform all validations
            validateCandidate(candidate);
            return candidateDAO.saveCandidate(candidate);
        } catch (Exception e) {
            throw new DatabaseException("Failed to save candidate: " + e.getMessage(), e);
        }
    }

    public List<Candidate> getAllCandidates() {
        try {
            return candidateDAO.getAllCandidates();
        } catch (Exception e) {
            throw new DatabaseException("Error occurred while fetching candidates: " + e.getMessage(), e);
        }
    }

    public Candidate getCandidateById(Long id) {
        validateCandidateId(id);
        try {
            return candidateDAO.getCandidateById(id);
        } catch (Exception e) {
            throw new DatabaseException("Error occurred while fetching candidate by ID: " + e.getMessage(), e);
        }
    }

    public boolean updateCandidate(Candidate candidate) {
        try {
            validateCandidate(candidate);
            return candidateDAO.updateCandidate(candidate);
        } catch (Exception e) {
            throw new DatabaseException("Failed to update candidate: " + e.getMessage(), e);
        }
    }

    public boolean deleteCandidate(Long id) {
        validateCandidateId(id);
        try {
            return candidateDAO.deleteCandidate(id);
        } catch (Exception e) {
            throw new DatabaseException("Failed to delete candidate: " + e.getMessage(), e);
        }
    }

    // Validation and Business Logic Methods

    private void validateCandidate(Candidate candidate) {
        validateCandidateName(candidate.getName());
        validateAge(candidate.getAge());
        validateElection(candidate.getElection());
        validateParty(candidate.getParty());
        validateBiography(candidate.getBiography());
        validateDuplicateCandidate(candidate);
    }

    private void validateCandidateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidIdException("Invalid ID: ID cannot be null or non-positive.");
        }
    }

    private void validateCandidateName(String name) {
        if (name == null || name.length() < 3 || name.length() > 100) {
            throw new InvalidCandidateNameException("Candidate name must be between 3 and 100 characters.");
        }
    }

    private void validateAge(int age) {
        if (age < 18 || age > 120) {
            throw new InvalidAgeException("Candidate must be at least 18 years old.");
        }
    }

    private void validateElection(Election election) {
        if (election == null || !election.isActive()) {
            throw new InvalidElectionException("Candidate's associated election is either invalid or inactive.");
        }
    }

    private void validateParty(String party) {
        List<String> validParties = new ArrayList<>();
        validParties.add("Party A");
        validParties.add("Party B");
        validParties.add("Party C");
        boolean isValidParty = false;
        for (String validParty : validParties) {
            if (validParty.equals(party)) {
                isValidParty = true;
                break;
            }
        }
        if (!isValidParty) {
            throw new InvalidPartyException("Invalid party name.");
        }
    }

    private void validateBiography(String biography) {
        if (biography != null && biography.length() > 1000) {
            throw new InvalidBiographyException("Biography cannot exceed 1000 characters.");
        }
    }

    private void validateDuplicateCandidate(Candidate candidate) {
        List<Candidate> candidates = candidateDAO.getAllCandidates();
        for (Candidate existingCandidate : candidates) {
            if (existingCandidate.getName().equals(candidate.getName()) && 
                existingCandidate.getParty().equals(candidate.getParty()) &&
                existingCandidate.getElection().equals(candidate.getElection())) {
                throw new DuplicateCandidateException("Candidate with this name and party already exists in the election.");
            }
        }
    }

    // Business Logic for Filtering and Sorting

    public List<Candidate> filterCandidatesByElection(Election election) {
        List<Candidate> filteredCandidates = new ArrayList<>();
        List<Candidate> allCandidates = candidateDAO.getAllCandidates();
        for (Candidate candidate : allCandidates) {
            if (candidate.getElection().equals(election)) {
                filteredCandidates.add(candidate);
            }
        }
        return filteredCandidates;
    }

    public List<Candidate> filterCandidatesByParty(String party) {
        List<Candidate> filteredCandidates = new ArrayList<>();
        List<Candidate> allCandidates = candidateDAO.getAllCandidates();
        for (Candidate candidate : allCandidates) {
            if (candidate.getParty().equalsIgnoreCase(party)) {
                filteredCandidates.add(candidate);
            }
        }
        return filteredCandidates;
    }

    public List<Candidate> filterCandidatesByAgeRange(int minAge, int maxAge) {
        List<Candidate> filteredCandidates = new ArrayList<>();
        List<Candidate> allCandidates = candidateDAO.getAllCandidates();
        for (Candidate candidate : allCandidates) {
            if (candidate.getAge() >= minAge && candidate.getAge() <= maxAge) {
                filteredCandidates.add(candidate);
            }
        }
        return filteredCandidates;
    }

    public List<Candidate> filterByActiveElectionStatus() {
        List<Candidate> filteredCandidates = new ArrayList<>();
        List<Candidate> allCandidates = candidateDAO.getAllCandidates();
        for (Candidate candidate : allCandidates) {
            if (candidate.getElection().isActive()) {
                filteredCandidates.add(candidate);
            }
        }
        return filteredCandidates;
    }

    public List<Candidate> filterCandidatesByExperienceLength(int minExperienceLength) {
        List<Candidate> filteredCandidates = new ArrayList<>();
        List<Candidate> allCandidates = candidateDAO.getAllCandidates();
        for (Candidate candidate : allCandidates) {
            if (candidate.getExperience().length() >= minExperienceLength) {
                filteredCandidates.add(candidate);
            }
        }
        return filteredCandidates;
    }

    public List<Candidate> filterCandidatesByBiographyLength(int maxBiographyLength) {
        List<Candidate> filteredCandidates = new ArrayList<>();
        List<Candidate> allCandidates = candidateDAO.getAllCandidates();
        for (Candidate candidate : allCandidates) {
            if (candidate.getBiography().length() <= maxBiographyLength) {
                filteredCandidates.add(candidate);
            }
        }
        return filteredCandidates;
    }

    public List<Candidate> filterCandidatesByNameKeyword(String keyword) {
        List<Candidate> filteredCandidates = new ArrayList<>();
        List<Candidate> allCandidates = candidateDAO.getAllCandidates();
        for (Candidate candidate : allCandidates) {
            if (candidate.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredCandidates.add(candidate);
            }
        }
        return filteredCandidates;
    }

    public List<Candidate> filterByPartyAffiliation(String party) {
        List<Candidate> filteredCandidates = new ArrayList<>();
        List<Candidate> allCandidates = candidateDAO.getAllCandidates();
        for (Candidate candidate : allCandidates) {
            if (candidate.getParty().equalsIgnoreCase(party)) {
                filteredCandidates.add(candidate);
            }
        }
        return filteredCandidates;
    }

    public List<Candidate> sortCandidatesByAge() {
        List<Candidate> sortedCandidates = new ArrayList<>(candidateDAO.getAllCandidates());
        sortedCandidates.sort((c1, c2) -> Integer.compare(c1.getAge(), c2.getAge()));
        return sortedCandidates;
    }

    public List<Candidate> sortCandidatesByExperienceLength() {
        List<Candidate> sortedCandidates = new ArrayList<>(candidateDAO.getAllCandidates());
        sortedCandidates.sort((c1, c2) -> Integer.compare(c1.getExperience().length(), c2.getExperience().length()));
        return sortedCandidates;
    }
}
