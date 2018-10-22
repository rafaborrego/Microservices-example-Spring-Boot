package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description "Should return a uuid"
    request{
        method GET()
        url("/uuid")
    }
    response {
        status 200
        body("BEGIN-56bb05bf-8f12-4a08-952f-9b2c0317fe11-END")
    }
}
