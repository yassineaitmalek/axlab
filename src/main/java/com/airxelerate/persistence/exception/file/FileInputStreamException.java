package com.airxelerate.persistence.exception.file;

import com.airxelerate.persistence.exception.config.ServerSideException;

public class FileInputStreamException extends ServerSideException {

  /**
   * @param message
   */
  public FileInputStreamException() {
    super("error while getting the file inputstream");

  }

}
