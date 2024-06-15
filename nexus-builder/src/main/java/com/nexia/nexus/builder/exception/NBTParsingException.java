package com.nexia.nexus.builder.exception;

public class NBTParsingException extends RuntimeException {
    public NBTParsingException(String nbtString) { super(String.format("Error parsing %s as an NBTObject", nbtString)); }
}
