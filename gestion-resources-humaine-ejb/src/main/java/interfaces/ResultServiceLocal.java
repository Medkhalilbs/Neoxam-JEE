package interfaces;

import java.util.List;

import entities.Result;
import entities.User;

public interface ResultServiceLocal {

	Boolean addResult(Result result);

	Boolean updateResult(Result result);

	Boolean deleteResult(Result result);

	Boolean deleteResult(int id);

	Boolean deleteResult(String id);

	Result findResultById(Integer id);

	Result findResultById(String id);

	List<Result> findAllResult();

	List<User> listeResultsbyname();

	Long CountResults();

}
