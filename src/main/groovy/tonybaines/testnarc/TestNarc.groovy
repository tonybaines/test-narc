package tonybaines.testnarc

class TestNarc {
  def check(pathToProjectRoot) {
    def projectRoot = new File(pathToProjectRoot)
    def productionLoC = findLoC(pathToProjectRoot + '/src/main/java')
    def testLoC = findLoC(pathToProjectRoot + '/src/test/java')
    
    "The ratio of test to production code is ${testLoC/productionLoC}"
  }
  
  def findLoC(String path) {
    def loc = 0
    def files = findJavaFiles(path) 
    files.each { File file ->
      loc += findLoC(file)
    }
    loc
  }
  
  def findLoC(File file) {
    def loc = 0
    file.eachLine { line ->
      if (isCode(line)) loc++
    }
    loc
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
