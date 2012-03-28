CREATE DATABASE `connector` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

use `connector`;

CREATE TABLE tradeshift_account (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  created TIMESTAMP NOT NULL,
  deactivated TIMESTAMP NULL,
  name VARCHAR(255),
  email VARCHAR(255),
  country VARCHAR(20),
  lang VARCHAR(20)
) ENGINE=InnoDB;

CREATE TABLE tradeshift_access_token (
  account_id VARCHAR(40) PRIMARY KEY,
  value VARCHAR(40) NOT NULL,
  secret VARCHAR(40) NOT NULL,
  consumer_key VARCHAR(255) NOT NULL,
  created TIMESTAMP NOT NULL
) ENGINE=InnoDB;