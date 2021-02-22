package api.models;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Worker {
    private String name;
    private BigDecimal salary;
}
