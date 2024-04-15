package org.example.wechat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Exercitation {
    private String company;
    private String work_name;
    private Integer salary;
    private Integer work_quantity;

    public Exercitation(String company, String work_name, Integer salary, Integer work_quantity){
        this.company = company;
        this.work_name = work_name;
        this.salary = salary;
        this.work_quantity = work_quantity;
    }
}
