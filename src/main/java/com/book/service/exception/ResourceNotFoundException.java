package com.book.service.exception;

public class ResourceNotFoundException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1788418564244824606L;
	
	private Long id;

    public ResourceNotFoundException(Long id) {
        super(String.format("resource %s was not found", id));
        this.id = id;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public Long getId() {
        return id;
    }

}
