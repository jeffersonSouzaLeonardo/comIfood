package br.com.lett.comifood.repository;

import br.com.lett.comifood.model.EnterpriseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseRepository extends JpaRepository<EnterpriseEntity, Long> {

    List<EnterpriseEntity> findByStatus(String active);

}
