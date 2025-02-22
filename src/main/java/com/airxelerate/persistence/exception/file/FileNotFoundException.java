package com.airxelerate.persistence.exception.file;

import com.airxelerate.persistence.exception.config.ResourceNotFoundException;

public class FileNotFoundException extends ResourceNotFoundException {

  /**
   * @param message
   */
  public FileNotFoundException() {
    super("error File does not exist");

  }

}
