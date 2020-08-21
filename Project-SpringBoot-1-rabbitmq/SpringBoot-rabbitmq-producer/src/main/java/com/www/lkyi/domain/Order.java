package com.www.lkyi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = 8229086380909212145L;

    private String id;

    private String name;

    private String messageId;

    public Order(String id, String name, String messageId) {
        this.id = id;
        this.name = name;
        this.messageId = messageId;
    }
}
