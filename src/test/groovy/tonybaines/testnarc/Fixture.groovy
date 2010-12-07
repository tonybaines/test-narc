package tonybaines.testnarc

class Fixture {
  static final File ROOT_DIR = new File('.')
  static final File TEST_PROJ_ROOT_DIR = new File(ROOT_DIR.absolutePath + '/build/test-project')
  
  def buildDefaultProject() {
    TEST_PROJ_ROOT_DIR.mkdirs()
    ['/src/main/java', '/src/test/java'].each {
      new File(TEST_PROJ_ROOT_DIR.absolutePath + it).mkdirs()
    }
    addMainClass()
    addTestClass()
  }
  
  def addMainClass() {
    copyFile('/src/test/resources/TemplateJavaProd.java', '/src/main/java/Prod1.java')
  }
  def addTestClass() {
    copyFile('/src/test/resources/TemplateJavaJUnit4.java', '/src/test/java/Prod1Test.java')
  }
  
  def copyFile(from, to) {
    new File(TEST_PROJ_ROOT_DIR.absolutePath+to).withWriter { file ->
        new File(ROOT_DIR.absolutePath+from).eachLine { line ->
            file.writeLine( line )
        }
    }
    
  }
}
