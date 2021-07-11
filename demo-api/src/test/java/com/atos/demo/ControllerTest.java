package com.atos.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.atos.demo.api.UserController;
import com.atos.demo.model.User;
import com.atos.demo.port.IDao;

/**
 * Unit tests for controller (dao is mocked).
 */
@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
public class ControllerTest {

	@Autowired
	private MockMvc mvc;

    @MockBean
    private IDao dao;
    
    @Autowired
    private JacksonTester<User> jsonUser;
    
    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        given(dao.getUser("login1"))
                .willReturn(Optional.of(new User("login1")));

        // when
        MockHttpServletResponse response = mvc.perform(get("/user/login1")
        		.accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonUser.write(new User("login1")).getJson()
        );
    }
    
    @Test
    public void cantRetrieveByIdWhenNotExists() throws Exception {
        // given
        given(dao.getUser("login1"))
                .willReturn(Optional.empty());

        // when
        MockHttpServletResponse response = mvc.perform(get("/user/login1")
        		.accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    public void canSave() throws Exception {
        // given
        given(dao.saveUser(Mockito.any(User.class)))
                .willReturn(new User());

        // when
		var birthDate = Date.from(ZonedDateTime.now().minusYears(18).minusDays(1).toInstant());
		var user = new User("login1", birthDate, "FR");
        MockHttpServletResponse response = mvc.perform(post("/user")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(
                        jsonUser.write(user).getJson()
                ))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
    
    @Test
    public void cantSaveInvalidBirthDate() throws Exception {
        // given
        given(dao.saveUser(Mockito.any(User.class)))
                .willReturn(new User());

        // when
		var birthDate = Date.from(ZonedDateTime.now().minusYears(18).plusDays(1).toInstant());
		var user = new User("login1", birthDate, "FR");
        MockHttpServletResponse response = mvc.perform(post("/user")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(
                        jsonUser.write(user).getJson()
                ))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void cantSaveNullBirthDate() throws Exception {
        // given
        given(dao.saveUser(Mockito.any(User.class)))
                .willReturn(new User());

        // when
		var user = new User("login1", null, "FR");
        MockHttpServletResponse response = mvc.perform(post("/user")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(
                        jsonUser.write(user).getJson()
                ))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void cantSaveBadCountryCode() throws Exception {
        // given
        given(dao.saveUser(Mockito.any(User.class)))
                .willReturn(new User());

        // when
		var birthDate = Date.from(ZonedDateTime.now().minusYears(18).minusDays(1).toInstant());
		var user = new User("login1", birthDate, "FRA");
        MockHttpServletResponse response = mvc.perform(post("/user")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(
                        jsonUser.write(user).getJson()
                ))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString().contains("\"countryCode\":\"User's country is not France\""));
    }
    
    @Test
    public void canSaveWithParam() throws Exception {
        // given
        given(dao.saveUser(Mockito.any(User.class)))
                .willReturn(new User());

        // when
		var birthDate = Date.from(ZonedDateTime.now().minusYears(18).minusDays(1).toInstant());
		var user = new User("login1", birthDate, "FR");
        MockHttpServletResponse response = mvc.perform(post("/user?simulation=true")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(
                        jsonUser.write(user).getJson()
                ))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }
}