package org.aa.branch.mapping.unit

import com.google.cloud.datastore.testing.LocalDatastoreHelper
import org.aa.branch.mapping.BranchConnectionBuilder
import org.aa.branch.mapping.BranchMappingController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration(classes = [BranchMappingControllerTestConfiguration, BranchMappingDataStoreTestConfiguration])
class BranchMappingControllerSpec extends Specification {

    @Autowired
    private BranchMappingController branchMappingController

    @Autowired
    private LocalDatastoreHelper localDatastoreHelper

    private validDefaultBranchConnection = new BranchConnectionBuilder()
            .setType("ci")
            .setApplication("beApp")
            .setApplicationBranch("/master")
            .setConnectedBranch("/release")
            .setIsDefault(true)
            .setComment("This is default CI mapping for release branch")
            .createBranchConnection()

    private validBranchConnection = new BranchConnectionBuilder()
            .setType("ci")
            .setApplication("beApp")
            .setApplicationBranch("/feature-branch")
            .setConnectedBranch("/custom-ci")
            .setComment("This is custom mapping for feature branch")
            .createBranchConnection()

    def setup() {
        localDatastoreHelper.reset()
    }

    def "Validate adding default branch of the Application"() {
        when:
        branchMappingController.addBranchConnection(validDefaultBranchConnection);

        and:
        def actualBranchConnections = branchMappingController.getBranchConnections()

        then:
        actualBranchConnections == [validDefaultBranchConnection]
    }

    def "Validate adding the same default branch of the Application"() {
        when:
        branchMappingController.addBranchConnection(validDefaultBranchConnection);
        branchMappingController.addBranchConnection(validDefaultBranchConnection);

        and:
        def actualBranchConnections = branchMappingController.getBranchConnections()

        then:
        actualBranchConnections == [validDefaultBranchConnection]
    }

    def "Validate replacing default branch of the Application"() {
        when:
        branchMappingController.addBranchConnection(validDefaultBranchConnection);

        and:
        validDefaultBranchConnection.setConnectedBranch("/RC/feature")
        branchMappingController.addBranchConnection(validDefaultBranchConnection);

        and:
        def actualBranchConnections = branchMappingController.getBranchConnections()

        then:
        actualBranchConnections == [validDefaultBranchConnection]
    }

    def "Validate adding not default branch Application without default Branch"() {
        when:
        branchMappingController.addBranchConnection(validBranchConnection);

        and:
        def actualBranchConnections = branchMappingController.getBranchConnections()

        then:
        actualBranchConnections.isEmpty()
    }
}
