package com.bank.account_service.repository;

import com.bank.account_service.entity.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryImpl
        implements AccountRepository {

    private final SessionFactory sessionFactory;

    public AccountRepositoryImpl(
            SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Account account) {

        System.out.println("SAVE METHOD CALLED");

        Session session =
                sessionFactory.getCurrentSession();

        System.out.println("SESSION OBTAINED");

        session.persist(account);

        System.out.println("ACCOUNT SAVED");
    }

    @Override
    public Account findByAccountNumber(
            String accountNumber) {

        Session session =
                sessionFactory.getCurrentSession();

        return session.createQuery(
                        "from Account where accountNumber=:accountNumber",
                        Account.class)
                .setParameter(
                        "accountNumber",
                        accountNumber)
                .uniqueResult();
    }

    @Override
    public List<Account> findAll() {

        Session session =
                sessionFactory.getCurrentSession();

        return session.createQuery(
                        "from Account",
                        Account.class)
                .list();
    }

    @Override
    public void update(
            Account account) {

        Session session =
                sessionFactory.getCurrentSession();

        session.merge(account);
    }

    @Override
    public void delete(
            Account account) {

        Session session =
                sessionFactory.getCurrentSession();

        session.remove(account);
    }
}