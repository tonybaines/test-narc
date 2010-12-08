package tonybaines.testnarc

class TestNarc {
  def check(pathToProjectRoot) {
    def files = findJavaFiles(pathToProjectRoot)
    def testFiles = files.grep { it.name.contains 'Test'}
    def productionFiles = files - testFiles
    def productionLoC = countLoCIn(productionFiles)
    def testLoC = countLoCIn(testFiles)
    def testAssertions = findAssertionsIn(testFiles)
    def badAssertions = testAssertions.grep {
      it.contains('assertTrue(true)') ||
      it.contains('assertFalse(false)')
    }
    def notNullAssertions = testAssertions.grep { it.contains('assertNotNull') }
    def usefulAssertions = testAssertions - badAssertions - notNullAssertions
    
    [sprintf("The ratio of test to production code is %.2f", (testLoC/productionLoC))
    ,sprintf("The ratio of assertions to lines of test code is %.4f", (testAssertions.size()/testLoC))
    ,sprintf("The number of useless assertions e.g. assertTrue(true) is %d", badAssertions.size())
    ,sprintf("The number of not-null assertions is %d", notNullAssertions.size())
    ,sprintf("The number of useful assertions is %d", usefulAssertions.size())
    ,sprintf("The ratio of assertions to lines of test code with not-null and useless assertions removed is %.4f", (usefulAssertions.size()/testLoC))]
  }
  
  def countLoCIn(files) {
    def loc = 0
    files.each { File file ->
      file.eachLine { line ->
        if (isCode(line)) loc++
      }
    }
    loc
  }
  
  def findAssertionsIn(files) {
    def assertions = []
    files.each { File file ->
      file.eachLine { line -> 
         if (line.contains('assert')) assertions << line.trim()
      }
    }
    assertions
  }
  
  private def isCode(line) { (line.trim() != '' && isNotAComment(line)) }
  private def isNotAComment(line) { !(line.trim().startsWith('//') || line.trim().startsWith('*') || line.trim().startsWith('/*')) }
  
  private def findJavaFiles(path) { findFiles(path) {it.name.endsWith('.java')} }
  private def findFiles(path, criteria) {
    def files = []
    File root = new File(path)
    root.eachFileRecurse { File it ->
      if (it.isFile() && criteria.call(it)) {
        files << it
      }
    }
    files
  }
}
