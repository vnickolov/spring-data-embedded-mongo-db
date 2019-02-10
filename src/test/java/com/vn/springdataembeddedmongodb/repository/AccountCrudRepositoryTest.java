package com.vn.springdataembeddedmongodb.repository;

import com.vn.springdataembeddedmongodb.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AccountCrudRepositoryTest {

  @Autowired
  private AccountCrudRepository repository;

  @Test
  void givenValue_whenFindAllByValue_thenFindAccount() {
    repository.save(new Account(null, "Bill", 12.3)).block();
    Flux<Account> accountFlux = repository.findAllByValue(12.3);

    StepVerifier
      .create(accountFlux)
      .assertNext(account -> {
        assertEquals("Bill", account.getOwner());
        assertEquals(Double.valueOf(12.3) , account.getValue());
        assertNotNull(account.getId());
      })
      .expectComplete()
      .verify();
  }

  @Test
  void givenOwner_whenFindFirstByOwner_thenFindAccount() {
    repository.save(new Account(null, "Bill", 12.3)).block();
    Mono<Account> accountMono = repository
      .findFirstByOwner(Mono.just("Bill"));

    StepVerifier
      .create(accountMono)
      .assertNext(account -> {
        assertEquals("Bill", account.getOwner());
        assertEquals(Double.valueOf(12.3) , account.getValue());
        assertNotNull(account.getId());
      })
      .expectComplete()
      .verify();
  }

  @Test
  void givenNoOwner_whenFindFirstByOwner_thenReturnEmpty() {
    Mono<Account> accountMono = repository.findFirstByOwner(Mono.just("Bill"));

    StepVerifier
      .create(accountMono)
      .expectNextCount(0)
      .expectComplete()
      .verify();
  }

  @Test
  void givenNoOwner_whenFindByValue_thenReturnEmpty() {
    Flux<Account> accountFlux = repository.findAllByValue(12.3);

    StepVerifier
      .create(accountFlux)
      .expectNextCount(0)
      .expectComplete()
      .verify();
  }

  @Test
  void givenAccount_whenSave_thenSaveAccount() {
    Mono<Account> accountMono = repository.save(new Account(null, "Bill", 12.3));

    StepVerifier
      .create(accountMono)
      .assertNext(account -> assertNotNull(account.getId()))
      .expectComplete()
      .verify();
  }

}