package com.shootr.web.core.dao.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * To make it work automatically a bean of MongoSynchronizedListener must be defined.
 */
public abstract class MongoSynchronized implements Serializable {
    private static final long serialVersionUID = 1L;

    @Field("sys_birth")
    private Long birth;

    @Field("sys_modified")
    private Long modified;

    @Field("sys_revision")
    private Long revision;

    @Field("sys_deleted")
    private Long deleted;


    public Long getBirth() {
        return birth;
    }

    public void setBirth(Long birth) {
        this.birth = birth;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "{" +
                "birth=" + birth +
                ", modified=" + modified +
                ", revision=" + revision +
                ", deleted=" + deleted +
                '}';
    }
}
