package robert.talabishka.jack.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robert.talabishka.jack.model.Ticket;
import robert.talabishka.jack.repositories.TicketRepository;
import robert.talabishka.jack.services.TicketService;

@Service
@Transactional
public class TicketServiceImpl extends AbstractServiceImpl<Ticket> implements TicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository repository) {
        super(repository);
        this.ticketRepository = repository;
    }


    @Override
    public void addWatcher(Long userId, Long ticketId) {
        ticketRepository.addWatcher(userId, ticketId);
    }

    @Override
    public void removeWatcher(Long userId, Long ticketId) {
        ticketRepository.removeWatcher(userId, ticketId);
    }

    @Override
    public Long getIdByKey(String key) {
        return ticketRepository.getIdByKey(key);
    }
}
