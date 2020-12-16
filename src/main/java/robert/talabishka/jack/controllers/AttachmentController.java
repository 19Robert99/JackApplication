package robert.talabishka.jack.controllers;

import org.springframework.web.bind.annotation.*;
import robert.talabishka.jack.model.Attachment;
import robert.talabishka.jack.model.Ticket;
import robert.talabishka.jack.services.AttachmentService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/attachmentManagement/")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @RequestMapping(value = "/create/all/{id}", method = RequestMethod.POST)
    public List<Attachment> createAttachment(@PathVariable Long id, @RequestBody Attachment attachment) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        attachment.setTicket(ticket);
        attachment.setCreationDate(new Date());

        attachmentService.save(attachment);
        return attachmentService.getAttachmentsByTicketId(attachment.getTicket().getId());
    }

    @RequestMapping(value = "/all/{id}", method = RequestMethod.GET)
    public List<Attachment> createAttachment(@PathVariable Long id) {
        return attachmentService.getAttachmentsByTicketId(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void deleteAttachment(@PathVariable Long id) {
        attachmentService.deleteById(id);
    }
}
