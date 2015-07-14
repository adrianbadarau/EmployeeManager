/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.HibernateUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author adrian
 */
@Entity
@Table (name = "Employe")
public class Employee {
    private static final Session session = HibernateUtil.getSessionFactory().openSession();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "Id")
    private int Id;
    @Column (name = "Name")
    private String Name;
    @Column (name = "Address")
    private String Address;
    @Column (name = "Age")
    private int Age;
    @Column (name = "Salary")
    private float Salary;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public float getSalary() {
        return Salary;
    }

    public void setSalary(float Salary) {
        this.Salary = Salary;
    }
    
    public Employee()
    {
        
    }
    
    public static ArrayList<Employee> findAll()
    {
        String queryStr = "From Employee";
        Query query = session.createQuery(queryStr);
        
        ArrayList<Employee> all = new ArrayList<>();
        return (ArrayList<Employee>) query.list();
       
    }
    
    public static ArrayList<Employee> findAll(String colName, String operator, String colVal)
    {
        String queryStr = "";
        if(!operator.equals("LIKE")){
            queryStr = "From Employee as e where e."+colName+" "+operator+" '"+colVal+"'";
        }else{
            queryStr = "From Employee as e where e."+colName+" "+operator+" '%"+colVal+"%'";
        }
        Query query = session.createQuery(queryStr);
        
        return (ArrayList<Employee>) query.list();       
    }
    
    public static Employee findBy(String colName, String colVal)
    {
        String queryStr = "From Employee as e where e."+colName+" ='"+colVal+"'";
        Query query = session.createQuery(queryStr);
        query.setMaxResults(1);
        return (Employee) query.uniqueResult();
    }
    
    public void save()
    {
        Employee.session.saveOrUpdate(this);
        Employee.session.flush();
    }
    
    public void update()
    {
        Employee.session.merge(this);
        Employee.session.flush();
    }
    
    public boolean delete()
    {
        Employee.session.delete(this);
        Employee.session.flush();
        return true;
    }
    
    public static Employee setAttrsFromHasMap(HashMap<String,String> attrs, Employee emp)
    {
        emp.setId(Integer.getInteger(attrs.get("Id")));
        emp.setAge(Integer.getInteger(attrs.get("Age")));
        emp.setSalary(Float.valueOf(attrs.get("Salary")));
        emp.setAddress(attrs.get("Address"));
        emp.setName(attrs.get("Name"));
        
        return emp;
    }
    
    public static Employee setAttrsFromDB(ResultSet resultSet) throws SQLException
    {
        Employee emp = new Employee();
        emp.setAddress(resultSet.getString("Address"));
        emp.setName(resultSet.getString("Name"));
        emp.setAge(resultSet.getInt("Age"));
        emp.setSalary(resultSet.getFloat("Salary"));
        emp.setId(resultSet.getInt("Id"));
        
        return emp;
    }
    
    public void setAttributesFromDB(ResultSet resultSet) throws SQLException
    {
        this.setAddress(resultSet.getString("Address"));
        this.setName(resultSet.getString("Name"));
        this.setAge(resultSet.getInt("Age"));
        this.setSalary(resultSet.getFloat("Salary"));
        this.setId(resultSet.getInt("Id"));
    }
    
//    public static ArrayList<Employee> findAll()
//    {
//        ArrayList<Employee> all = new ArrayList<>();
//        try {
//            ResultSet resultSet = getQueryResultSet("select * from "+table);
//            while(resultSet.next()){                
//                all.add(setAttrsFromDB(resultSet));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return all;
//    }
//    
//    public static ArrayList<Employee> findAll(String ColName, String operator, String val)
//    {
//        ArrayList<Employee> all = new ArrayList<>();
//        try {
//            ResultSet resultSet = getQueryResultSet("select * from "+table+" where "+ColName+" "+operator+" '"+val+"'");
//            while(resultSet.next()){                
//                all.add(setAttrsFromDB(resultSet));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return all;
//    }
//    
//    public static Employee findBy(String columnName, String value)
//    {
//        Employee emp = new Employee();
//        try {
//            ResultSet rs = getQueryResultSet("select * from "+table+" where "+columnName+" = '"+value+"' limit 1");
//            while(rs.next()){
//                emp.setAttributesFromDB(rs);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return emp;
//    }
//    public static Employee create(HashMap<String,String> values)
//    {
//        Employee emp = new Employee();
//        try {
//            Connection con = getConnection();
//            PreparedStatement st = con.prepareStatement("insert into "+table+" (Name,Age,Address,Salary) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//            st.setString(1, values.get("Name"));
//            st.setString(2, values.get("Age"));
//            st.setString(3, values.get("Address"));
//            st.setString(4, values.get("Salary"));
//            int afRows = st.executeUpdate();
//            setAttrsFromHasMap(values, emp);
//            if (afRows == 0){
//                throw new SQLException("Nu s-a modificat nimic");
//            }
//            try {
//                ResultSet gk = st.getGeneratedKeys();
//                if(gk.next()){
//                    emp.setId(gk.getInt("Id"));
//                }else{
//                    throw new SQLException("Nu s-a putut creea utilizatorul, nu s-a gasit Id");
//                }
//            } catch (Exception e) {
//                Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, e);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return emp;
//    }
//    
//    public void save()
//    {
//        try {
//            Connection con = getConnection();
//            PreparedStatement st = con.prepareStatement("insert into " + table + " (Name,Age,Address,Salary) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//            st.setString(1, this.getName());
//            st.setInt(2, this.getAge());
//            st.setString(3, this.getAddress());
//            st.setFloat(4, this.getSalary());
//            int afRows = st.executeUpdate();
//            if (afRows == 0) {
//                throw new SQLException("Nu s-a modificat nimic");
//            }
//            try {
//                ResultSet gk = st.getGeneratedKeys();
//                if (gk.next()) {
//                    this.setId(gk.getInt(1));
//                } else {
//                    throw new SQLException("Nu s-a putut creea utilizatorul, nu s-a gasit Id");
//                }
//            } catch (Exception e) {
//                Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, e);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
//    
//    public boolean update()
//    {
//        try {
//            Connection con = getConnection();
//            PreparedStatement st = con.prepareStatement("update "+table+" set Name= ?, Age= ?, Address= ?, Salary= ? where Id= ?");
//            st.setString(1, this.getName());
//            st.setInt(2, this.getAge());
//            st.setString(3, this.getAddress());
//            st.setFloat(4, this.getSalary());
//            st.setInt(5, this.getId());
//            return st.execute();
//        } catch (SQLException ex) {
//            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
//    
//    public boolean delete()
//    {
//        try {
//            Connection con = getConnection();
//            PreparedStatement st = con.prepareStatement("delete from "+table+" where Id = ?");
//            st.setInt(1, this.getId());
//            return !st.execute();
//        } catch (SQLException ex) {
//            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//        
//    }

}








