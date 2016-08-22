package main.java.model;

import main.java.model.common.GroupObject;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "PRODUCTS")
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_ID"))
public class Product extends GroupObject {
    private String name;
    private String description;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private List<ProductOperation> productOperations;

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "ATTRIBUTE_1")
    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    @Column(name = "ATTRIBUTE_2")
    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    @Column(name = "ATTRIBUTE_3")
    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    public List<ProductOperation> getProductOperations() {
        return productOperations;
    }

    public void setProductOperations(List<ProductOperation> productOperations) {
        this.productOperations = productOperations;
    }

    @PostLoad
    public void sortOperations() {
        Collections.sort(productOperations, new Comparator<ProductOperation>() {
            @Override
            public int compare(ProductOperation o1, ProductOperation o2) {
                if(o1.getOperationNumber() == null && o2.getOperationNumber() == null) {
                    return 0;
                }
                else if(o1.getOperationNumber() == null && o2.getOperationNumber() != null) {
                    return -1;
                }
                else if(o1.getOperationNumber() != null && o2.getOperationNumber() == null) {
                    return  1;
                }
                else {
                    return o1.getOperationNumber() - o2.getOperationNumber();
                }
            }
        });
    }

    public void orderProductOperations() {
        int i = 0;
        for(ProductOperation productOperation : productOperations) {
            if(productOperation.isSequential() == true) {
                productOperation.setOperationNumber(i++);
            }
        }
    }
}
