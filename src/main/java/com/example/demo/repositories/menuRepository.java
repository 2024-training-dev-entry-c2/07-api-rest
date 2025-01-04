
package com.example.demo.repositories;

import com.example.demo.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface menuRepository extends JpaRepository<Menu,Long> {
}
