package finalproject.database.queries;

import org.hibernate.Session;

public interface ComplexQuery<R> {

	  R query(Session session);
}
