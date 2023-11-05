package com.devsu.repositories;

import com.devsu.dto.ReportProject;
import com.devsu.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByIdAndStatus(Long id, Boolean status);

    @Query(value = "SELECT t.update_date updateDate, p.name client, a.id, a.type, " +
            "a.initial_balance initialBalance,\n" +
            "a.status, t.amount, a.current_balance currentBalance\n" +
            "FROM person p\n" +
            "JOIN account a ON p.id = a.person_id\n" +
            "JOIN transaction t ON a.id = t.account_id\n" +
            "WHERE p.id = ?3 and t.update_date between ?1 and ?2 ", nativeQuery=true)
    List<ReportProject> getReport(Instant initialDate, Instant finalDate, Long clientId);
}
