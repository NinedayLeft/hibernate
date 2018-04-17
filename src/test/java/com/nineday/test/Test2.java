package com.nineday.test;


import org.hibernate.cfg.AnnotationConfiguration;
import com.nineday.model.Employee2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;

/**
 * Created by nineday on 2018/4/17.
 */
public class Test2 {


    private static SessionFactory factory;
    public static void main(String[] args) {
        try{
            factory = new AnnotationConfiguration().configure().addAnnotatedClass(Employee2.class).buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
            Test2 ME = new Test2();

      /* Add few employee records in database */
            Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
            Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
            Integer empID3 = ME.addEmployee("John", "Paul", 10000);

      /* List down all the employees */
            ME.listEmployees();

      /* Update employee's records */
            ME.updateEmployee(empID1, 5000);

      /* Delete an employee from the database */
            ME.deleteEmployee(empID2);

      /* List down new list of the employees */
            ME.listEmployees();
        }
        /* Method to CREATE an employee in the database */
        public Integer addEmployee(String fname, String lname, int salary){
            Session session = factory.openSession();
            Transaction tx = null;
            Integer employeeID = null;
            try{
                tx = session.beginTransaction();
                Employee2 employee = new Employee2(fname, lname, salary);
                employeeID = (Integer) session.save(employee);
                tx.commit();
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            }finally {
                session.close();
            }
            return employeeID;
        }
        /* Method to  READ all the employees */
        public void listEmployees( ){
            Session session = factory.openSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                List employees = session.createQuery("FROM Employee2").list();
                for (Iterator iterator =
                     employees.iterator(); iterator.hasNext();){
                    Employee2 employee = (Employee2) iterator.next();
                    System.out.print("First Name: " + employee.getFirstName());
                    System.out.print("  Last Name: " + employee.getLastName());
                    System.out.println("  Salary: " + employee.getSalary());
                }
                tx.commit();
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            }finally {
                session.close();
            }
        }
        /* Method to UPDATE salary for an employee */
        public void updateEmployee(Integer EmployeeID, int salary ){
            Session session = factory.openSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Employee2 employee =
                        (Employee2)session.get(Employee2.class, EmployeeID);
                employee.setSalary( salary );
                session.update(employee);
                tx.commit();
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            }finally {
                session.close();
            }
        }
        /* Method to DELETE an employee from the records */
        public void deleteEmployee(Integer EmployeeID){
            Session session = factory.openSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Employee2 employee =
                        (Employee2)session.get(Employee2.class, EmployeeID);
                session.delete(employee);
                tx.commit();
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            }finally {
                session.close();
            }
        }
}

