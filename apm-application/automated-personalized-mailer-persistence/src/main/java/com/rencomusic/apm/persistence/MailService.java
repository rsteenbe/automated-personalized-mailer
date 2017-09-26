//////////////////////////////////////////////////////////////////////////
//                                                                      //
//      2017-09-26 - Created by Renco Steenbergen                       //
//                                                                      //
//////////////////////////////////////////////////////////////////////////

package com.rencomusic.apm.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class MailService {
    @PersistenceContext(unitName="RecipientService")
    protected EntityManager entityManager;

    EntityManager getEntityManager() {
        return entityManager;
    }

    public Mail createMailData(String category, String sender, String subject, String message) {
        Mail mail = new Mail();
        mail.setCategory(category);
        mail.setSubject(subject);
        mail.setMessage(message);
        getEntityManager().persist(mail);
        return mail;
    }

    public Mail findMail(int id) {
        return getEntityManager().find(Mail.class, id);
    }

    public List<Integer> maxMailId() {
        return entityManager.createQuery(
                "SELECT max(m.id) FROM Mail m")
                .setMaxResults(1)
                .getResultList();
    }

    public List findAllMail() {
        return entityManager.createQuery(
                "SELECT m FROM Mail m")
                .getResultList();
    }
}
