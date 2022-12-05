package se.accelerateit.signup6.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.accelerateit.signup6.scheduling.SchedulingConfiguration;

@RestController
public class SchedulerController extends BaseApiController {

  private final SchedulingConfiguration schedulingConfiguration;

  @Autowired
  SchedulerController(SchedulingConfiguration schedulingConfiguration) {
    this.schedulingConfiguration = schedulingConfiguration;
  }

  @GetMapping("/scheduledtrigger")
  public void runScheduledTrigger() {
    schedulingConfiguration.scheduledTrigger();
  }
}
