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
      Collection result = testnarc.check(TEST_PROJ_ROOT_DIR.absolutePath)
    then: "The number of lines of code for the production and test classes are returned"
      result.contains('The ratio of test to production code is 3.40')
      result.contains('The ratio of assertions to lines of test code is 0.2941')
      result.contains('The number of useless assertions e.g. assertTrue(true) is 2')
      result.contains('The number of not-null assertions is 1')
  }
}