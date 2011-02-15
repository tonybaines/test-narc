package tonybaines.testnarc

import org.apache.log4j.*

@Singleton
class LogInit {
  public static def DEFAULT_LAYOUT = new org.apache.log4j.PatternLayout('[%d{yyyy-MM-dd hh:mm:ss:sss z}] %5p %c{2}: %m%n')
  public static def DEFAULT_CONSOLE_APPENDER =  new org.apache.log4j.ConsoleAppender(DEFAULT_LAYOUT)
  static def DEFAULT_LEVEL = Level.INFO
  
  static def initialise(params = [:]) {
    if (params.appenders) {
      params.appenders.each { defn ->
        def (appender, level) = defn
        appender.threshold = level
        LogManager.rootLogger.addAppender appender
      }
    }
    else {
      BasicConfigurator.configure params.appender ?: DEFAULT_CONSOLE_APPENDER
      DEFAULT_CONSOLE_APPENDER.threshold = params.level ?: DEFAULT_LEVEL
      LogManager.rootLogger.level = Level.ALL
    }
  }
}
