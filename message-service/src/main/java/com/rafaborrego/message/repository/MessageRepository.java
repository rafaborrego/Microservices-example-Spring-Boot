package com.rafaborrego.message.repository;

import com.rafaborrego.message.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("select message " +
            "from #{#entityName} message " +
            "where message.deleted=false " +
            "order by message.creationTimestamp desc")
    List<Message> findNonDeletedMessagesOrderedByCreationTimestamp();
}
