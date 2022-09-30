package com.greatlearning.service;
import java.util.List;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.greatlearning.entity.Student;

@Repository
public class StudentServiceImpl implements StudentService
{
private SessionFactory	sessionFactory;
private Session session;

@Autowired
StudentServiceImpl(SessionFactory sessionFactory)
{
	this.sessionFactory=sessionFactory;
	try {
		session=(Session) sessionFactory.getCurrentSession();
		
	}
	catch(HibernateException e)
	{
		session=(Session) sessionFactory.openSession();
	}
}
@Transactional
public List<Student> findAll()
{
	org.hibernate.Transaction tx=session.beginTransaction();
	//find all the records from database table
	List<Student> students=session.createQuery("from Student").list();
	tx.commit();
	return students;
}
@Transactional
public void save(Student theStudent)
{
	org.hibernate.Transaction tx=session.beginTransaction();
	//save transaction
	session.saveOrUpdate(theStudent);
	tx.commit();
}
@Transactional
public void deleteById(int id)
{
	org.hibernate.Transaction tx=session.beginTransaction();
	
	Student student=session.get(Student.class,id);
	session.delete(student);
	tx.commit();
}
@Override
public Student findById(int theId) {
	 
		Student student=new Student();
		org.hibernate.Transaction tx=session.beginTransaction();
		 student=session.get(Student.class,theId);
		tx.commit();
		return student;
	
}


@Override
	@Transactional
	public List<Student> searchBy(String name,String country) {
		org.hibernate.Transaction tx=session.beginTransaction();
		String query="";
		
		if (name.length() != 0 && country.length() != 0)
			query = "from Student where name like '%" + name + "%' or country like '%" + country+ "%'";
		else if (name.length() != 0)
			query = "from Student where name like '%" + name + "%'";
		else if (country.length() != 0)
			query = "from Student where country like '%" + country + "%'";
		else
			System.out.println("Cannot search without input data");

		List<Student> students = session.createQuery(query).list();

		tx.commit();

		return students;
		
	
	}

}
