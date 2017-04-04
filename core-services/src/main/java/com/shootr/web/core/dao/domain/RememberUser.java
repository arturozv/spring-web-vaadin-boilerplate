package com.shootr.web.core.dao.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "RememberUser")
@TypeAlias("RememberUser")
public class RememberUser extends MongoSynchronized {

    @Id
    private String id;

    private String userName;

    private String cookie;

}
