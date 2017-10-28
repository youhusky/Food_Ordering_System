package com.bihju.repository;

import com.bihju.model.Payment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "payments")
public interface PaymentRepository extends PagingAndSortingRepository<Payment, String> {
    @RestResource(rel = "by-id", description = @Description("find payment by id"))
    Payment findPaymentById(@Param("id") String id);
}
