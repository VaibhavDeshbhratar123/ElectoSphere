package com.tka.electosphere.dao;

import com.tka.electosphere.entities.Vote;
import com.tka.electosphere.exceptions.DatabaseException;
import com.tka.electosphere.exceptions.VoteNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteDAOImpl implements VoteDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean saveVote(Vote vote) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(vote);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to save vote: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Vote> getAllVotes() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Vote";
            return session.createQuery(hql, Vote.class).getResultList();
        } catch (HibernateException e) {
            throw new DatabaseException("Error occurred while fetching votes: " + e.getMessage(), e);
        }
    }

    @Override
    public Vote getVoteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Vote vote = session.get(Vote.class, id);
            if (vote == null) {
                throw new VoteNotFoundException("Vote not found with ID: " + id);
            }
            return vote;
        } catch (HibernateException e) {
            throw new DatabaseException("Error occurred while fetching vote by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateVote(Vote vote) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(vote);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to update vote: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteVote(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Vote vote = session.get(Vote.class, id);
            if (vote != null) {
                session.delete(vote);
                transaction.commit();
                return true;
            } else {
                throw new VoteNotFoundException("Vote not found with ID: " + id);
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Failed to delete vote: " + e.getMessage(), e);
        }
    }
}
