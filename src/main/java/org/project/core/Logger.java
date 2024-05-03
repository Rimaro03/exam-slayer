package org.project.core;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Logger {
    public static void init(){ }
    public static void trace(Object o) { log.trace(o); }
    public static void info(Object o){ log.info(o); }
    public static void warn(Object o) { log.warn(o); }
    public static void error(Object o) { log.error(o); }
    public static void fatal(Object o) { log.fatal(o);}
}
