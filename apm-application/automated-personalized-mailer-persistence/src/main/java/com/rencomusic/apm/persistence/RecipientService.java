//////////////////////////////////////////////////////////////////////////
//                                                                      //
//      2017-09-20 - Created by Renco Steenbergen                       //
//                                                                      //
//////////////////////////////////////////////////////////////////////////

package com.rencomusic.apm.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RecipientService {
    @PersistenceContext(unitName = "RecipientService")
    protected EntityManager entityManager;

    EntityManager getEntityManager() {
        return entityManager;
    }

    public Recipient createRecipient(String name, String mail, String category) {
        Recipient recipient = new Recipient();
        recipient.setName(name);
        recipient.setMail(mail);
        recipient.setCategory(category);
        getEntityManager().persist(recipient);
        return recipient;
    }

    public List<Recipient> findAll() {
        TypedQuery query = getEntityManager().createQuery("SELECT r FROM Recipient r", Recipient.class);
        return query.getResultList();
    }

    public List findByCategory(String name) {
        return  entityManager.createQuery(
                "SELECT r FROM Recipient r WHERE r.category LIKE :name")
                .setParameter("name", name)
                .getResultList();
    }

    public List findByMail(String mail) {
        return  entityManager.createQuery(
                        "SELECT r FROM Recipient r WHERE r.mail LIKE :mail")
                        .setParameter("mail", mail)
                        .setMaxResults(1)
                        .getResultList();
    }
}