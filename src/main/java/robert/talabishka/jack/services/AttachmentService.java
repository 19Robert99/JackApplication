package robert.talabishka.jack.services;

import robert.talabishka.jack.model.Attachment;

import java.util.List;

public interface AttachmentService extends AbstractService<Attachment> {
    List<Attachment> getAttachmentsByTicketId(Long ticketId);
}
