package tonybaines.testnarc

import groovy.lang.Mixin;
import spock.lang.Ignore;
import spock.lang.Specification 

@Mixin(Fixture)
class AcceptanceSpec extends Specification {
  def "should produce a result that includes totals for LoC in test and production classes"() {
    given: "A project layout that matches the Maven convention"
      buildDefaultProject()
      def testnarc = new TestNarc()
    when: "The root of the project is analysed"
      def result = testnarc.check(TEST_PROJ_ROOT_DIR.absolutePath)
    then: "The number of lines of code for the production and test classes are returned"
      result.contains("The ratio of test to production code is 2.4")
  }
}