package com.airxelerate.persistence.exception.file;

import com.airxelerate.persistence.exception.config.ServerSideException;

public class FileUploadException extends ServerSideException {

  /**
   * @param message
   */
  public FileUploadException() {
    super("error could not upload File");

  }

}
