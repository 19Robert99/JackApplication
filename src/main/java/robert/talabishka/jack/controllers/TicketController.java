package robert.talabishka.jack.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import robert.talabishka.jack.model.Ticket;
import robert.talabishka.jack.model.User;
import robert.talabishka.jack.services.ProjectService;
import robert.talabishka.jack.services.TicketService;
import robert.talabishka.jack.services.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/ticketManagement/")
public class TicketController extends AbstractController{

    private final TicketService ticketService;
    private final ProjectService projectService;
    private final UserService userService;

    public TicketController(TicketService ticketService, ProjectService projectService, UserService userService) {
        this.ticketService = ticketService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return new ResponseEntity<>(ticketService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/tickets/project/{id}", method = RequestMethod.GET)
    public List<Ticket> getTicketByProjectId(@PathVariable Long id) {
        List<Ticket> list = (List<Ticket>) projectService.findById(id).getTickets();
        list.sort((d1, d2) -> (int) (d1.getId() - d2.getId()));
        return list;
    }

    @RequestMapping(value = "/ticket/watcher/add/{id}", method = RequestMethod.POST)
    public void startWatching(@PathVariable Long id,
                              @RequestBody Ticket ticket) {
        ticketService.addWatcher(id, ticket.getId());
    }

    @RequestMapping(value = "/ticket/watcher/remove/{id}", method = RequestMethod.POST)
    public void stopWatching(@PathVariable Long id,
                              @RequestBody Ticket ticket) {
        ticketService.removeWatcher(id, ticket.getId());
    }
}
