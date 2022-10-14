package com.azelewski.exercise.infrastructure;

import com.azelewski.exercise.controller.ComputerController;
import com.azelewski.exercise.model.Computer;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
    private ComputerController controller;


    @Override
    public void run(ApplicationArguments args){
    controller.saveComputer(new Computer("Komputer 1", LocalDate.of(2022, 1,3), BigDecimal.valueOf(345)));
    controller.saveComputer(new Computer("Komputer 2", LocalDate.of(2022, 1,3), BigDecimal.valueOf(543)));
    controller.saveComputer(new Computer("Komputer 3", LocalDate.of(2022, 1,3), BigDecimal.valueOf(346)));
    controller.saveComputer(new Computer("Komputer 1", LocalDate.of(2022, 1,10), BigDecimal.valueOf(345)));
    controller.saveComputer(new Computer("Komputer 2", LocalDate.of(2022, 1,10), BigDecimal.valueOf(543)));
    controller.saveComputer(new Computer("Komputer 3", LocalDate.of(2022, 1,10), BigDecimal.valueOf(346)));
    controller.saveAsXml("plik","name","asc","");
    }
}
