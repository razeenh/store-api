CREATE TABLE IF NOT EXISTS STORE (
  id BIGINT PRIMARY KEY,
  version INT NOT NULL
);

CREATE TABLE IF NOT EXISTS STORE_INVENTORY (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  version INT NOT NULL,
  store_id BIGINT NOT NULL,
  inventory_action INT NOT NULL,
  item_id BIGINT DEFAULT NULL,
  item_name VARCHAR(40) NOT NULL,
  quantity INT DEFAULT NULL,
  UNIQUE KEY uk_inv_reg_store (store_id, inventory_action, item_name),
  CONSTRAINT fk_inv_reg_store FOREIGN KEY (store_id) REFERENCES STORE (id)
);