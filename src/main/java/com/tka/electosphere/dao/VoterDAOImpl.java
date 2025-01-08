package com.tka.electosphere.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.electosphere.entities.Voter;
import com.tka.electosphere.exceptions.DatabaseException;
import com.tka.electosphere.exceptions.VoterNotFoundException;

@Repository
public class VoterDAOImpl implements VoterDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean saveVoter(Voter voter) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(voter);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to save voter: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Voter> getAllVoters() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Voter";
            return session.createQuery(hql, Voter.class).getResultList();
        } catch (HibernateException e) {
            throw new DatabaseException("Error occurred while fetching voters: " + e.getMessage(), e);
        }
    }

    @Override
    public Voter getVoterById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Voter voter = session.get(Voter.class, id);
            if (voter == null) {
                throw new VoterNotFoundException("Voter not found with ID: " + id);
            }
            return voter;
        } catch (HibernateException e) {
            throw new DatabaseException("Error occurred while fetching voter by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateVoter(Voter voter) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(voter);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to update voter: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteVoter(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Voter voter = session.get(Voter.class, id);
            if (voter != null) {
                session.delete(voter);
                transaction.commit();
                return true;
            } else {
                throw new VoterNotFoundException("Voter not found with ID: " + id);
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to delete voter: " + e.getMessage(), e);
        }
    }
}
