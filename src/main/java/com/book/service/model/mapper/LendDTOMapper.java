package com.book.service.model.mapper;

import com.book.service.entity.LendEntity;
import com.book.service.model.Lend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface LendDTOMapper {

	LendDTOMapper INSTANCE = Mappers.getMapper( LendDTOMapper.class );
	
	 @Mappings({ 
	        @Mapping(source = "id", target = "id")
	    })
	 LendEntity lendToLendsEntity(Lend lend);
	
	 Lend lendsEntityToLend(LendEntity offerEntity);
	
	 default List<Lend> lendsEntityListToLendList(List<LendEntity> lendEntities){
		List<Lend> lends= new ArrayList<>();
		for(LendEntity lendEntity: lendEntities) {
			Lend lend=INSTANCE.lendsEntityToLend(lendEntity);
			lends.add(lend);
			
		}
		return lends;
		
	 }
}
