package com.example.mpesaservice.repository;

import com.example.mpesaservice.model.PayRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author samwel.wafula
 * Created on 29/01/2024
 * Time 14:54
 * Project MpesaService
 */
@Repository
public interface PayRequestRepository extends JpaRepository<PayRequest,Integer> {

    PayRequest findByMerchantRequestID(String merchantRequestId);

   // @Modifying(clearAutomatically = true)
    @Query(value = "update tbl_stk_payments set  stk_callback_response_code=:responseCode,stk_callback_response_desc=:responseDesc,mpesa_receipt_number=:mpesaReceiptNumber where merchant_request_id=:merchantRequestId",nativeQuery = true)
    @Modifying
    @Transactional
    int updatePayment(@Param("responseCode") int responseCode,@Param("responseDesc") String responseDesc,@Param("mpesaReceiptNumber") String mpesaReceiptNumber,@Param("merchantRequestId") String merchantRequestId);
}
