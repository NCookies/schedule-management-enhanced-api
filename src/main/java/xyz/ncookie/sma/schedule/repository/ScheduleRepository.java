package xyz.ncookie.sma.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.ncookie.sma.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
