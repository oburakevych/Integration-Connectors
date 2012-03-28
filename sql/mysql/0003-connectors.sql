use `connector`;

CREATE TABLE file_transfer_log (
  id VARCHAR(40) PRIMARY KEY,
  file_id VARCHAR(40) NOT NULL,
  date TIMESTAMP NOT NULL,
  comments VARCHAR(1024),
  CONSTRAINT FOREIGN KEY 
    dropbox_file_id_fk (file_id) 
    REFERENCES dropbox_file (id),
  INDEX dropbox_file_index (file_id)
) ENGINE=InnoDB;