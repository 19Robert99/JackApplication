package robert.talabishka.jack.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import robert.talabishka.jack.model.Attachment;

import java.util.List;

@Repository
public interface AttachmentRepository extends AbstractRepository<Attachment> {

    @Query("select at from Attachment at where at.ticket.id = :ticketId")
    List<Attachment> getAttachmentsByTicketId(Long ticketId);
}
