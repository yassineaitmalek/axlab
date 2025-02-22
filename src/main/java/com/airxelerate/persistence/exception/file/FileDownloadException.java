package com.airxelerate.persistence.exception.file;

import com.airxelerate.persistence.exception.config.ServerSideException;

public class FileDownloadException extends ServerSideException {

  /**
   * @param message
   */
  public FileDownloadException() {
    super("error could not Download File");

  }

}
