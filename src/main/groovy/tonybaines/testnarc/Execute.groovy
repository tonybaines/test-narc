package tonybaines.testnarc

import org.apache.log4j.*

import com.bt.buildstatscollector.service.*


public class Execute {	
    static def LOG_FILE_NAME = "./test-narc.log"
  
    public static void main(String[] args) {
        def cli = new CliBuilder(usage: ' -p "path/to/project/root" [-v]')
        def version = Version.VERSION
        cli.p(argName:'path', args:1, required:true, 'Path to project root')
        cli.v(argName:'verbose', longOpt: 'verbose', required:false, 'Verbose diagnostic messages')
    
        def startTime = Calendar.instance.timeInMillis
        def opt = cli.parse(args)
        if (opt) {      
            def consoleLogLevel = opt.v ? Level.DEBUG : Level.INFO
      
            LogInit.initialise(appenders: [[LogInit.DEFAULT_CONSOLE_APPENDER, consoleLogLevel]])
                                     
            def log = Logger.getLogger("MAIN")
            log.info "Running application version ${Version.VERSION}"
      
            try {
                def path = new File(opt.p)
        
                if (path.exists()) {
                    TestNarc testNarc = new TestNarc()
					testNarc.check(path.absolutePath).each {
						log.info it
					}
                }
                else {
                    log.error "The specified config file doesn't exist: ${path.absolutePath}"
                    cli.usage()
                    System.exit(1)
                }
        
                def runtime = Calendar.instance.timeInMillis - startTime
                log.info "Completed in ${runtime/1000}s"
            }
            catch (Throwable e) {
                log.error("There was a problem running the process: ${e.message}")
				log.debug("Stacktrace:", e)
            }
        }
    }
}
