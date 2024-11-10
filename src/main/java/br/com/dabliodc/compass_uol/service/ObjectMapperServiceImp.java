package br.com.dabliodc.compass_uol.service;

import br.com.dabliodc.compass_uol.domain.VendorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ObjectMapperServiceImp implements ObjectMapperService {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public VendorDTO mapperPayments(Object object) {
        return objectMapper.convertValue(
                objectMapper.convertValue(object, Map.class).get("vendor"),
                VendorDTO.class
        );
    }
}
