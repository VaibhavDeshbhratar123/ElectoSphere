package com.tka.electosphere.dao;

import com.tka.electosphere.entities.Election;
import java.util.List;

public interface ElectionDAO {

    boolean saveElection(Election election);

    List<Election> getAllElections();

    Election getElectionById(Long id);

    boolean updateElection(Election election);

    boolean deleteElection(Long id);
}
