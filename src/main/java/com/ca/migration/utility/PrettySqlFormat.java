package com.ca.migration.utility;

import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class PrettySqlFormat implements MessageFormattingStrategy {

	private final Formatter formatter = new BasicFormatterImpl();

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
			String sql) {
		// TODO Auto-generated method stub
		/*if(sql.startsWith("select") || sql.startsWith("SELECT"))
			return "";*/

		String commitStatement ="";

		if("COMMIT".equalsIgnoreCase(category)||"ROLLBACK".equalsIgnoreCase(category)) {
			commitStatement= " \n"+category+";";
		}

		if(null!=sql && sql.length()>0) {
			sql =sql+";";
		}
		return formatter.format(sql+commitStatement);
	}

}
