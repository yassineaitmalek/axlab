package com.airxelerate.persistence.exception.file;

import com.airxelerate.persistence.exception.config.ServerSideException;

public class FileUnStreamableException extends ServerSideException {

  /**
   * @param message
   */
  public FileUnStreamableException() {
    super("error the file is unstreamable");

  }

}
