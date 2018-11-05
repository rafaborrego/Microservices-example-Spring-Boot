package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description "Should return a ${domainCamelCase}"
    request{
        method GET()
        url("/${domain}")
    }
    response {
        status 200
        body("")
    }
}