package com.coinmaster.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coinmaster.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

}
