package robert.talabishka.jack.services.impl;

import org.springframework.stereotype.Service;
import robert.talabishka.jack.model.Attachment;
import robert.talabishka.jack.repositories.AttachmentRepository;
import robert.talabishka.jack.services.AttachmentService;

import java.util.List;

@Service
public class AttachmentServiceImpl extends AbstractServiceImpl<Attachment> implements AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        super(attachmentRepository);
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public List<Attachment> getAttachmentsByTicketId(Long ticketId) {
        return attachmentRepository.getAttachmentsByTicketId(ticketId);
    }
}
