package com.tka.electosphere.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.electosphere.entities.Candidate;
import com.tka.electosphere.exceptions.CandidateNotFoundException;
import com.tka.electosphere.exceptions.DatabaseException;

@Repository
public class CandidateDAOImpl implements CandidateDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean saveCandidate(Candidate candidate) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(candidate);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to save candidate: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Candidate> getAllCandidates() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Candidate";
            List<Candidate> candidates = session.createQuery(hql, Candidate.class).getResultList();
            return candidates;
        } catch (HibernateException e) {
            throw new DatabaseException("Error occurred while fetching candidates: " + e.getMessage(), e);
        }
    }

    @Override
    public Candidate getCandidateById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Candidate candidate = session.get(Candidate.class, id);
            if (candidate == null) {
                throw new CandidateNotFoundException("Candidate not found with ID: " + id);
            }
            return candidate;
        } catch (HibernateException e) {
            throw new DatabaseException("Error occurred while fetching candidate by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateCandidate(Candidate candidate) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(candidate);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to update candidate: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteCandidate(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Candidate candidate = session.get(Candidate.class, id);
            if (candidate != null) {
                session.delete(candidate);
                transaction.commit();
                return true;
            } else {
                throw new CandidateNotFoundException("Candidate not found with ID: " + id);
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to delete candidate: " + e.getMessage(), e);
        }
    }
}
