package car.top.content360.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import car.top.content360.entities.AccessTokenPojo;

public interface AccessTokenPojoRepository extends CrudRepository<AccessTokenPojo, Long> {

	// TODO (ZP, 2017-07-13, 此处的自定义查询access_token_pojo 是表名 request_host 是表字段名 。 用entity的名字会报错。)
	@Query(value="select * from access_token_pojo where request_host =?1 and request_query=?2", nativeQuery=true)
	public List<AccessTokenPojo> findByRequestHostAndrequestQuery(String requestHost, String requestQuery);
}
