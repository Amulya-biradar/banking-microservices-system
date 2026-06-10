package com.bank.transaction_service.repository;

import com.bank.transaction_service.entity.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryImpl
        implements TransactionRepository {

    private final SessionFactory sessionFactory;

    public TransactionRepositoryImpl(
            SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(
            Transaction transaction) {

        Session session =
                sessionFactory.getCurrentSession();

        session.persist(transaction);
    }

    @Override
    public Transaction findByTransactionId(
            String transactionId) {

        Session session =
                sessionFactory.getCurrentSession();

        return session.createQuery(
                        "from Transaction where transactionId=:transactionId",
                        Transaction.class)
                .setParameter(
                        "transactionId",
                        transactionId)
                .uniqueResult();
    }

    @Override
    public List<Transaction> findAll() {

        Session session =
                sessionFactory.getCurrentSession();

        return session.createQuery(
                        "from Transaction",
                        Transaction.class)
                .list();
    }
}