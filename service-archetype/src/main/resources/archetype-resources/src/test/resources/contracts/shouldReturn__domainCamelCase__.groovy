package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    description "Should return ${domain}s"
    request{
        method GET()
        url("/${domain}s")
    }
    response {
        status 200
        body("""
            {
              "${domain}s": []
            }
            """)
    }
}
