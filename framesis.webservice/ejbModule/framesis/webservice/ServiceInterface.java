package framesis.webservice;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ServiceInterface {

	public List<String> subScens();
}
