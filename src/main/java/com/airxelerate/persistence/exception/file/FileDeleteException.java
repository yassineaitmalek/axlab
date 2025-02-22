package com.airxelerate.persistence.exception.file;

import com.airxelerate.persistence.exception.config.ServerSideException;

public class FileDeleteException extends ServerSideException {

  /**
   * @param message
   */
  public FileDeleteException() {
    super("error while deleting a file");

  }

}
