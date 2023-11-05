package com.devsu.repositories;

import com.devsu.entities.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
