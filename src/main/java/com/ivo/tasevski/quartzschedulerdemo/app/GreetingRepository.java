package com.ivo.tasevski.quartzschedulerdemo.app;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greeting, String> {
}
