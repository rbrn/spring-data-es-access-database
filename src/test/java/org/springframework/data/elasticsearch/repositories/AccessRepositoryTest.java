package org.springframework.data.elasticsearch.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/springContext-book-test.xml")
public class AccessRepositoryTest {

    private final ClassGenerator classGenerator = new ClassGenerator(this);
    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private ElasticsearchTemplate template;


    @Before
    public void emptyData() throws SQLException {
        employeeRepository.deleteAll();

        Connection conn = getConnection();
        Statement st = conn.createStatement();
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM tblEmployees");

        dump(rs, "example");

        st.close();
        conn.close();

    }

    @Test
    public void shouldCountAllElementsInIndex() throws SQLException {

        assertThat(employeeRepository.count(), notNullValue());
    }

    String url = "jdbc:ucanaccess://" + "src/test/resources/AgeRange.mdb";


    private void dump(ResultSet rs, String exName)
            throws SQLException {
        System.out.println("-------------------------------------------------");
        System.out.println();
        System.out.println(exName + " result:");
        System.out.println();

        classGenerator.withAccessResultSet(rs)
                .generateClassFromAccess();

/*        while (rs.next()) {
            System.out.print("| ");
            Employee employee = new Employee();
            int j = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= j; ++i) {
                Object o = rs.getObject(i);
                if (rs.getMetaData().getColumnName(i) != null && o != null)
                    employee.callSetter(rs.getMetaData().getColumnName(i), o.toString());
                System.out.print(rs.getMetaData().getColumnName(i) + " : " + o + " | ");
            }

            employeeRepository.save(employee);

            System.out.println();
            System.out.println();
        }*/
    }

    private void generateClassFromAccess(ResultSetMetaData metaData) {
        try {
            classGenerator.generateClassFromAccess();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public Connection getConnection() {


        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");  // can be omitted in most cases
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }


}
