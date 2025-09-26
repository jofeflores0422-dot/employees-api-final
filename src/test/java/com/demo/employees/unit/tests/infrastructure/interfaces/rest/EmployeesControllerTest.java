package com.demo.employees.unit.tests.infrastructure.interfaces.rest;

import com.demo.employees.domain.model.Gender;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeePatchDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeRequestDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeResponseDTO;
import com.demo.employees.infrastructure.adapters.inbound.handlers.employee.EmployeeCommandHandler;
import com.demo.employees.infrastructure.config.SecurityConfig;
import com.demo.employees.infrastructure.interfaces.rest.EmployeesController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeesController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = "security.jwt.secret=test-secret-32-bytes-minimo-para-hmac........")
class EmployeesControllerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper objectMapper;

  @MockBean EmployeeCommandHandler handler;

  private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");

  private static EmployeeRequestDTO validCreateReq(String fn, String ln) {
    var dto = new EmployeeRequestDTO();
    dto.setFirstName(fn);
    dto.setMiddleName("María");
    dto.setLastName(ln);
    dto.setSecondLastName("López");
    dto.setAge(25);
    dto.setGender(Gender.F);
    dto.setBirthDate(LocalDate.of(2000, 1, 15));
    dto.setPosition("Developer");
    dto.setActive(true);
    return dto;
  }

  private static EmployeeRequestDTO validUpdateReq() {
    var dto = validCreateReq("Mario", "Rossi");
    dto.setAge(31);
    return dto;
  }

  private static EmployeePatchDTO validPatch() {
    var dto = new EmployeePatchDTO();
    dto.setPosition("Lead Developer");
    dto.setActive(true);
    return dto;
  }

  private static EmployeeResponseDTO fullResponse(long id, String fn, String ln) {
    var dto = new EmployeeResponseDTO();
    dto.setId(id);
    dto.setFirstName(fn);
    dto.setMiddleName("María");
    dto.setLastName(ln);
    dto.setSecondLastName("López");
    dto.setAge(25);
    dto.setGender(Gender.F);
    dto.setBirthDate(LocalDate.of(2000, 1, 15));
    dto.setPosition("Developer");
    dto.setActive(true);
    return dto;
  }

  @Test
  @DisplayName("GET /employees – 200 con scope employees.read")
  void getAll_ok_with_read_scope() throws Exception {
    var e = fullResponse(1L, "John", "Doe");
    when(handler.getAll()).thenReturn(List.of(e));

    mvc.perform(get("/employees")
                    .with(jwt().jwt(j -> j.claim("scope", "employees.read")))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].firstName").value("John"));
  }

  @Test
  @DisplayName("GET /employees/{id} – 200 con scope employees.read")
  void getById_ok_with_read_scope() throws Exception {
    when(handler.getById(5L)).thenReturn(fullResponse(5L, "Ana", "Doe"));

    mvc.perform(get("/employees/{id}", 5)
                    .with(jwt().jwt(j -> j.claim("scope", "employees.read")))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(5))
            .andExpect(jsonPath("$.firstName").value("Ana"));
  }

  @Test
  @DisplayName("POST /employees – 201 con scope employees.write")
  void create_201_with_write_scope() throws Exception {
    var req = validCreateReq("Ana", "Doe");
    var created = fullResponse(10L, "Ana", "Doe");

    when(handler.create(anyList())).thenReturn(List.of(created));

    mvc.perform(post("/employees")
                    .with(jwt().jwt(j -> j.claim("scope", "employees.write")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(List.of(req)))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/employees")))
            .andExpect(jsonPath("$[0].id").value(10))
            .andExpect(jsonPath("$[0].firstName").value("Ana"));
  }

  @Test
  @DisplayName("PUT /employees/{id} – 200 con scope employees.write")
  void update_put_ok_with_write_scope() throws Exception {
    var req = validUpdateReq();
    var updated = fullResponse(5L, "Mario", "Rossi");
    updated.setAge(31);

    when(handler.update(eq(5L), any(EmployeeRequestDTO.class))).thenReturn(updated);

    mvc.perform(put("/employees/{id}", 5)
                    .with(jwt().jwt(j -> j.claim("scope", "employees.write")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(5))
            .andExpect(jsonPath("$.firstName").value("Mario"))
            .andExpect(jsonPath("$.lastName").value("Rossi"));
  }

  @Test
  @DisplayName("PATCH /employees/{id} – 200 con scope employees.write")
  void patch_ok_with_write_scope() throws Exception {
    var patched = fullResponse(7L, "Ana", "Doe");
    patched.setPosition("Lead Developer");
    patched.setActive(true);

    when(handler.updatePartial(eq(7L), any(EmployeePatchDTO.class))).thenReturn(patched);

    mvc.perform(patch("/employees/{id}", 7)
                    .with(jwt().jwt(j -> j.claim("scope", "employees.write")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(validPatch()))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(7))
            .andExpect(jsonPath("$.position").value("Lead Developer"))
            .andExpect(jsonPath("$.active").value(true));
  }

  @Test
  @DisplayName("DELETE /employees/{id} – 204 con scope employees.write")
  void delete_204_with_write_scope() throws Exception {
    mvc.perform(delete("/employees/{id}", 9)
                    .with(jwt().jwt(j -> j.claim("scope", "employees.write"))))
            .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("GET /employees/search?name=... – 200 con scope employees.read")
  void search_ok_with_read_scope() throws Exception {
    when(handler.search("ana")).thenReturn(List.of(fullResponse(1L, "Ana", "Doe")));

    mvc.perform(get("/employees/search")
                    .param("name", "ana")
                    .with(jwt().jwt(j -> j.claim("scope", "employees.read")))
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].firstName").value("Ana"));
  }

  @Nested
  @DisplayName("Seguridad")
  class SecurityCases {
    @Test
    @DisplayName("401 sin token")
    void unauthorized_when_no_token() throws Exception {
      mvc.perform(get("/employees").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("403 con token pero sin scope requerido")
    void forbidden_when_missing_scope() throws Exception {
      mvc.perform(get("/employees")
                      .with(jwt().jwt(j -> j.claim("scope", "other.scope")))
                      .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isForbidden());
    }
  }
}
