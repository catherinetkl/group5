package com.ntu.edu.group5.ecommerce.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(name="order_date", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @CreationTimestamp
    private Timestamp orderDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer orderingCustomer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private OrderDummyP orderedProduct;

    // TODO ~validation causing internal server error -
    // @NotBlank(message ="Please key in quantity")
    // @Range(min=1 , max = 9)
    // @Digits(fraction = 0, integer = 1)
    @Column(name = "quantity")
    private Integer orderedQuantity;

    public Order(Customer orderingCustomer, OrderDummyP orderedProduct, Integer orderedQuantity) {
        this.orderingCustomer = orderingCustomer;
        this.orderedProduct = orderedProduct;
        this.orderedQuantity = orderedQuantity;
    }

    public long getCustomerId(){
        return orderingCustomer.getId();
    }

    public long getProductId(){
        return orderedProduct.getProductId();
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", orderingCustomer=" + orderingCustomer.getFirstName()
                + ", orderedProduct=" + orderedProduct.getName() + ", orderedQuantity=" + orderedQuantity + "]";
    }

    
}
