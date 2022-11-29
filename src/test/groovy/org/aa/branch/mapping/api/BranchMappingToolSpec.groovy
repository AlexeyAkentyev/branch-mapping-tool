package org.aa.branch.mapping.api

import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.BaseProducer
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.aa.branch.mapping.BranchConnection
import org.aa.branch.mapping.BranchConnectionBuilder
import org.apache.http.HttpStatus

class BranchMappingToolSpec extends BaseConfiguration {
    private static final String CREATE_UPDATE_REQUEST_PATH = '/api/v1/branch-connection'

    private final BaseProducer producer = Fairy.create().baseProducer()
    private final String type = producer.letterify('type-????')
    private final String application = producer.letterify('Application-?????')

    private final RequestSpecification requestSpec = baseRequestSpec

    private final BranchConnection validDefaultBranchMapping = new BranchConnectionBuilder()
            .setType(type)
            .setApplication(application)
            .setApplicationBranch('/master')
            .setConnectedBranch('/release')
            .setIsDefault(true)
            .setComment('This is default CI mapping for release branch')
            .createBranchConnection()

    private final BranchConnection validBranchMapping = new BranchConnectionBuilder()
            .setType(producer.letterify('test-type-????'))
            .setApplication(producer.letterify('test-Application-?????'))
            .setApplicationBranch('/master')
            .setConnectedBranch('/release')
            .setIsDefault(false)
            .setComment('This is default CI mapping for release branch')
            .createBranchConnection()

    def 'Create new default branch mapping'() {
        when: 'call for mapping creation request'
        def response = requestSpec
                .contentType(ContentType.JSON)
                .body(validDefaultBranchMapping)
                .when()
                .post(CREATE_UPDATE_REQUEST_PATH)

        then: 'gets successful response'
        response.statusCode == HttpStatus.SC_OK
    }

    def 'User is not able to create mapping without default branch'() {
        when: 'call for mapping creation request'
        def response = requestSpec
                .contentType(ContentType.JSON)
                .body(validBranchMapping)
                .when()
                .post(CREATE_UPDATE_REQUEST_PATH)

        then: 'gets successful response'
        response.statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR
        response.body().asString() == "There is no default branch for application $validBranchMapping.application with connection type $validBranchMapping.type"
    }
}
