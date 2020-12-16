package robert.talabishka.jack.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import robert.talabishka.jack.model.Ticket;

@Repository
public interface TicketRepository extends AbstractRepository<Ticket> {
    @Modifying
    @Query(nativeQuery = true,value = "INSERT INTO watchers(user_id,ticket_id) values (?1, ?2)")
    void addWatcher(Long userId, Long ticketId);

    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM watchers WHERE user_id = ?1 AND ticket_id = ?2")
    void removeWatcher(Long userId, Long ticketId);
}
