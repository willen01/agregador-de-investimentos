package com.willen.agregadorinvestimentos.services;

import org.springframework.stereotype.Service;

import com.willen.agregadorinvestimentos.controllers.dto.CreateStockDTO;
import com.willen.agregadorinvestimentos.entities.Stock;
import com.willen.agregadorinvestimentos.repositories.StockRepository;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO createStockDTO) {
        var stock = new Stock(
                createStockDTO.stockId(),
                createStockDTO.description());

        stockRepository.save(stock);
    }
}
