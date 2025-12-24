package com.panunited.airdemo.models;

import com.panunited.airdemo.enums.CustomerCategory;
import com.panunited.airdemo.enums.CustomerStatus;
import com.panunited.airdemo.enums.CustomerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Customer extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerCode;
    private String customerName;
    private String customerAddress;
    private String contactNumber;
    @Enumerated(EnumType.STRING)
    private CustomerCategory customerCategory;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;
    private String customerMailingAddress1;
    private String customerMailingAddress2;
    private String city;
    private String state;
    private String posCode;
    private LocalDate setupDate;
    private String customerCountry;
    private Double creditLimit;
    private Boolean printSoa;
    private LocalDate stopSupply;
    private Boolean invoiceDraft;
    private Boolean selfBilling;
    private Boolean invoiceCarbonEmission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SALESPERSON_ID")
    private Salesperson salesperson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PAYMENT_TERM_ID")
    private PaymentTerm paymentTerm;

    // @MappedCollection(idColumn = "CUSTOMER_ID", keyColumn = "CUSTOMER_ID")
    // private Set<CustomerContact> customerContacts = new HashSet<>();

    public Customer(Long customerId, String customerCode, String customerName, String customerAddress, String contactNumber) {
        this.id = customerId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.contactNumber = contactNumber;
    }

    public Boolean getPrintSoa() {
        if (printSoa == null)
            return false;
        return printSoa;
    }

    public Boolean getInvoiceDraft() {
        if (invoiceDraft == null)
            return false;
        return invoiceDraft;
    }

    public String getAddress() {
        return this.getCustomerMailingAddress1() + ", " + this.getCustomerMailingAddress2() + ", " + this.getCustomerCountry() + ", " + this.getCustomerCountry() + ", "
                + this.getCity() + ", " + this.getState() + ", " + this.getPosCode();
    }

    public Customer(String customerCode, String customerName) {
        super();
        this.customerCode = customerCode;
        this.customerName = customerName;
    }

    public Boolean getInvoiceCarbonEmission() {
        if (invoiceCarbonEmission == null)
            return true;
        return invoiceCarbonEmission;
    }

}

