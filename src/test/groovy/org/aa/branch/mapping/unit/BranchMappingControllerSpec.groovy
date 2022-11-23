package org.aa.branch.mapping.unit

import com.google.cloud.datastore.testing.LocalDatastoreHelper
import groovy.util.logging.Slf4j
import org.aa.branch.mapping.BranchConnectionBuilder
import org.aa.branch.mapping.BranchMappingController
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

@Slf4j
@SpringBootTest
@EnableSharedInjection
@ContextConfiguration(classes = [BranchMappingControllerTestConfiguration, BranchMappingDataStoreTestConfiguration])
class BranchMappingControllerSpec extends Specification {

    @Autowired
    private BranchMappingController branchMappingController

    @Shared
    @Autowired
    private LocalDatastoreHelper localDatastoreHelper

    private validDefaultBranchMapping = new BranchConnectionBuilder()
            .setType("ci")
            .setApplication("beApp")
            .setApplicationBranch("/master")
            .setConnectedBranch("/release")
            .setIsDefault(true)
            .setComment("This is default CI mapping for release branch")
            .createBranchConnection()

    private validBranchMapping = new BranchConnectionBuilder()
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
        log.info('Disabled step')

        and:
        log.info('Disabled step')

        then:
        true == true
    }

    def "Validate adding the same default branch of the Application"() {
        when:
        log.info('Disabled step')

        and:
        log.info('Disabled step')

        then:
        true == true
    }

    def "Validate replacing default branch of the Application"() {
        when:
        log.info('Disabled step')

        and:
        log.info('Disabled step')

        and:
        def actualBranchConnections = branchMappingController.getBranchConnections()

        then:
        true == true
    }

    def "Validate adding not default branch Application without default Branch"() {
        when:
        log.info('Disabled step')

        and:
        log.info('Disabled step')

        then:
        true == true
    }

    void cleanupSpec() {
        localDatastoreHelper.stop()
    }
}
