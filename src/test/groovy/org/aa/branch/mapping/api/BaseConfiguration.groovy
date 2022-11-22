package org.aa.branch.mapping.api

import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.aa.branch.mapping.api.utils.EnvironmentUtils
import spock.lang.Specification

abstract class BaseConfiguration extends Specification {

    protected static RequestSpecification getBaseRequestSpec() {
        def spec = new RequestSpecBuilder()
                .setBaseUri(EnvironmentUtils.appUrl.toURI())
                .setRelaxedHTTPSValidation()
                .build()

        RestAssured.given(spec)
    }
}
