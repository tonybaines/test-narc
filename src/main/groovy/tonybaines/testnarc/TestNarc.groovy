package tonybaines.testnarc

class TestNarc {
  def check(pathToProjectRoot) {
    def projectRoot = new File(pathToProjectRoot)
    def productionLoC = findLoC(pathToProjectRoot + '/src/main/java')
    def testLoC = findLoC(pathToProjectRoot + '/src/test/java')
    "The ratio of test to production code is ${testLoC/productionLoC}"
  }
  
  def findLoC(path) {
    def loc = 0
    def files = findFiles(path)
    files.each { File file ->
      file.eachLine { 
        if (it != '' && !it.trim().startsWith('//')) loc++ 
      }
    }
    loc
  }
  
  private def findFiles(path) {
    def files = []
    File root = new File(path)
    root.eachFileRecurse { File it ->
      if (it.isFile()) {
        files << it
      }
    }
    files
  }
}
