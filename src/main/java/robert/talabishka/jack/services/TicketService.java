package robert.talabishka.jack.services;

import robert.talabishka.jack.model.Ticket;

public interface TicketService extends AbstractService<Ticket> {
    void addWatcher(Long userId, Long ticketId);
    void removeWatcher(Long userId, Long ticketId);
}
