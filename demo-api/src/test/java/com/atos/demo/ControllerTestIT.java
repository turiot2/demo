package com.atos.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.time.ZonedDateTime;
import java.util.Date;

import com.atos.demo.model.User;

/**
 * Integration tests for controller (applcation is started with a real dao).
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void canSaveAndRetrieve() {
        // given
		var birthDate = Date.from(ZonedDateTime.now().minusYears(18).minusDays(1).toInstant());
		var user = new User("login1", birthDate, "FR");
        restTemplate.postForEntity("/user", user, User.class);

        // when
        ResponseEntity<User> superHeroResponse = restTemplate.getForEntity("/user/login1", User.class);

        // then
        assertThat(superHeroResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(superHeroResponse.getBody().equals(user));
    }
    
}
