package net.reyadeyat.api.service.spring.process;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.reyadeyat.api.library.binary.file.BinaryFileProcess;
import net.reyadeyat.api.library.environment.ApiEnvironment;
import net.reyadeyat.api.library.process.BackgroundProcessScheduler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SpringBinaryFileProcess implements BackgroundProcessScheduler {
    
    final private BinaryFileProcess binary_file_process;
    final private TaskScheduler task_scheduler;
    
    public SpringBinaryFileProcess(TaskScheduler task_scheduler) throws Exception {
        this.task_scheduler = task_scheduler;
        binary_file_process = new BinaryFileProcess(this);
    }
        
    @Override
    public String getDescription() {
        return "";
    }
    
    @Override
    public ScheduledFuture<?> schedule(Runnable task) throws Exception {
        String initial_delay_seconds_property = ApiEnvironment.getProperty("net.reyadeyat.api.library.binary.file.process.initial_delay_seconds");
        String fixed_rate_seconds_property = ApiEnvironment.getProperty("net.reyadeyat.api.library.binary.file.process.fixed_rate_seconds");
        if (initial_delay_seconds_property == null || initial_delay_seconds_property.isBlank() ||
                fixed_rate_seconds_property == null || fixed_rate_seconds_property.isBlank()) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "SpringBinaryFileProcess task is disabled since initial_delay_seconds_property "
                    + (initial_delay_seconds_property == null || initial_delay_seconds_property.isBlank() ? "mull" : initial_delay_seconds_property)
                    + ", fixed_rate_seconds_property "
                    + (fixed_rate_seconds_property == null || fixed_rate_seconds_property.isBlank() ? "mull" : fixed_rate_seconds_property));
            /*throw new Exception("wrong task parameters initial_delay_seconds_property "
                    + (initial_delay_seconds_property == null || initial_delay_seconds_property.isBlank() ? "mull" : initial_delay_seconds_property)
                    + ", fixed_rate_seconds_property "
                    + (fixed_rate_seconds_property == null || fixed_rate_seconds_property.isBlank() ? "mull" : fixed_rate_seconds_property));*/
            return null;
        }
        long initial_delay_seconds = Long.parseLong(initial_delay_seconds_property);
        long fixed_rate_seconds = Long.parseLong(fixed_rate_seconds_property);
        Instant start_time_instant = Instant.now().plusSeconds(initial_delay_seconds);
        Duration fixed_rate_duration = Duration.ofSeconds(fixed_rate_seconds);
        return task_scheduler.scheduleAtFixedRate(task, start_time_instant, fixed_rate_duration);
    }
    
}
