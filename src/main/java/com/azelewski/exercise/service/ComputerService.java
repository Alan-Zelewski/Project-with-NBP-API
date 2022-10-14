package com.azelewski.exercise.service;

import com.azelewski.exercise.exceptions.RestTemplateExceptionHandler;
import com.azelewski.exercise.infrastructure.Invoice;
import com.azelewski.exercise.model.Computer;
import com.azelewski.exercise.repository.ComputerRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@AllArgsConstructor
public class ComputerService {
    private final ComputerRepository computerRepository;
    private static final Logger LOGGER = LogManager.getLogger(ComputerService.class);

    public void save(Computer computer) {
        computerRepository.save(computer);
    }

    public Page<Computer> searchComputers(String searchValue, int pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber -1,pageSize, sort);
        try{
            return computerRepository.findByDate(LocalDate.parse(searchValue),pageable);
        }catch (DateTimeParseException e){
            return computerRepository.searchComputers(searchValue, pageable);
        }
    }
    public BigDecimal getCurrency(LocalDate date) throws JSONException, ResourceAccessException {
        String uri = "http://api.nbp.pl/api/exchangerates/rates/c/usd/" + date +"/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateExceptionHandler());
        String exchangeRates = restTemplate.getForObject(uri, String.class);
        JSONObject object = new JSONObject(exchangeRates);
        JSONArray array = object.getJSONArray("rates");
        return array.getJSONObject(0).getBigDecimal("ask");
    }
    public void saveToFile(List<Computer> computers, String fileName){
        Invoice invoice = new Invoice(computers);
        fileName += ".xml";
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        try {
            xmlMapper.writeValue(new File(fileName), invoice);
            LOGGER.info("File saved successfully.");
        } catch (IOException e) {
            LOGGER.error("An error occurred, file not saved");
            LOGGER.error(e);
        }
    }
}
