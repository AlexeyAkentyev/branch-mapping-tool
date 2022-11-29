package org.aa.branch.mapping.unit

import com.google.cloud.datastore.testing.LocalDatastoreHelper
import org.aa.branch.mapping.BranchConnection
import org.aa.branch.mapping.BranchConnectionBuilder
import org.aa.branch.mapping.BranchMappingController
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@EnableSharedInjection
@ContextConfiguration(classes = [BranchMappingControllerTestConfiguration, BranchMappingDataStoreTestConfiguration])
class BranchMappingControllerSpec extends Specification {

    @Autowired
    private BranchMappingController branchMappingController

    @Shared
    @Autowired
    private LocalDatastoreHelper localDatastoreHelper

    private final BranchConnection validDefaultBranchMapping = new BranchConnectionBuilder()
            .setType('ci')
            .setApplication('beApp')
            .setApplicationBranch('/master')
            .setConnectedBranch('/release')
            .setIsDefault(true)
            .setComment('This is default CI mapping for release branch')
            .createBranchConnection()

    private final BranchConnection validBranchMapping = new BranchConnectionBuilder()
            .setType('ci')
            .setApplication('beApp')
            .setApplicationBranch('/feature-branch')
            .setConnectedBranch('/custom-ci')
            .setComment('This is custom mapping for feature branch')
            .createBranchConnection()

    def setup() {
        localDatastoreHelper.reset()
    }

    def 'Validate adding default branch of the Application'() {
        when:
        branchMappingController.addBranchConnection(validDefaultBranchMapping)

        and:
        def actualBranchConnections = branchMappingController.branchConnections

        then:
        actualBranchConnections == [validDefaultBranchMapping]
    }

    def 'Validate adding the same default branch of the Application'() {
        when:
        branchMappingController.addBranchConnection(validDefaultBranchMapping)
        branchMappingController.addBranchConnection(validDefaultBranchMapping)

        and:
        def actualBranchConnections = branchMappingController.branchConnections

        then:
        actualBranchConnections == [validDefaultBranchMapping]
    }

    def 'Validate replacing default branch of the Application'() {
        when:
        branchMappingController.addBranchConnection(validDefaultBranchMapping)

        and:
        validDefaultBranchMapping.connectedBranch = '/RC/feature'
        branchMappingController.addBranchConnection(validDefaultBranchMapping)

        and:
        def actualBranchConnections = branchMappingController.branchConnections

        then:
        actualBranchConnections == [validDefaultBranchMapping]
    }

    def 'Validate adding not default branch Application without default Branch'() {
        when:
        branchMappingController.addBranchConnection(validBranchMapping)

        and:
        def actualBranchConnections = branchMappingController.branchConnections

        then:
        actualBranchConnections.empty
    }

    void cleanupSpec() {
        localDatastoreHelper.stop()
    }
}
