package com.rafaborrego.uuid.contract;

import com.rafaborrego.uuid.controller.UuidControllerImpl;
import com.rafaborrego.uuid.service.UuidService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Base class for the contract tests on the Producer side.
 * The tests are generated by Spring Cloud in the target folder: generated-test-sources/contracts
 */
public class BaseContractTest {

    private final static String EXPECTED_GENERATED_UUID = "BEGIN-56bb05bf-8f12-4a08-952f-9b2c0317fe11-END";

    @Before
    public void setup() {

        UuidService uuidServiceMock = configureUuidServiceMock();

        RestAssuredMockMvc.standaloneSetup(
                new UuidControllerImpl(uuidServiceMock));
    }

    private UuidService configureUuidServiceMock() {

        UuidService uuidService = mock(UuidService.class);
        when(uuidService.generateUuid(any(), any())).thenReturn(EXPECTED_GENERATED_UUID);

        return uuidService;
    }
}
