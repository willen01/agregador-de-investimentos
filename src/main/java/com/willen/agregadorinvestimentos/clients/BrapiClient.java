package com.willen.agregadorinvestimentos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.willen.agregadorinvestimentos.clients.dto.BrapiResponseDTO;

@FeignClient(name = "BrapiClient", url = "https://brapi.dev")
public interface BrapiClient {

    @GetMapping(value = "/api/quote/{stockId}")
    BrapiResponseDTO getQuote(@RequestParam("token") String token,
            @PathVariable("stockId") String stockId);
}
