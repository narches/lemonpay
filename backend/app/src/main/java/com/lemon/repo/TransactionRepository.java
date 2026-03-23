//package com.lemon.repo;
//
//import com.lemon.models.Transaction;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface TransactionRepository extends MongoRepository<Transaction, String> {
//
//    List<Transaction> findTop10ByDebitPhoneOrCreditPhoneOrderByCreatedAtDesc(
//            String debitPhone,
//            String creditPhone
//    );
//
//    Optional<Transaction> findByReference(String reference);
//}


package com.lemon.repo;

import com.lemon.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository
        extends MongoRepository<Transaction, String> {

    /**
     * Fetch transactions where the account participated
     * (either debit or credit side)
     */
    @Query("""
        {
          $or: [
            { debitPhone: ?0 },
            { creditPhone: ?0 }
          ]
        }
        """)
    List<Transaction> findForAccount(
            String phoneNumber,
            Pageable pageable
    );

    /**
     * Lookup by reference (ledger-safe)
     */
    Optional<Transaction> findByReference(String reference, String phoneNumber);
}