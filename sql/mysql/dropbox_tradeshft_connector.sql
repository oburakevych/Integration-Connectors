use `connector`;

CREATE TABLE dropbox_tradeshift_connector (
  id VARCHAR(40) PRIMARY KEY,
  dropbox_account_id VARCHAR(40) NOT NULL,  
  tradeshift_account_id VARCHAR(40) NOT NULL,
  CONSTRAINT FOREIGN KEY 
    cn_dropbox_tradeshift_accountid_fk (dropbox_account_id) 
    REFERENCES dropbox_account (id),
  CONSTRAINT FOREIGN KEY 
    cn_tradeshift_dropbox_accountid_fk (tradeshift_account_id) 
    REFERENCES tradeshift_account (id)
) ENGINE=InnoDB;

CREATE TABLE dropbox_trdeshift_connector_properties (
  connector_id VARCHAR(40) NOT NULL,
  `key` VARCHAR(255) NOT NULL,
  `value` VARCHAR(1024),
  CONSTRAINT FOREIGN KEY 
    cn_dropbox_tradeshift_properties_id_fk (connector_id) 
    REFERENCES dropbox_tradeshift_connector (id)
) ENGINE=InnoDB;