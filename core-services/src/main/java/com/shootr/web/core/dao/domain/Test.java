package com.shootr.web.core.dao.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Test")
@TypeAlias("Test")
public class Test extends MongoSynchronized implements Serializable {

    @Id
    private String id;

    @NotNull
    private String name;
}
