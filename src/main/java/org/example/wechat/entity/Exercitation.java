package org.example.wechat.entity;

import lombok.Data;


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

    public Exercitation(String company){
        this.company = company;
    }

    @Override
    public String toString() {
        return "Exercitation{" +
                "company='" + company + '\'' +
                ", work_name='" + work_name + '\'' +
                ", salary=" + salary +
                ", work_quantity=" + work_quantity +
                '}';
    }
}
