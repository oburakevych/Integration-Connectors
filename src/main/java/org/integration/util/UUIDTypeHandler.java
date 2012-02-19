package org.integration.util;

import java.sql.SQLException;
import java.util.UUID;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class UUIDTypeHandler implements TypeHandlerCallback {

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		return UUID.fromString((String) getter.getObject());
	}

	@Override
	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		setter.setObject(parameter.toString());
	}

	@Override
	public Object valueOf(String s) {
		return UUID.fromString(s);
	}
}
