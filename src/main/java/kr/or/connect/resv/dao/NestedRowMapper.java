package kr.or.connect.resv.dao;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.StringUtils;

public class NestedRowMapper<T> implements RowMapper<T> {

    private Class<T> mappedClass;
    private Map<String, PropertyDescriptor> mappedFields;
    private Map<String, String> snakeToCamelCaseProperties;

    public NestedRowMapper(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
        this.mappedFields = new HashMap();
        this.snakeToCamelCaseProperties = new HashMap();
        setMappedFields();
    }

    private void setMappedFields() {
        for (PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(mappedClass)) {
            setAsSnakeCase(pd.getName(), pd);
            if (pd.getPropertyType().getClassLoader() != null) {
                for (PropertyDescriptor nestedPd : BeanUtils.getPropertyDescriptors(pd.getPropertyType())) {
                    setAsSnakeCase(pd.getName() + "." + nestedPd.getName(), nestedPd);
                }
            }
        }
    }

    private void setAsSnakeCase(String camelCaseProperty, PropertyDescriptor pd) {
        String snakeCaseProperty = underscoreName(camelCaseProperty);
        snakeToCamelCaseProperties.put(snakeCaseProperty, camelCaseProperty);
        mappedFields.put(camelCaseProperty, pd);
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T mappedObject = BeanUtils.instantiate(this.mappedClass);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
        bw.setAutoGrowNestedPaths(true);

        ResultSetMetaData meta_data = rs.getMetaData();
        int columnCount = meta_data.getColumnCount();

        String column, field;
        PropertyDescriptor pd;
        Object value;
        for (int index = 1; index <= columnCount; index++) {
            try {
                column = JdbcUtils.lookupColumnName(meta_data, index);
                field = snakeToCamelCaseProperties.get(column);
                pd = mappedFields.get(field);
                value = JdbcUtils.getResultSetValue(rs, index, pd.getPropertyType());

                bw.setPropertyValue(field, value);
            } catch (NullPointerException | TypeMismatchException | NotWritablePropertyException e) {
                System.out.println(e);
            }
        }

        return mappedObject;
    }

    protected String underscoreName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        } else {
            StringBuilder result = new StringBuilder();
            result.append(Character.toLowerCase(name.charAt(0)));

            for (int i = 1; i < name.length(); ++i) {
                char c = name.charAt(i);
                if (Character.isUpperCase(c)) {
                    result.append('_').append(Character.toLowerCase(c));
                } else {
                    result.append(c);
                }
            }

            return result.toString();
        }
    }
}
