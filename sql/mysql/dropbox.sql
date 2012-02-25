use `connector`;

CREATE TABLE dropbox_account (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  created TIMESTAMP NOT NULL,
  deactivated TIMESTAMP,
  name VARCHAR(255),
  email VARCHAR(255),
  country VARCHAR(20),
  lang VARCHAR(20),
  referral_link VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE dropbox_access_token (
  account_id VARCHAR(40) PRIMARY KEY,
  value VARCHAR(40) NOT NULL,
  secret VARCHAR(40) NOT NULL,
  consumer_key VARCHAR(255) NOT NULL,
  created TIMESTAMP NOT NULL
) ENGINE=InnoDB;

CREATE TABLE dropbox_directory (
  id VARCHAR(40) PRIMARY KEY,
  account_id VARCHAR(40) NOT NULL,
  directory VARCHAR(255) NOT NULL,
  hash VARCHAR(255),
  modified TIMESTAMP,
  last_check TIMESTAMP,
  last_processed TIMESTAMP,
  is_updated BOOLEAN DEFAULT TRUE,
  locked_by VARCHAR(40),
  CONSTRAINT FOREIGN KEY 
    dropbox_hash_access_token_fk (account_id) 
    REFERENCES dropbox_access_token (account_id),
  CONSTRAINT UNIQUE INDEX dropbox_dir_account_index (account_id, directory),
  INDEX dropbox_dir_locked_updated_index (locked_by)
) ENGINE=InnoDB;

CREATE TABLE dropbox_file (
  id VARCHAR(40) PRIMARY KEY,
  directory_id VARCHAR(40) NOT NULL,
  name VARCHAR(255) NOT NULL,
  created TIMESTAMP NOT NULL,
  status VARCHAR(20),
  CONSTRAINT FOREIGN KEY 
    dropbox_directory_id_fk (directory_id) 
    REFERENCES dropbox_directory (id),
  INDEX dropbox_dir_index (directory_id)
) ENGINE=InnoDB;