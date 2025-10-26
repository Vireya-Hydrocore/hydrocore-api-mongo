package org.api.hydrocore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "login")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Field(name = "_id")
    private String id;

    private String email;
    @Field(name = "senha")
    private String password;
    private String codigoEmpresa;
    private String chaveApi;
    private String fcmToken;

}