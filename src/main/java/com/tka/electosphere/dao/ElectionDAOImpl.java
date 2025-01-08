package com.tka.electosphere.dao;

import com.tka.electosphere.entities.Election;
import com.tka.electosphere.exceptions.ElectionNotFoundException;
import com.tka.electosphere.exceptions.DatabaseException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ElectionDAOImpl implements ElectionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean saveElection(Election election) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(election);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to save election: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Election> getAllElections() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Election";
            return session.createQuery(hql, Election.class).getResultList();
        } catch (HibernateException e) {
            throw new DatabaseException("Error occurred while fetching elections: " + e.getMessage(), e);
        }
    }

    @Override
    public Election getElectionById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Election election = session.get(Election.class, id);
            if (election == null) {
                throw new ElectionNotFoundException("Election not found with ID: " + id);
            }
            return election;
        } catch (HibernateException e) {
            throw new DatabaseException("Error occurred while fetching election by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateElection(Election election) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(election);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to update election: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteElection(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Election election = session.get(Election.class, id);
            if (election != null) {
                session.delete(election);
                transaction.commit();
                return true;
            } else {
                throw new ElectionNotFoundException("Election not found with ID: " + id);
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to delete election: " + e.getMessage(), e);
        }
    }
}
