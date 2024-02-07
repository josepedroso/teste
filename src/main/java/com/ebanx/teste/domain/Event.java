package com.ebanx.teste.domain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String type;
    private Integer origin;
    private Integer destination;
    private Integer amount;
}
