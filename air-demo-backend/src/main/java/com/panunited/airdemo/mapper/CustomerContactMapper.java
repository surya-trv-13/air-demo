package com.panunited.airdemo.mapper;


import com.panunited.airdemo.dto.CustomerContactDetailResponse;
import com.panunited.airdemo.models.CustomerContact;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface CustomerContactMapper {

    public List<CustomerContactDetailResponse> toResponse(List<CustomerContact> list);
}
