package com.ebanx.teste.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String type;
    private String origin;
    private String destination;
    private Integer amount;
}
