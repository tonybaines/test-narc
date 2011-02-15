package tonybaines.testnarc

import org.apache.log4j.*

import com.bt.buildstatscollector.service.*


public class Execute {	
    static def DEFAULT_CONFIG_FILE_PATH = "./build-stats-collector.config"
    static def LOG_FILE_NAME = "./build-stats-collector.log"
  
    public static void main(String[] args) {
        def cli = new CliBuilder(usage: ' -p "path/to/project/root" [-v] [-t]')
        def version = Version.VERSION
        cli.p(argName:'path', args:1, required:true, 'Path to project root')
        cli.v(argName:'verbose', longOpt: 'verbose', required:false, 'Verbose diagnostic messages')
        cli.t(argName:'trace', longOpt: 'trace', required:false, 'Verbose logging messages')
    
        def startTime = Calendar.instance.timeInMillis
        def opt = cli.parse(args)
        if (opt) {
            RollingFileAppender logFileAppender = new RollingFileAppender(LogInit.DEFAULT_LAYOUT, LOG_FILE_NAME, true)
            logFileAppender.setMaxFileSize("1MB")
            logFileAppender.maxBackupIndex = 1
      
            def consoleLogLevel = opt.v ? Level.DEBUG : Level.INFO
            def fileLogLevel = opt.t ? Level.DEBUG : Level.INFO
      
            LogInit.initialise(appenders: [[LogInit.DEFAULT_CONSOLE_APPENDER, consoleLogLevel],
                    [logFileAppender, fileLogLevel]])
                                     
            def log = Logger.getLogger("MAIN")
            log.info "You are running application version ${Version.VERSION}"
      
            try {
                def path = new File(opt.p)
        
                if (path.exists()) {
                    

                    TestNarc testNarc = new TestNarc()
					testNarc.check(path).each {
						println it
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
            }
        }
    }
}
