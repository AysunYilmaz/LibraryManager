package com.book.service.model.mapper;

import com.book.service.entity.BookEntity;
import com.book.service.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface BookDTOMapper {

	BookDTOMapper INSTANCE = Mappers.getMapper( BookDTOMapper.class );
	
	 @Mappings({ 
	        @Mapping(source = "id", target = "id"),
	        @Mapping(source = "name", target = "name")
	    })
	 BookEntity bookToBooksEntity(Book book);
	
	Book booksEntityToBook(BookEntity offerEntity);
	
	default List<Book> booksEntityListToBookList(List<BookEntity> bookEntities){
		List<Book> books= new ArrayList<>();
		for(BookEntity bookEntity: bookEntities) {
			Book book=INSTANCE.booksEntityToBook(bookEntity);
			books.add(book);
			
		}
		return books;
		
	};
}
