/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.HibernateUtil;
import java.util.ArrayList;
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
}








