package com.company.repository;

import com.company.entity.attach.AttachEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttachRepository extends PagingAndSortingRepository<AttachEntity, String> {
}
