package com.lnet.ums.mapper.dao;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serializable;

public class UnderscoresNameStrategy extends PhysicalNamingStrategyStandardImpl implements Serializable {

    private static String addUnderscores(String name) {
        final StringBuilder buf = new StringBuilder(name.replace('.', '_'));
        for (int i = 1; i < buf.length() - 1; i++) {
            if (
                    Character.isLowerCase(buf.charAt(i - 1)) &&
                            Character.isUpperCase(buf.charAt(i)) &&
                            Character.isLowerCase(buf.charAt(i + 1))
                    ) {
                buf.insert(i++, '_');
            }
        }
        return buf.toString().toUpperCase();
    }
    public static String columnToProperty(String column) {
        column = column.toLowerCase();
        if (column.contains("_")) {
            String[] array = column.split("_");
            StringBuffer sb = new StringBuffer();
            sb.append(array[0]);
            for (int i = 1; i < array.length; i++) {
                sb.append(toUpperCaseFirstOne(array[i]));
            }
            return sb.toString();
        }
        return column;
    }
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    private static Identifier convertToUnderscores(Identifier identifier, JdbcEnvironment context) {
        if (identifier == null || identifier.getText() == null || identifier.getText().trim().length() == 0) {
            return identifier;
        }
        return Identifier.toIdentifier(addUnderscores(identifier.getText()), identifier.isQuoted());
    }

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return convertToUnderscores(name, context);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return convertToUnderscores(name, context);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return convertToUnderscores(name, context);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return convertToUnderscores(name, context);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return convertToUnderscores(name, context);
    }
}
