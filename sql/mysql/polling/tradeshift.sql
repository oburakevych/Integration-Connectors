use `polling`;

CREATE TABLE tradeshift_account (
  id VARCHAR(40) NOT NULL PRIMARY KEY,
  created TIMESTAMP NOT NULL,
  deactivated TIMESTAMP,
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