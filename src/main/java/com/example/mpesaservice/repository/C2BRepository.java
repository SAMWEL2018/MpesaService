package com.example.mpesaservice.repository;

import com.example.mpesaservice.model.C2BResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author samwel.wafula
 * Created on 01/02/2024
 * Time 19:44
 * Project MpesaService
 */
public interface C2BRepository extends JpaRepository<C2BResponse, Integer> {
    Optional<C2BResponse> findByBillRefNumberAndPaymentConsumed(String phoneNo,boolean isPaymentConsumed);

    @Query(value = "update tbl_paybill set is_payment_consumed=:isPaymentConsumed where transid=:transId", nativeQuery = true)
    @Modifying
    @Transactional
    void updateIfTicketIsPaid(@Param("isPaymentConsumed") boolean isPaymentConsumed, @Param("transId") String transId);
}
