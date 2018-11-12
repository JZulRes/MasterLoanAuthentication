package io.github.jhipster.masterloanauthentication.web.rest;

import io.github.jhipster.masterloanauthentication.MasterLoanAuthenticationApp;

import io.github.jhipster.masterloanauthentication.domain.Userloan;
import io.github.jhipster.masterloanauthentication.repository.UserloanRepository;
import io.github.jhipster.masterloanauthentication.repository.search.UserloanSearchRepository;
import io.github.jhipster.masterloanauthentication.service.UserloanService;
import io.github.jhipster.masterloanauthentication.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.masterloanauthentication.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserloanResource REST controller.
 *
 * @see UserloanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MasterLoanAuthenticationApp.class)
public class UserloanResourceIntTest {

    private static final Long DEFAULT_CEDULA_CUSTOMER = 1L;
    private static final Long UPDATED_CEDULA_CUSTOMER = 2L;

    private static final String DEFAULT_TYPE_ID_CUSTOMER = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_ID_CUSTOMER = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private UserloanRepository userloanRepository;

    @Autowired
    private UserloanService userloanService;

    /**
     * This repository is mocked in the io.github.jhipster.masterloanauthentication.repository.search test package.
     *
     * @see io.github.jhipster.masterloanauthentication.repository.search.UserloanSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserloanSearchRepository mockUserloanSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserloanMockMvc;

    private Userloan userloan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserloanResource userloanResource = new UserloanResource(userloanService);
        this.restUserloanMockMvc = MockMvcBuilders.standaloneSetup(userloanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Userloan createEntity(EntityManager em) {
        Userloan userloan = new Userloan()
            .cedulaCustomer(DEFAULT_CEDULA_CUSTOMER)
            .typeIdCustomer(DEFAULT_TYPE_ID_CUSTOMER)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD);
        return userloan;
    }

    @Before
    public void initTest() {
        userloan = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserloan() throws Exception {
        int databaseSizeBeforeCreate = userloanRepository.findAll().size();

        // Create the Userloan
        restUserloanMockMvc.perform(post("/api/userloans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userloan)))
            .andExpect(status().isCreated());

        // Validate the Userloan in the database
        List<Userloan> userloanList = userloanRepository.findAll();
        assertThat(userloanList).hasSize(databaseSizeBeforeCreate + 1);
        Userloan testUserloan = userloanList.get(userloanList.size() - 1);
        assertThat(testUserloan.getCedulaCustomer()).isEqualTo(DEFAULT_CEDULA_CUSTOMER);
        assertThat(testUserloan.getTypeIdCustomer()).isEqualTo(DEFAULT_TYPE_ID_CUSTOMER);
        assertThat(testUserloan.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserloan.getPassword()).isEqualTo(DEFAULT_PASSWORD);

        // Validate the Userloan in Elasticsearch
        verify(mockUserloanSearchRepository, times(1)).save(testUserloan);
    }

    @Test
    @Transactional
    public void createUserloanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userloanRepository.findAll().size();

        // Create the Userloan with an existing ID
        userloan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserloanMockMvc.perform(post("/api/userloans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userloan)))
            .andExpect(status().isBadRequest());

        // Validate the Userloan in the database
        List<Userloan> userloanList = userloanRepository.findAll();
        assertThat(userloanList).hasSize(databaseSizeBeforeCreate);

        // Validate the Userloan in Elasticsearch
        verify(mockUserloanSearchRepository, times(0)).save(userloan);
    }

    @Test
    @Transactional
    public void checkCedulaCustomerIsRequired() throws Exception {
        int databaseSizeBeforeTest = userloanRepository.findAll().size();
        // set the field null
        userloan.setCedulaCustomer(null);

        // Create the Userloan, which fails.

        restUserloanMockMvc.perform(post("/api/userloans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userloan)))
            .andExpect(status().isBadRequest());

        List<Userloan> userloanList = userloanRepository.findAll();
        assertThat(userloanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIdCustomerIsRequired() throws Exception {
        int databaseSizeBeforeTest = userloanRepository.findAll().size();
        // set the field null
        userloan.setTypeIdCustomer(null);

        // Create the Userloan, which fails.

        restUserloanMockMvc.perform(post("/api/userloans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userloan)))
            .andExpect(status().isBadRequest());

        List<Userloan> userloanList = userloanRepository.findAll();
        assertThat(userloanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userloanRepository.findAll().size();
        // set the field null
        userloan.setUserName(null);

        // Create the Userloan, which fails.

        restUserloanMockMvc.perform(post("/api/userloans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userloan)))
            .andExpect(status().isBadRequest());

        List<Userloan> userloanList = userloanRepository.findAll();
        assertThat(userloanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = userloanRepository.findAll().size();
        // set the field null
        userloan.setPassword(null);

        // Create the Userloan, which fails.

        restUserloanMockMvc.perform(post("/api/userloans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userloan)))
            .andExpect(status().isBadRequest());

        List<Userloan> userloanList = userloanRepository.findAll();
        assertThat(userloanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserloans() throws Exception {
        // Initialize the database
        userloanRepository.saveAndFlush(userloan);

        // Get all the userloanList
        restUserloanMockMvc.perform(get("/api/userloans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userloan.getId().intValue())))
            .andExpect(jsonPath("$.[*].cedulaCustomer").value(hasItem(DEFAULT_CEDULA_CUSTOMER.intValue())))
            .andExpect(jsonPath("$.[*].typeIdCustomer").value(hasItem(DEFAULT_TYPE_ID_CUSTOMER.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    
    @Test
    @Transactional
    public void getUserloan() throws Exception {
        // Initialize the database
        userloanRepository.saveAndFlush(userloan);

        // Get the userloan
        restUserloanMockMvc.perform(get("/api/userloans/{id}", userloan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userloan.getId().intValue()))
            .andExpect(jsonPath("$.cedulaCustomer").value(DEFAULT_CEDULA_CUSTOMER.intValue()))
            .andExpect(jsonPath("$.typeIdCustomer").value(DEFAULT_TYPE_ID_CUSTOMER.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserloan() throws Exception {
        // Get the userloan
        restUserloanMockMvc.perform(get("/api/userloans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserloan() throws Exception {
        // Initialize the database
        userloanService.save(userloan);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUserloanSearchRepository);

        int databaseSizeBeforeUpdate = userloanRepository.findAll().size();

        // Update the userloan
        Userloan updatedUserloan = userloanRepository.findById(userloan.getId()).get();
        // Disconnect from session so that the updates on updatedUserloan are not directly saved in db
        em.detach(updatedUserloan);
        updatedUserloan
            .cedulaCustomer(UPDATED_CEDULA_CUSTOMER)
            .typeIdCustomer(UPDATED_TYPE_ID_CUSTOMER)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD);

        restUserloanMockMvc.perform(put("/api/userloans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserloan)))
            .andExpect(status().isOk());

        // Validate the Userloan in the database
        List<Userloan> userloanList = userloanRepository.findAll();
        assertThat(userloanList).hasSize(databaseSizeBeforeUpdate);
        Userloan testUserloan = userloanList.get(userloanList.size() - 1);
        assertThat(testUserloan.getCedulaCustomer()).isEqualTo(UPDATED_CEDULA_CUSTOMER);
        assertThat(testUserloan.getTypeIdCustomer()).isEqualTo(UPDATED_TYPE_ID_CUSTOMER);
        assertThat(testUserloan.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserloan.getPassword()).isEqualTo(UPDATED_PASSWORD);

        // Validate the Userloan in Elasticsearch
        verify(mockUserloanSearchRepository, times(1)).save(testUserloan);
    }

    @Test
    @Transactional
    public void updateNonExistingUserloan() throws Exception {
        int databaseSizeBeforeUpdate = userloanRepository.findAll().size();

        // Create the Userloan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserloanMockMvc.perform(put("/api/userloans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userloan)))
            .andExpect(status().isBadRequest());

        // Validate the Userloan in the database
        List<Userloan> userloanList = userloanRepository.findAll();
        assertThat(userloanList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Userloan in Elasticsearch
        verify(mockUserloanSearchRepository, times(0)).save(userloan);
    }

    @Test
    @Transactional
    public void deleteUserloan() throws Exception {
        // Initialize the database
        userloanService.save(userloan);

        int databaseSizeBeforeDelete = userloanRepository.findAll().size();

        // Get the userloan
        restUserloanMockMvc.perform(delete("/api/userloans/{id}", userloan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Userloan> userloanList = userloanRepository.findAll();
        assertThat(userloanList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Userloan in Elasticsearch
        verify(mockUserloanSearchRepository, times(1)).deleteById(userloan.getId());
    }

    @Test
    @Transactional
    public void searchUserloan() throws Exception {
        // Initialize the database
        userloanService.save(userloan);
        when(mockUserloanSearchRepository.search(queryStringQuery("id:" + userloan.getId())))
            .thenReturn(Collections.singletonList(userloan));
        // Search the userloan
        restUserloanMockMvc.perform(get("/api/_search/userloans?query=id:" + userloan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userloan.getId().intValue())))
            .andExpect(jsonPath("$.[*].cedulaCustomer").value(hasItem(DEFAULT_CEDULA_CUSTOMER.intValue())))
            .andExpect(jsonPath("$.[*].typeIdCustomer").value(hasItem(DEFAULT_TYPE_ID_CUSTOMER)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Userloan.class);
        Userloan userloan1 = new Userloan();
        userloan1.setId(1L);
        Userloan userloan2 = new Userloan();
        userloan2.setId(userloan1.getId());
        assertThat(userloan1).isEqualTo(userloan2);
        userloan2.setId(2L);
        assertThat(userloan1).isNotEqualTo(userloan2);
        userloan1.setId(null);
        assertThat(userloan1).isNotEqualTo(userloan2);
    }
}
