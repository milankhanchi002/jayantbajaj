package com.example.bfhl.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetEndpoint() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation_code", is(1)));
    }

    @Test
    public void testExampleA_MixedInput() throws Exception {
        String requestBody = "{\"data\": [\"a\", \"1\", \"334\", \"4\", \"R\", \"$\"]}";

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("jayant_chugh_21122004")))
                .andExpect(jsonPath("$.email", is("jayant0450.be23@chitkara.edu.in")))
                .andExpect(jsonPath("$.roll_number", is("2310990450")))
                .andExpect(jsonPath("$.odd_numbers", contains("1")))
                .andExpect(jsonPath("$.even_numbers", contains("334", "4")))
                .andExpect(jsonPath("$.alphabets", contains("A", "R")))
                .andExpect(jsonPath("$.special_characters", contains("$")))
                .andExpect(jsonPath("$.sum", is("339")))
                .andExpect(jsonPath("$.concat_string", is("Ra")));
    }

    @Test
    public void testExampleB_PureAlphabets() throws Exception {
        String requestBody = "{\"data\": [\"A\", \"ABCD\", \"DOE\"]}";

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("jayant_chugh_21122004")))
                .andExpect(jsonPath("$.email", is("jayant0450.be23@chitkara.edu.in")))
                .andExpect(jsonPath("$.roll_number", is("2310990450")))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", is(empty())))
                .andExpect(jsonPath("$.alphabets", contains("A", "ABCD", "DOE")))
                .andExpect(jsonPath("$.special_characters", is(empty())))
                .andExpect(jsonPath("$.sum", is("0")))
                .andExpect(jsonPath("$.concat_string", is("EoDdCbAa")));
    }

    @Test
    public void testExampleC_PureNumbers() throws Exception {
        String requestBody = "{\"data\": [\"2\", \"5\", \"10\", \"15\"]}";

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("jayant_chugh_21122004")))
                .andExpect(jsonPath("$.email", is("jayant0450.be23@chitkara.edu.in")))
                .andExpect(jsonPath("$.roll_number", is("2310990450")))
                .andExpect(jsonPath("$.odd_numbers", contains("5", "15")))
                .andExpect(jsonPath("$.even_numbers", contains("2", "10")))
                .andExpect(jsonPath("$.alphabets", is(empty())))
                .andExpect(jsonPath("$.special_characters", is(empty())))
                .andExpect(jsonPath("$.sum", is("32")))
                .andExpect(jsonPath("$.concat_string", is("")));
    }

    @Test
    public void testNullInputCase() throws Exception {
        String requestBody = "{\"data\": null}";

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.user_id", is("jayant_chugh_21122004")))
                .andExpect(jsonPath("$.email", is("jayant0450.be23@chitkara.edu.in")))
                .andExpect(jsonPath("$.roll_number", is("2310990450")))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", is(empty())))
                .andExpect(jsonPath("$.alphabets", is(empty())))
                .andExpect(jsonPath("$.special_characters", is(empty())))
                .andExpect(jsonPath("$.sum", is("0")))
                .andExpect(jsonPath("$.concat_string", is("")));
    }

    @Test
    public void testCustomMixedInput_Step4() throws Exception {
        String requestBody = "{\"data\": [\"2\",\"a\",\"y\",\"4\",\"&\",\"-\",\"*\",\"5\",\"92\",\"b\"]}";

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("jayant_chugh_21122004")))
                .andExpect(jsonPath("$.email", is("jayant0450.be23@chitkara.edu.in")))
                .andExpect(jsonPath("$.roll_number", is("2310990450")))
                .andExpect(jsonPath("$.odd_numbers", contains("5")))
                .andExpect(jsonPath("$.even_numbers", contains("2", "4", "92")))
                .andExpect(jsonPath("$.alphabets", contains("A", "Y", "B")))
                .andExpect(jsonPath("$.special_characters", contains("&", "-", "*")))
                .andExpect(jsonPath("$.sum", is("103")))
                .andExpect(jsonPath("$.concat_string", is("ByA")));
    }

    @Test
    public void testHugeNumberEdgeCase() throws Exception {
        String requestBody = "{\"data\": [\"1000000000000000000000000000\"]}";

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("jayant_chugh_21122004")))
                .andExpect(jsonPath("$.email", is("jayant0450.be23@chitkara.edu.in")))
                .andExpect(jsonPath("$.roll_number", is("2310990450")))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", contains("1000000000000000000000000000")))
                .andExpect(jsonPath("$.alphabets", is(empty())))
                .andExpect(jsonPath("$.special_characters", is(empty())))
                .andExpect(jsonPath("$.sum", is("1000000000000000000000000000")))
                .andExpect(jsonPath("$.concat_string", is("")));
    }

    @Test
    public void testInvalidJsonInputCase() throws Exception {
        String requestBody = "{\"data\": [invalid_json_without_quotes]}";

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.user_id", is("jayant_chugh_21122004")))
                .andExpect(jsonPath("$.email", is("jayant0450.be23@chitkara.edu.in")))
                .andExpect(jsonPath("$.roll_number", is("2310990450")));
    }

    @Test
    public void testEmptyArrayCase() throws Exception {
        String requestBody = "{\"data\": []}";

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("jayant_chugh_21122004")))
                .andExpect(jsonPath("$.email", is("jayant0450.be23@chitkara.edu.in")))
                .andExpect(jsonPath("$.roll_number", is("2310990450")))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", is(empty())))
                .andExpect(jsonPath("$.alphabets", is(empty())))
                .andExpect(jsonPath("$.special_characters", is(empty())))
                .andExpect(jsonPath("$.sum", is("0")))
                .andExpect(jsonPath("$.concat_string", is("")));
    }

    @Test
    public void testOnlySpecialCharactersCase() throws Exception {
        String requestBody = "{\"data\": [\"$\", \"#\", \"@\"]}";

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("jayant_chugh_21122004")))
                .andExpect(jsonPath("$.email", is("jayant0450.be23@chitkara.edu.in")))
                .andExpect(jsonPath("$.roll_number", is("2310990450")))
                .andExpect(jsonPath("$.odd_numbers", is(empty())))
                .andExpect(jsonPath("$.even_numbers", is(empty())))
                .andExpect(jsonPath("$.alphabets", is(empty())))
                .andExpect(jsonPath("$.special_characters", contains("$", "#", "@")))
                .andExpect(jsonPath("$.sum", is("0")))
                .andExpect(jsonPath("$.concat_string", is("")));
    }
}

