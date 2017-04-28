package finalproject.database.queries;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public  abstract class AbstractQuery {

	public static <R>  void query(SessionFactory sessionFactory, SimpleQuery query ){
		
		Session session = null;
		Transaction tx= null;
		try{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			query.query(session);
			tx.commit();
		}catch(HibernateException e){
			System.err.println(e);	
			if(tx!=null){
				try{
					tx.rollback();
				}catch (HibernateException b) {
					 System.err.println("Create transcation failed." + tx);	
				}
			}
		}finally{
			try{
				session.close();
			}catch (HibernateException b) {
				 System.err.println("Closing Session failed." + session);
			}
		}
	}

	public static <R>  R query(SessionFactory sessionFactory, ComplexQuery<R> query ){
		R response=null;
		Session session = null;
		Transaction tx= null;
		try{
			session=sessionFactory.openSession();
			tx=session.beginTransaction();
			response=query.query(session);
			tx.commit();
		}catch(HibernateException e){
			System.err.println(e);	
			if(tx!=null){
				try{
					tx.rollback();
				}catch (HibernateException b) {
					 System.err.println("Create transcation failed." + tx);	
				}
			}
		}finally{
			try{
				session.close();
			}catch (HibernateException b) {
				 System.err.println("Closing Session failed." + session);
			}
		}
		return response;
	}
}
