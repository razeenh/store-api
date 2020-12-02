package com.repl.store.api.service;

import com.repl.store.api.dao.Store;
import com.repl.store.api.dao.StoreRepository;
import com.repl.store.api.dto.StoreDto;
import com.repl.store.api.mapper.StoreMapperImpl;
import com.repl.store.api.sdk.mapper.DomainMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StoreServiceImpl implements StoreService {

    private StoreRepository storeRepo;
    private DomainMapper<Store, StoreDto> storeMapper;
    private Logger logger = LogManager.getLogger(StoreServiceImpl.class);

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepo) {
        this.storeRepo = storeRepo;
        this.storeMapper = new StoreMapperImpl();
    }

    @Override
    @Transactional
    public StoreDto createStore(Long id) {
        logger.info("Creating store for ID " + id);
        Store storeEntity = new Store();
        storeEntity.setId(id);
        return storeMapper.to(storeRepo.save(storeEntity));
    }

    @Override
    public StoreDto getStore(Long id) {
        return storeMapper.to(storeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Store not found for ID " + id)));
    }

    @Override
    public List<StoreDto> getAllStores() {
        return StreamSupport.stream(storeRepo.findAll().spliterator(), false)
                .map(storeMapper::to)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStores(List<StoreDto> stores) {
        stores.forEach(store -> {
            logger.info("Deleting store for ID " + store.getId());
            Store storeEntity = storeRepo.findById(store.getId()).orElseThrow(() -> new EntityNotFoundException("Store not found for ID " + store.getId()));
            storeRepo.delete(storeEntity);
        });
    }
}
