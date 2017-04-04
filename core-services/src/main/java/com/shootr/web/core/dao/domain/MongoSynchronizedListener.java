package com.shootr.web.core.dao.domain;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class MongoSynchronizedListener extends AbstractMongoEventListener<Object> {

    /**
     * Called in MongoTemplate insert, insertList and save operations before the object is converted
     * to a DBObject using a MongoConveter.
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object obj = event.getSource();

        if (MongoSynchronized.class.isAssignableFrom(obj.getClass())) {

            MongoSynchronized csys = (MongoSynchronized) obj;

            long now = System.currentTimeMillis();

            csys.setRevision(csys.getRevision() != null ? csys.getRevision() + 1l : 0l);
            csys.setModified(now);
            csys.setDeleted(null);
            if (csys.getBirth() == null) {
                csys.setBirth(now);
            }
        }
    }
}
