package com.vertex.quality.common.utils;

import com.vertex.quality.common.enums.VertexLogLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

public class VertexLogger
{
	private static StackWalker walker = StackWalker.getInstance(RETAIN_CLASS_REFERENCE);

	private VertexLogger( )
	{
	}

	public static void log( final String logText, final VertexLogLevel logLevel, final Class<?> logClass )
	{
		final Logger logger = LogManager.getLogger(logClass);

		switch ( logLevel )
		{
			case FATAL:
				logger.fatal(logText);
				break;

			case ERROR:
				logger.error(logText);
				break;

			case WARN:
				logger.warn(logText);
				break;

			case INFO:
				logger.info(logText);
				break;

			case DEBUG:
				logger.debug(logText);
				break;

			case TRACE:
				logger.trace(logText);
				break;

			default:
				break;
		}
	}

	public static void log( final String logText, final Class<?> logClass )
	{
		log(logText, VertexLogLevel.INFO, logClass);
	}

	public static void log( final String logText, final VertexLogLevel logLevel )
	{
		final Class<?> logClass = walker.getCallerClass();

		log(logText, logLevel, logClass);
	}

	public static void log( final String logText )
	{
		final Class<?> logClass = walker.getCallerClass();

		log(logText, VertexLogLevel.INFO, logClass);
	}
}
