package com.creafund.creafund_api.repository;

import com.creafund.creafund_api.entity.GroupeBillet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeBilletRepository extends JpaRepository<GroupeBillet, Long> {
}