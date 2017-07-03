package org.springframework.data.elasticsearch.repositories;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassGenerator {
    private final AccessRepositoryTest accesssRepositoryTest;

    public ClassGenerator(AccessRepositoryTest accesssRepositoryTest) {
        this.accesssRepositoryTest = accesssRepositoryTest;
    }

    String declaration = "private String %s;";
    String declarationGetter = "public String get%s(){" +
            "  return %s;" +
            "}";
    String declarationSetter = "public void set%s(String  %s){" +
            "  this.%s = %s;" +
            "}";

    String classDef = "public class %s {" +
            "%s" +
            "}";

    private String generatePropertySetterAndGetter(String f) {
        return String.format(declaration, f);
    }


    String upperCaseFirst(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
    }

    public void generateClassFromAccess() throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<String> columns = new ArrayList<String>();
        try {
            for (int j = 1; j < metaData.getColumnCount(); j++) {
                System.out.println(metaData.getColumnName(j));
                columns.add(metaData.getColumnName(j));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<String> generated = new ArrayList();

        StringBuffer stringBuffer = new StringBuffer();

        for (String property : columns) {
            stringBuffer.append(String.format(declaration, property));
            stringBuffer.append(String.format(declarationGetter, upperCaseFirst(property), property));
            stringBuffer.append(String.format(declarationSetter, upperCaseFirst(property), property, property, property));
        }


        System.out.println(String.format(classDef, "RandomClass", stringBuffer.toString()));
    }

    private ResultSet resultSet;

    public ClassGenerator withAccessResultSet(ResultSet rs) {
        resultSet = rs;
        return this;
    }
}