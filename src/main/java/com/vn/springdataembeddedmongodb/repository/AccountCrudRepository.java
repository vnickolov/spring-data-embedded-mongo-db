package com.vn.springdataembeddedmongodb.repository;

import com.vn.springdataembeddedmongodb.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountCrudRepository extends ReactiveMongoRepository<Account, String> {

  Flux<Account> findAllByValue(Double value);

  Mono<Account> findFirstByOwner(Mono<String> owner);
}
